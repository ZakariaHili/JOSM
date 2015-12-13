package com.hili.clientosm.Controllers;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hili.clientosm.Entities.JSONEntitie;
import com.hili.clientosm.Metier.GPSMetierImpl;
import com.hili.clientosm.Services.HttpServicesImpl;
import com.hili.clientosm.Services.HttprequestThread;



/**
 * Created by HILI on 05/11/2015.
 */
@Controller
public class GPSController {
    
	public static String defaultServer="8.8.8.8";
    private GPSMetierImpl gps ;
    private HttpServicesImpl services;
    private HttprequestThread httprequest ;
    private LinkedList<String[]> positions;
    private JSONEntitie json;
    
    public GPSController (){
        gps = new GPSMetierImpl();
        services = new HttpServicesImpl();
        httprequest =new HttprequestThread();

    }

    // Send one Position for each 10 sec
    public void fakePositionStart(String Id,String lat,String lon, boolean isServer,String url){
    	positions = new LinkedList<String[]>();
    	positions.add(new String[]{lat,lon});
    	
         json= gps.createJSON(Id, positions, isServer);
        
        try {

            httprequest.setAll(json,url,0);
            httprequest.demarrer();
            httprequest.start();
        }catch (Exception e){
            System.out.println("Error"+ e.toString());
        }
    }
    
    //Route Sender
    @RequestMapping(value = "/MakeRoute", method = RequestMethod.POST)
    public String fakeRoutesStart(Model model,HttpServletRequest request){

        json= new JSONEntitie();
        json.setDefaultserver(defaultServer);
        String url=gps.makeLinkForRoutes(request.getParameter("lat1"),
        		request.getParameter("lon1"),request.getParameter("lat2"), 
        		request.getParameter("lon2"));
        positions=services.sendGetHttpRequest(url);
        
        json= gps.createJSON(gps.getMacAddress(), positions, false);
        model.addAttribute("Id",request.getParameter("Id"));
        model.addAttribute("lat1",request.getParameter("lat1") );
        model.addAttribute("lon1", request.getParameter("lon1"));
        model.addAttribute("lat2",request.getParameter("lat2") );
        model.addAttribute("lon2", request.getParameter("lon2")); 
        List<String[]> myList = new ArrayList<String[]>(positions);
        model.addAttribute("positions",myList );
       
        try {
        	
            httprequest.setAll(json,defaultServer,json.getPositions().size());
        	
			
            httprequest.demarrer();
            
            httprequest.start();
        }catch (Exception e){
            System.out.println("Error"+ e.toString());
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

    @RequestMapping(value = "/getNewPosition", method = RequestMethod.GET)
    public @ResponseBody
    String getNewPosition() {
    	if(httprequest.isRunning()){
    	  String [] position = json.getPositions().get(httprequest.getHttpServices().getCounter());
    	
          return "{\"position\":["+position[0]+","+position[1]+"]}";}
    	return null;
    }
  
	


}
