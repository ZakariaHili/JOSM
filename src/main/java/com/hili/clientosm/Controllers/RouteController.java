package com.hili.clientosm.Controllers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hili.clientosm.Services.HttprequestThread;

@Controller
public class RouteController extends MainController {

	

	private HttprequestThread httprequest ;
	private LinkedList<String[]> positions;
	private static int codeMapQuest;

	
	public RouteController() {
		super();
		httprequest = super.getHttprequest();
		positions= new LinkedList<String[]>();
	}
	
	
	
	
	



	//Route Sender
	@RequestMapping(value = "/MakeRoute", method = RequestMethod.POST)
	public String fakeRoutesStart(Model model,HttpServletRequest request){
		
		if(!super.readSettings()){
	
		return "redirect:/Config";	
		}
		else{
		int delay=httprequest.getHttpServices().getGpsMetier().getSettingsDao().getDelay();
		int carSpeed=httprequest.getHttpServices().getCarSpeed();
		
		String id= request.getParameter("Id");
		httprequest.getHttpServices().getGpsMetier().getJsonEntitieDAO().setId(id);
		
		String lat1=request.getParameter("lat1");
		String lon1=request.getParameter("lon1");
		String lat2=request.getParameter("lat2");
		String lon2=request.getParameter("lon2");
		String url=httprequest.getHttpServices().getApiMetier().makeLinkForRoutesMapQuest(lat1, lon1, lat2, lon2);
				
		
		positions=new LinkedList<String[]>();
		try{
		positions=httprequest.getHttpServices().sendGetHttpRequest(url,"MapQuest");
		if(httprequest.getHttpServices().getApiMetier().isNoRoute()==true){
			  url=httprequest.getHttpServices().getApiMetier().makeLinkForRoutesGoogle(lat1, lon1, lat2, lon2);

			 positions=httprequest.getHttpServices().sendGetHttpRequest(url,"Google");	
		}
		}catch(Exception e){
			System.out.println("Can't generate positions using MapQuest: line 75 in Controller MakeRoute");
			
			
			
			
			
			
			try {
				positions=httprequest.getHttpServices().sendGetHttpRequest(url,"Google");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			
			
			
			
			
			
			
		}
		httprequest.getHttpServices().setCounter(0);
		
		for(int i=0;i<carSpeed;i++)
			try{
		positions.addFirst(new String[]{request.getParameter("lat1"),request.getParameter("lon1")}); 
			}catch (Exception e) {
                System.out.println("check MapQuest");
            }
		
		httprequest.getHttpServices().getGpsMetier().getJsonEntitieDAO().setPositions(positions);
		httprequest.getHttpServices().getGpsMetier().getJsonEntitieDAO().setIsServer(false);
		String strSpeed=String.valueOf(delay);
		model.addAttribute("speed",strSpeed);
		model.addAttribute("Id",request.getParameter("Id"));
		model.addAttribute("lat1",request.getParameter("lat1") );
		model.addAttribute("lon1", request.getParameter("lon1"));
		model.addAttribute("lat2",request.getParameter("lat2") );
		model.addAttribute("lon2", request.getParameter("lon2")); 
		
		
		
		

		try {
		while(httprequest.isRunning()==true) {
			httprequest.arret();
			Thread.sleep(100);
		}
			
			while(httprequest.getHttpServices().getErrorAPI()==0) Thread.sleep(100);
			codeMapQuest=httprequest.getHttpServices().getErrorAPI();
			
			model.addAttribute("CodeMapQuest",codeMapQuest);
			
			
			httprequest.demarrer();
		
		}catch (Exception e){
			
			System.out.println("MakeRoute "+e.toString() );
		}
		
		
		
		return "MakeRoute";
	}
	}

	
	
	@RequestMapping(value = "/MyRoute")
	public String myRoute(Locale locale, Model model)  {

		return "MyRoute";
	}

	
	
	

	
	
	
	//contacted by Javascript to send a new position
	@RequestMapping(value = "/getNewPosition", method = RequestMethod.GET)
	public @ResponseBody
	String getNewPosition() {
		if(httprequest.isRunning()){
			String [] position;
			try{
				LinkedList<String[]> positions= httprequest.getHttpServices().getGpsMetier().getJsonEntitieDAO().getPositions();
			if(httprequest.getHttpServices().getCounter()<positions.size()-1){

				position = positions.get(httprequest.getHttpServices().getCounter());		

			}
			else {
				if(positions.size()==1){
					
					position = positions.get(0);
				}
				else{
					position = positions.get(positions.size()-1);	
				httprequest.arret();}
			}
			}catch(Exception e){
				System.out.println("We can't get new position");
				position = positions.get(0);
			}


			return "{\"position\":["+position[0]+","+position[1]+"]}";}
		return null;
	}
	@RequestMapping(value = "/getCode", method = RequestMethod.GET)
	public @ResponseBody
	String getCode() {
		//httprequest.getHttpServices()
		return String.valueOf( httprequest.getHttpServices().getCode());
	}



}
