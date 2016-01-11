package com.hili.clientosm.Modele.Metier;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hili.clientosm.Modele.Entities.JSONEntitie;

/**
 * Created by HILI on 05/11/2015.
 */
public class GPSMetierImpl implements  GPSMetier {


	private static int countfailed;
	final static int maxAcceptFailed=3;


	public GPSMetierImpl(){
		countfailed =3;
	}

	//Convert GPS Coordinates TO String
	@Override
	public String jsonGPSCoordinateToString(JSONEntitie jsonEntitie,int counter) {
		try {

			JSONObject position = new JSONObject();
			
			if(jsonEntitie.getPositions().size()>jsonEntitie.getVitesse()&&counter<jsonEntitie.getPositions().size()){
				
				position.put("lat", Double.valueOf(jsonEntitie.getPositions().get(counter)[0]));
				position.put("lon", Double.valueOf(jsonEntitie.getPositions().get(counter)[1]));	
				
			}
			else if(counter>jsonEntitie.getPositions().size()-1){
				position.put("lat", Double.valueOf(jsonEntitie.getPositions().get(jsonEntitie.getPositions().size()-1)[0]));
				position.put("lon", Double.valueOf(jsonEntitie.getPositions().get(jsonEntitie.getPositions().size()-1)[1]));
				
			}
			else{
				position.put("lat", Double.valueOf(jsonEntitie.getPositions().get(0)[0]));
				position.put("lon", Double.valueOf(jsonEntitie.getPositions().get(0)[1]));
			}
			JSONObject client = new JSONObject();
			
			
			JSONObject json = new JSONObject();
			json.put("isServer", jsonEntitie.isServer());
			json.put("client", client);
			client.put("ID", jsonEntitie.getId());
			client.put("Position", position);
			
			
			return json.toString();
		}catch (Exception e){
			System.out.println("Ouups : createJSON"+ e.toString());
			return null;
		}
	}


	@Override
	public JSONEntitie createJSON(String Id, LinkedList<String[]>positions, boolean isServer) {

		return new JSONEntitie(Id,positions,isServer);
	}


	// Parse Response From Server
	@Override
	public int stringToJSON(JSONEntitie jsonEntitie,String jsonString, int codeResponse) {
		try {
			JSONObject jsonRootObject = new JSONObject(jsonString);
			System.out.println(codeResponse);
			String server;
			if(codeResponse==200) {
				System.out.println(codeResponse);
				return 0;}
			else
			if (codeResponse == 303) {
				JSONArray jsonArray = jsonRootObject.optJSONArray("servers");
				JSONObject jsonObject;

				for (int i = 0; i < jsonArray.length(); i++) {
					jsonObject = jsonArray.getJSONObject(i);
					server=jsonObject.optString("IP").toString();
					if(i==0)
						jsonEntitie.setDefaultserver(server);

					if(jsonEntitie.isServerIn(server)==false){
						jsonEntitie.addServer(server);
					}

				}
				return 1;
			}
			else if(codeResponse==404) {
				if(countfailed>0){
					countfailed--;}
				else{
					if(jsonEntitie.getServers().size()>1){
						jsonEntitie.removeServer(jsonEntitie.getDefaultserver());
						jsonEntitie.newDefaultServer();
						countfailed=maxAcceptFailed;
					}
				}
			}

		}
		catch (Exception e){
			System.out.println("Help stringToJSON :'( "+e.toString());
		}
		return 0;
	}


	//Parse Response From API

	@Override
	public LinkedList<String[]> jsontoLinkedList(String jsonString) {
		try {
			

			LinkedList <String[]> list = new LinkedList<String[]>();

			jsonString= jsonString.substring(24, jsonString.length()-2);


			JSONObject jsonRootObject = new JSONObject(jsonString);
			JSONObject route = jsonRootObject.optJSONObject("route");
			try{
			JSONObject shape = route.getJSONObject("shape");

			JSONArray shapePoints=shape.getJSONArray("shapePoints");

			for (int i = 0; i < shapePoints.length(); i++) {
				
				
				list.add(new String[]{shapePoints.get(i).toString(),shapePoints.get(i+1).toString()});
				i++;
			}
			}catch(Exception e){
				System.out.println("JSONObject[shape] not found. => No Route found");
			}
			return list;


		}
		catch (Exception e){
			System.out.println("Help jsontoLinkedList :'( "+e.toString());
		}
		return null;
	}

	// Generate Link for API
	@Override
	public String makeLinkForRoutes(String lat1, String lon1, String lat2, String lon2) {

		String s= "http://www.mapquestapi.com/directions/v2/route?key=f4SfWZKaf0pHk2QxkAF7VqqNVY4AiBPp&callback="
				+ "renderAdvancedNarrative&ambiguities=ignore&avoidTimedConditions=false&doReverseGeocode=true&o"
				+ "utFormat=json&routeType=fastest&timeType=1&enhancedNarrative=true&shapeFormat=raw&generalize=0&"
				+ "locale=fr_FR&unit=k&from="+lat1+","+lon1+"&to="+lat2+","+lon2;
		return s.replace(" ", "%");

	}

	@Override
	public String getMacAddress() {
		InetAddress ip;
		try {

			ip = InetAddress.getLocalHost();

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();



			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));        
			}
			return sb.toString();

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (SocketException e){

			e.printStackTrace();

		}
		return null;

	}
}