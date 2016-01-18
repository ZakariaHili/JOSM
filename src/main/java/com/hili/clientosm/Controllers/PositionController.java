package com.hili.clientosm.Controllers;

import java.util.LinkedList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hili.clientosm.Services.HttprequestThread;
@Controller
public class PositionController extends MainController{

	private HttprequestThread httprequest ;
	private LinkedList<String[]> positions;

	
	public PositionController() {
		super();
		httprequest = super.getHttprequest();
		positions= new LinkedList<String[]>();

	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model)  {
		
		return "home";
	}
	
	@RequestMapping(value = "/SendGPS", method = RequestMethod.POST)
	public String fakePosition(Model model,HttpServletRequest request){
		
		String defaultServer= httprequest.getHttpServices().getGpsMetier().getJsonEntitieDAO().getDefaultserver();
		
		if(!super.readSettings()){
		return "redirect:/Config";	
		}
		else{
		int delay=httprequest.getHttpServices().getGpsMetier().getSettingsDao().getDelay();
		if(httprequest.isRunning()){
			fakesetNewInfo(request.getParameter("Id"),request.getParameter("lat"),
					request.getParameter("lon"));
		}
		else fakePositionStart(request.getParameter("Id"),request.getParameter("lat"),
				request.getParameter("lon"),false,defaultServer);

		model.addAttribute("Id",request.getParameter("Id"));
		model.addAttribute("lat",request.getParameter("lat") );
		model.addAttribute("lon", request.getParameter("lon"));
		String strSpeed=String.valueOf(delay);
		model.addAttribute("speed",strSpeed);
		
		return "SendGPS";
		}
	}
	@RequestMapping(value = "/MyPosition")
	public String myPosition(Locale locale, Model model)  {

		return "MyPosition";
	}
	
	// Send one Position for each 10 sec
	public void fakePositionStart(String id,String lat,String lon, boolean isServer,String url){
		positions = new LinkedList<String[]>();
		positions.add(new String[]{lat,lon});
		httprequest.getHttpServices().setCounter(0);
		
		httprequest.getHttpServices().getGpsMetier().createJSONEntitie(id, positions, isServer);

		try {
			httprequest.arret();
			httprequest.setAll(url,1000);
			httprequest.demarrer();
			
		}catch (Exception e){
			System.out.println("Error"+ e.toString());
		}
	}
	
	//change info /* for one position */
	public void fakesetNewInfo(String Id,String lat,String lon){
		positions = new LinkedList<String[]>();
		positions.add(new String[]{lat,lon});

		httprequest.getHttpServices().getGpsMetier().getJsonEntitieDAO().setPositions(positions);

		
	}

	
}
