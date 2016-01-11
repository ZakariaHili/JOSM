package com.hili.clientosm.Controllers;



import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	public static String defaultServer="http://httpbin.org/post";//172.20.0.69";//192.168.23.123"; //10.42.0.71";//"https://httpbin.org/post";
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
		
		positions=httprequest.getHttpServices().sendGetHttpRequest(url);
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
			httprequest.arret();
			httprequest.setAll(json,defaultServer,json.getPositions().size());
			while(httprequest.getHttpServices().getErrorCodeMapQuest()==0) Thread.sleep(1);
			codeMapQuest=httprequest.getHttpServices().getErrorCodeMapQuest();
			
			model.addAttribute("CodeMapQuest",codeMapQuest);
			
			
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

	@RequestMapping(value = "/getNewPosition", method = RequestMethod.GET)
	public @ResponseBody
	String getNewPosition() {
		if(httprequest.isRunning()){
			String [] position;
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
