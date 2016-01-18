package com.hili.clientosm.Controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import com.hili.clientosm.Modele.Metier.APIMetier;
import com.hili.clientosm.Modele.Metier.GPSMetier;
import com.hili.clientosm.Services.HttprequestThread;


@Controller
public class MainController {
	@Autowired(required=false)
	private HttprequestThread httprequest ;
	@Autowired(required=false)
	private GPSMetier gpsMetier;
	@Autowired(required=false)
	private APIMetier apiMetier;
	private static int codeMapQuest;
	private InputStream input = null;
	private Properties prop;

	public MainController(){
		ClassPathXmlApplicationContext context = 
				new ClassPathXmlApplicationContext(new String[]{"Config.xml"});
		httprequest = (HttprequestThread) context.getBean("HttprequestThread");
		gpsMetier = (GPSMetier) context.getBean("GPSMetier");
		apiMetier = (APIMetier) context.getBean("APIMetier");
		prop = new Properties();
	}


	public HttprequestThread getHttprequest() {
		return httprequest;
	}




	public boolean readSettings(){

		try{
			input = new FileInputStream("config.properties");

			// set the properties value
			String defaultServer;
			String carSpeed;
			String delay;
			prop.load(input);
			defaultServer=prop.getProperty("DefaultServer");
			carSpeed=prop.getProperty("speed");
			delay=prop.getProperty("delay");

			// save properties to project root folder
			gpsMetier.getJsonEntitieDAO().setDefaultserver(defaultServer);
			gpsMetier.getSettingsDao().setDefaultServer(defaultServer);
			gpsMetier.getSettingsDao().setCarSpeed(Integer.valueOf(carSpeed));
			gpsMetier.getSettingsDao().setDelay(Integer.valueOf(delay));
			httprequest.getHttpServices().setGpsMetier(gpsMetier);
			setGpsMetier(gpsMetier);
			return true;
		} catch (IOException io) {
			return false;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					return false;
				}
			}

		}

	}


	public void setHttprequest(HttprequestThread httprequest) {
		this.httprequest = httprequest;
	}


	public GPSMetier getGpsMetier() {
		return gpsMetier;
	}


	public void setGpsMetier(GPSMetier gpsMetier) {
		this.gpsMetier = gpsMetier;
	}


	public APIMetier getApiMetier() {
		return apiMetier;
	}


	public void setApiMetier(APIMetier apiMetier) {
		this.apiMetier = apiMetier;
	}


	public static int getCodeMapQuest() {
		return codeMapQuest;
	}


	public static void setCodeMapQuest(int codeMapQuest) {
		MainController.codeMapQuest = codeMapQuest;
	}







}
