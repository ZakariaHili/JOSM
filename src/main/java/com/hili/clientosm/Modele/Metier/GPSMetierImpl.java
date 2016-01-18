package com.hili.clientosm.Modele.Metier;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONObject;
import com.hili.clientosm.Modele.Dao.JSONEntitieDAO;
import com.hili.clientosm.Modele.Dao.SettingsDao;
/**
 * Created by HILI on 05/11/2015.
 */
public class GPSMetierImpl implements  GPSMetier {


	private int countfailed;
	public int maxAcceptFailed=3;
	
	private JSONEntitieDAO jsonEntitieDAO;
	private SettingsDao settingsDao;


	public GPSMetierImpl(){
		countfailed =3;
		
	}

	//Convert GPS Coordinates TO String

	@Override
	public String jsonGPSCoordinateToString(int counter) {
		try {

			JSONObject position = new JSONObject();

			if(jsonEntitieDAO.getPositions().size()>settingsDao.getCarSpeed()&&counter<jsonEntitieDAO.getPositions().size()){

				position.put("lat", Double.valueOf(jsonEntitieDAO.getPositions().get(counter)[0]));
				position.put("lon", Double.valueOf(jsonEntitieDAO.getPositions().get(counter)[1]));	

			}
			else if(counter>jsonEntitieDAO.getPositions().size()-1){
				position.put("lat", Double.valueOf(jsonEntitieDAO.getPositions().get(jsonEntitieDAO.getPositions().size()-1)[0]));
				position.put("lon", Double.valueOf(jsonEntitieDAO.getPositions().get(jsonEntitieDAO.getPositions().size()-1)[1]));

			}
			else{
				position.put("lat", Double.valueOf(jsonEntitieDAO.getPositions().get(0)[0]));
				position.put("lon", Double.valueOf(jsonEntitieDAO.getPositions().get(0)[1]));
			}
			JSONObject client = new JSONObject();


			JSONObject json = new JSONObject();
			json.put("isServer", jsonEntitieDAO.isServer());
			json.put("client", client);
			client.put("ID", jsonEntitieDAO.getId());
			client.put("Position", position);


			return json.toString();
		}catch (Exception e){
			System.out.println("Ouups : createJSON"+ e.toString());
			return null;
		}
	}




	//	create the JSONEntitie by id, positions ,isServer
	@Override
	public void createJSONEntitie(String id, LinkedList<String[]>positions, boolean isServer) {
		jsonEntitieDAO.setId(id);
		jsonEntitieDAO.setIsServer(isServer);
		jsonEntitieDAO.setPositions(positions);
	}


	// Parse Response From Server
	@Override
	public int parseResponse(String jsonString, int codeResponse) {
		try {
			JSONObject jsonRootObject = new JSONObject(jsonString);

			String server;

			if (codeResponse == 303) {
				JSONArray jsonArray = jsonRootObject.optJSONArray("servers");
				JSONObject jsonObject;
				// the server can send many server
				//so we should to make the first one as default and we put others in the reserve list
				for (int i = 0; i < jsonArray.length(); i++) {
					jsonObject = jsonArray.getJSONObject(i);
					server=jsonObject.optString("IP").toString();
					if(i==0){
						//make the first server as default server
						jsonEntitieDAO.setDefaultserver(server);
					}
					//to avoid duplication if we saved another server in the reserve list
					if(jsonEntitieDAO.isServerIn(server)==false){
						jsonEntitieDAO.addServer(server);
					}

				}
				return 1;
			}//after 3 fails we should to contact another server
			else if(codeResponse==404) {
				if(countfailed>0){
					countfailed--;}
				else{
					if(jsonEntitieDAO.getServers().size()>1){
						jsonEntitieDAO.removeServer(jsonEntitieDAO.getDefaultserver());
						jsonEntitieDAO.newDefaultServer();
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


	//Generate MacAddress
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
	
	@Override
	public int increment(int counter) {
		return counter+settingsDao.getCarSpeed();
	}
	@Override
	public String getDefaultServer() {
		return jsonEntitieDAO.getDefaultserver();
	}
	//Setters for Spring Dependency injection
	public void setJsonEntitieDAO(JSONEntitieDAO jsonEntitieDAO){
		this.jsonEntitieDAO=jsonEntitieDAO;
	}
	public void setSettingsDao(SettingsDao settingsDao){
		this.settingsDao=settingsDao;
	}

	public JSONEntitieDAO getJsonEntitieDAO() {
		return jsonEntitieDAO;
	}

	public SettingsDao getSettingsDao() {
		return settingsDao;
	}

	@Override
	public int getCarSpeed() {
		return settingsDao.getCarSpeed();
	}



	
}