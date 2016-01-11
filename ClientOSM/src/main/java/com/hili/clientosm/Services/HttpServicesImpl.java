package com.hili.clientosm.Services;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;



import com.hili.clientosm.Modele.Entities.JSONEntitie;
import com.hili.clientosm.Modele.Metier.GPSMetierImpl;


public class HttpServicesImpl  implements HttpServices {
	private int counter;
	private int maxRoutes;
	private GPSMetierImpl gpsMetier;
	
	private static int errorCodeMapQuest;
	private static int code;
	public HttpServicesImpl(){
		gpsMetier =new GPSMetierImpl();
		counter=0;
		maxRoutes=0;
	}




	public int getCounter() {
		return counter;
	}



	public static int getCode() {
		return code;
	}




	public static void setCode(int code) {
		HttpServicesImpl.code = code;
	}




	public void setCounter(int counter) {
		this.counter = counter;
	}



	public  int getErrorCodeMapQuest() {
		return errorCodeMapQuest;
	}



	public static void setErrorCodeMapQuest(int CodeMapQuest) {
		errorCodeMapQuest =CodeMapQuest;	}



	public int getMaxRoutes() {
		return maxRoutes;
	}



	public void setMaxRoutes(int maxRoutes) {
		this.maxRoutes = maxRoutes;
	}



	@Override
	public void sendPostHttpRequest(JSONEntitie jsonEntitie,String url)  {


		try {
			final URL obj;

			final String message= gpsMetier.jsonGPSCoordinateToString(jsonEntitie,counter);

			if(jsonEntitie.getDefaultserver().isEmpty())
				obj= new URL("http://"+url+":8080/SendGPS");
			else obj= new URL("http://"+jsonEntitie.getDefaultserver()+":8080/SendGPS");
		//	obj= new URL(url);
			//obj= new URL(url);

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
			counter=counter+jsonEntitie.getVitesse();
			code=responseCode;
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
				System.out.println("  Response  : "+ response.toString());
				in.close();         


				
				if(errorCodeMapQuest==200 && responseCode==303){

					if(gpsMetier.stringToJSON(jsonEntitie,response.toString(),responseCode)==1) {
						url =jsonEntitie.getDefaultserver();
						sendPostHttpRequest(jsonEntitie,url);
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
	public LinkedList<String[]> sendGetHttpRequest(String url)  {

		try {
			final URL obj;



			obj= new URL(url);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			//add reuqest header
			con.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;

			StringBuffer response = new StringBuffer();
			 errorCodeMapQuest = con.getResponseCode();
			
			if(errorCodeMapQuest==200){
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				con.disconnect();

				return gpsMetier.jsontoLinkedList(response.toString());
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

}
