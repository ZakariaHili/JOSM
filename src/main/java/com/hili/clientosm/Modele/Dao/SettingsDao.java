package com.hili.clientosm.Modele.Dao;


public interface SettingsDao {


	String getDefaultServer() ;

	void setDefaultServer(String defaultServer);

	int getDelay();

	void setDelay(int delay);

	int getCarSpeed();

	void setCarSpeed(int carSpeed);



}
