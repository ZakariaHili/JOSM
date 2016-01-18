package com.hili.clientosm.Modele.Metier;

import java.util.LinkedList;

import com.hili.clientosm.Modele.Dao.JSONEntitieDAO;
import com.hili.clientosm.Modele.Dao.SettingsDao;

/**
 * Created by HILI on 05/11/2015.
 */
public interface GPSMetier {

	void createJSONEntitie(String id, LinkedList<String[]> positions, boolean isServer);

	String jsonGPSCoordinateToString(int counter);

	int parseResponse(String jsonString, int codeResponse);

	int increment(int counter);

	String getDefaultServer();

	int getCarSpeed() ;

	String getMacAddress();
	void setSettingsDao(SettingsDao settingsDao);
	JSONEntitieDAO getJsonEntitieDAO();
	SettingsDao getSettingsDao();
	void setJsonEntitieDAO(JSONEntitieDAO jsonEntitieDAO);
}
