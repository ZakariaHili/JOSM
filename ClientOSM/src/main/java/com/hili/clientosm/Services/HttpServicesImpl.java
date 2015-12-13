package com.hili.clientosm.Services;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;



import com.hili.clientosm.Entities.JSONEntitie;
import com.hili.clientosm.Metier.GPSMetierImpl;


public class HttpServicesImpl  implements HttpServices {
	private int counter;
	private int maxRoutes;
	private GPSMetierImpl gpsMetier;

	public HttpServicesImpl(){
		gpsMetier =new GPSMetierImpl();
		counter=0;
		maxRoutes=0;
	}
	


	public int getCounter() {
		return counter;
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
	public void sendPostHttpRequest(JSONEntitie jsonEntitie,String url)  {


		try {
			final URL obj;

			final String message= gpsMetier.jsonGPSCoordinateToString(jsonEntitie,counter);
/*
			if(jsonEntitie.getDefaultserver().isEmpty())
				obj= new URL("http://"+url+":8080/OSM/SendGPS");
			else obj= new URL("http://"+jsonEntitie.getDefaultserver()+":8080/OSM/SendGPS");
*/
			obj= new URL("http://httpbin.org/post");

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
			con.setRequestProperty("Accept","*/*");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String urlParameters = message;
			con.setInstanceFollowRedirects(false);
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			System.out.print("information"+urlParameters);
			int responseCode = con.getResponseCode();

			System.out.print("Response Code : "+ String.valueOf(responseCode));
			//Get Response
			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();                     
			con.disconnect();
			
			
			System.out.println("  Response  : "+ response.toString());
			
			if(gpsMetier.stringToJSON(jsonEntitie,response.toString(),responseCode)==1) {
				url =jsonEntitie.getDefaultserver();
				sendPostHttpRequest(jsonEntitie,url);
			}
			counter++;
		}catch (Exception e){

			System.out.print("error sendHttpRequest"+ e.toString()+"\n");
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

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			
			con.disconnect();
			
			return gpsMetier.jsontoLinkedList(response.toString());
		}catch (Exception e){

			System.out.print(" error sendHttpRequest"+ e.toString()+"\n");
		}
		return null;
	}

}
