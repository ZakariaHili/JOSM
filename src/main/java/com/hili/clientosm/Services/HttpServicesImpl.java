package com.hili.clientosm.Services;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import com.hili.clientosm.Modele.Metier.APIMetier;
import com.hili.clientosm.Modele.Metier.GPSMetier;



public class HttpServicesImpl  implements HttpServices {
	private int counter;
	private int maxRoutes;
	private GPSMetier gpsMetier;
	private APIMetier apiMetier;

	private int errorAPI;
	private int code;
	public HttpServicesImpl(){
		//	gpsMetier =new GPSMetierImpl();
		counter=0;
		maxRoutes=0;
	}




	public int getCounter() {
		return counter;
	}



	public  int getCode() {
		return code;
	}




	public int getErrorAPI() {
		return errorAPI;
	}




	public void setErrorAPI(int errorAPI) {
		this.errorAPI = errorAPI;
	}




	public  void setCode(int code) {
		this.code = code;
	}




	public void setCounter(int counter) {
		this.counter = counter;
	}





	public int getMaxRoutes() {
		return maxRoutes;
	}



	public void setMaxRoutes(int maxRoutes) {
		this.maxRoutes = maxRoutes;
	}



	@Override
	public void sendPostHttpRequest(String url)  {

		try {
			URL obj;
			String defaultserver=gpsMetier.getDefaultServer();
			final String message= gpsMetier.jsonGPSCoordinateToString(counter);

//						if(defaultserver.isEmpty())
//							obj= new URL("http://"+url+":8080/SendGPS");
//						else obj= new URL("http://"+defaultserver+":8080/SendGPS");
			obj= new URL("http://httpbin.org/post");

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
			con.setRequestProperty("Accept","*/*");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String urlParameters = message;
			//stop AutoRedirect
			con.setInstanceFollowRedirects(false);
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			System.out.print("Data envoyé: "+urlParameters);

			int responseCode = con.getResponseCode();
			counter=gpsMetier.increment(counter);
			code=responseCode;
			setCode(responseCode);
			System.out.println(" Response Code : "+ String.valueOf(responseCode));

			if(responseCode==303||responseCode==200){
				//Get Response
				BufferedReader in = new BufferedReader(
						new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}

				in.close();         



				if(errorAPI==200 && responseCode==303){
					
					if(gpsMetier.parseResponse(response.toString(),responseCode)==1) {
						url =gpsMetier.getDefaultServer();
						sendPostHttpRequest(url);
					}
				}
			}

			else
				System.out.print(" Error SendHttpRequest "+code+" \n");
			con.disconnect();
		}catch (Exception e){

			System.out.print(" Error SendHttpRequest "+e.getMessage()+"\n");

		}
	}


	@Override
	public LinkedList<String[]> sendGetHttpRequest(String url,String nameAPI)  {

		try {
			final URL obj;

			obj= new URL(url);
			LinkedList<String[]> list=new LinkedList<String[]>();
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			//add reuqest header
			con.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;

			StringBuffer response = new StringBuffer();
			errorAPI = con.getResponseCode();

			if(errorAPI==200){
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				con.disconnect();
				if(nameAPI.compareTo("Google")==0){
					list= apiMetier.jsontoLinkedListGoogle(response.toString(),errorAPI);
					setMaxRoutes(list.size());
					return list;
					}
				else
					if(nameAPI.compareTo("MapQuest")==0){
						list= apiMetier.jsontoLinkedListMapQuest(response.toString(),errorAPI);
						setMaxRoutes(list.size());
						return list;
					}
				
			}
			else
				con.disconnect();

			return new LinkedList<String[]>();
		}catch (Exception e){

			//System.out.print(" error sendHttpGetRequest"+ e.toString()+"\n");
			//e.getStackTrace();
			System.out.println("  Can not connect Mapquest (check your proxy) ");
		}
		return null;
	}






	public void setGpsMetier(GPSMetier gpsMetier) {
		this.gpsMetier = gpsMetier;
	}




	public void setApiMetier(APIMetier apiMetier) {
		this.apiMetier = apiMetier;
	}

	public int getCarSpeed() {
		
		return gpsMetier.getCarSpeed();
	}




	public GPSMetier getGpsMetier() {
		return gpsMetier;
	}




	public APIMetier getApiMetier() {
		return apiMetier;
	}



}
