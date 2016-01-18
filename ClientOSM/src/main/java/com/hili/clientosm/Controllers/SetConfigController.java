package com.hili.clientosm.Controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hili.clientosm.Modele.Metier.GPSMetier;
import com.hili.clientosm.Services.HttprequestThread;

@Controller
public class SetConfigController extends MainController {


	Properties prop;
	OutputStream output = null;
	

	private GPSMetier gpsMetier;
	private HttprequestThread httprequest ;

	public SetConfigController() {
		super();
		prop = new Properties();
		
		gpsMetier = super.getGpsMetier();
		httprequest = super.getHttprequest();

	}

	public void newSettings(){

		try{
			output = new FileOutputStream("config.properties");
			output.flush();
			// set the properties value
			String defaultServer=gpsMetier.getDefaultServer();
			String carSpeed=String.valueOf(gpsMetier.getCarSpeed());
			String delay =String.valueOf(gpsMetier.getSettingsDao().getDelay());
			prop.setProperty("DefaultServer", defaultServer);
			prop.setProperty("speed", carSpeed);
			prop.setProperty("delay", delay);
			
			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}


	//Configure the application
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Config",method=RequestMethod.POST)
	@ResponseBody
	public String setConfig(HttpServletRequest request, 
			HttpServletResponse response) throws IOException{
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("code", 0);
		request.setCharacterEncoding("utf8");
		response.setContentType("application/json");
		String defaultServer=request.getParameter("defaultserver");
		gpsMetier.getSettingsDao().setDefaultServer(defaultServer);
		gpsMetier.getJsonEntitieDAO().setDefaultserver(defaultServer);
		
		int delay=Integer.valueOf(request.getParameter("delay"));
		int carSpeed= Integer.valueOf(request.getParameter("Vitesse"));
		gpsMetier.getSettingsDao().setDelay(delay);
		gpsMetier.getSettingsDao().setCarSpeed(carSpeed);
		httprequest.getHttpServices().setGpsMetier(gpsMetier);
		newSettings();
		return jsonResponse.toJSONString();
	}
	
	
	@RequestMapping(value = "/Config", method = RequestMethod.GET)
	public String DefaultServer(Locale locale, Model model,HttpServletRequest request)  {
		
		return "defaultServer";
	}

}
