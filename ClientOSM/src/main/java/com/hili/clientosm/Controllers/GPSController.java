package com.hili.clientosm.Controllers;



import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hili.clientosm.Modele.Entities.JSONEntitie;
import com.hili.clientosm.Modele.Metier.GPSMetierImpl;
import com.hili.clientosm.Services.HttprequestThread;



/**
 * Created by HILI on 05/11/2015.
 */
@Controller
public class GPSController {

	public static String defaultServer="172.20.11.11";//172.20.0.69";//192.168.23.123"; //10.42.0.71";//"https://httpbin.org/post";
	private GPSMetierImpl gps ;
	
	private HttprequestThread httprequest ;
	private LinkedList<String[]> positions= new LinkedList<String[]>();
	private JSONEntitie json;
	private static int speed=5000;
	private static int codeMapQuest;

	public GPSController (){
		gps = new GPSMetierImpl();
		
		httprequest =new HttprequestThread();

	}

	// Send one Position for each 10 sec
	public void fakePositionStart(String Id,String lat,String lon, boolean isServer,String url){
		positions = new LinkedList<String[]>();
		positions.add(new String[]{lat,lon});
		httprequest.getHttpServices().setCounter(0);
		httprequest.setSpeed(speed);
		json= gps.createJSON(Id, positions, isServer);
		

		try {
			httprequest.arret();
			httprequest.setAll(json,url,1000);
			httprequest.demarrer();
			httprequest.start();
		}catch (Exception e){
			System.out.println("Error"+ e.toString());
		}
	}

	//Route Sender
	@RequestMapping(value = "/MakeRoute", method = RequestMethod.POST)
	public String fakeRoutesStart(Model model,HttpServletRequest request){
		httprequest.setSpeed(speed);
		json= new JSONEntitie();
		json.setDefaultserver(defaultServer);
		json.setId(request.getParameter("Id"));
		String url=gps.makeLinkForRoutes(request.getParameter("lat1"),
				request.getParameter("lon1"),request.getParameter("lat2"), 
				request.getParameter("lon2"));
		
		positions.clear();
		try{
		positions=httprequest.getHttpServices().sendGetHttpRequest(url);
		if(gps.isNoRoute()==true){
			 url=gps.makeLinkForRoutesGoogle(request.getParameter("lat1"),
					request.getParameter("lon1"),request.getParameter("lat2"), 
					request.getParameter("lon2"));
		System.out.println(url);
			 positions=httprequest.getHttpServices().sendGetHttpRequestGoogle(url);	
		}
		}catch(Exception e){
			System.out.println("Can't generate positions using MapQuest: line 75 in Controller MakeRoute");
			positions=httprequest.getHttpServices().sendGetHttpRequestGoogle(url);	
		}
		httprequest.getHttpServices().setCounter(0);
		
		for(int i=0;i<json.getVitesse();i++)
			try{
		positions.addFirst(new String[]{request.getParameter("lat1"),request.getParameter("lon1")}); 
			}catch (Exception e) {
                System.out.println("check MapQuest");
            }
		json= gps.createJSON(//gps.getMacAddress()
				request.getParameter("Id"), positions, false);
		String strSpeed=String.valueOf(speed);
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
			httprequest.setAll(json,defaultServer,json.getPositions().size());
			while(httprequest.getHttpServices().getErrorCodeMapQuest()==0) Thread.sleep(100);
			codeMapQuest=httprequest.getHttpServices().getErrorCodeMapQuest();
			
			model.addAttribute("CodeMapQuest",codeMapQuest);
			
			
			httprequest.demarrer();
		
			httprequest.start();
		}catch (Exception e){
			System.out.println("MakeRoute "+e.toString() );
		}
		
		
		
		return "MakeRoute";
	}

	//Stop Service
	public void fakePositionStop()
	{
		httprequest.arret();

	}
	//change info /* for one position */
	public void fakesetNewInfo(String Id,String lat,String lon){
		positions = new LinkedList<String[]>();
		positions.add(new String[]{lat,lon});

		json.setPositions(positions);

		httprequest.setJsonEntitie(json);
	}

	@RequestMapping(value = "/SendGPS", method = RequestMethod.POST)
	public String fakePosition(Model model,HttpServletRequest request){
		if(httprequest.isRunning()){
			fakesetNewInfo(request.getParameter("Id"),request.getParameter("lat"),
					request.getParameter("lon"));
		}
		else fakePositionStart(request.getParameter("Id"),request.getParameter("lat"),
				request.getParameter("lon"),false,defaultServer);

		model.addAttribute("Id",request.getParameter("Id"));
		model.addAttribute("lat",request.getParameter("lat") );
		model.addAttribute("lon", request.getParameter("lon"));
		String strSpeed=String.valueOf(speed);
		model.addAttribute("speed",strSpeed);
		
		return "SendGPS";

	}
	@RequestMapping(value = "/MyPosition")
	public String myPosition(Locale locale, Model model)  {

		return "MyPosition";
	}
	@RequestMapping(value = "/MyRoute")
	public String myRoute(Locale locale, Model model)  {

		return "MyRoute";
	}
	@RequestMapping(value = "/Config", method = RequestMethod.GET)
	public String DefaultServer(Locale locale, Model model,HttpServletRequest request)  {
		
		return "defaultServer";
	}
//	@RequestMapping(value = "/Config", method = RequestMethod.POST)
//	public void config(Locale locale, Model model,HttpServletRequest request)  {
//		this.defaultServer=request.getParameter("defaultserver");
//		System.out.println(request.getParameter("defaultserver"));
//		
//	}
	@RequestMapping(value = "/Config",method=RequestMethod.POST)
	@ResponseBody
    public String setConfig(HttpServletRequest request, 
    		HttpServletResponse response) throws IOException{
        //System.out.println(defaultserver);
        //model.addAttribute("productBean", productBean);
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("code", 0);
		request.setCharacterEncoding("utf8");
		response.setContentType("application/json");
		this.defaultServer=request.getParameter("defaultserver");
        return jsonResponse.toJSONString();
    }
	
	
	@RequestMapping(value = "/getNewPosition", method = RequestMethod.GET)
	public @ResponseBody
	String getNewPosition() {
		if(httprequest.isRunning()){
			String [] position;
			try{
			if(httprequest.getHttpServices().getCounter()<json.getPositions().size()-1){

				position = json.getPositions().get(httprequest.getHttpServices().getCounter());		

			}
			else {
				if(json.getPositions().size()==1){
					
					position = json.getPositions().get(0);
				}
				else{
					position = json.getPositions().get(json.getPositions().size()-1);	
				httprequest.arret();}
			}
			}catch(Exception e){
				System.out.println("We can't get new position");
				position = json.getPositions().get(0);
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


	@RequestMapping(value = "/NewFile")
	public String Test(HttpServletRequest req,HttpServletResponse res,Locale locale, Model model) throws IOException  {
		
		System.out.println(req.getRealPath("/"));
		return "NewFile";
	}
}
