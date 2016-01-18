package com.hili.clientosm.Modele.Dao;



import com.hili.clientosm.Modele.Entities.Settings;

public class SettingsDaoImpl implements SettingsDao {

	Settings settings;
	public SettingsDaoImpl() {
		settings= new Settings();
	}

	@Override
	public String getDefaultServer() {
		return settings.getDefaultServer();
	}

	@Override
	public void setDefaultServer(String defaultServer) {
		settings.setDefaultServer(defaultServer);


	}

	@Override
	public int getDelay() {
		return settings.getDelay();
	}

	@Override
	public void setDelay(int delay) {
		settings.setDelay(delay);

	}

	@Override
	public int getCarSpeed() {
		return settings.getCarSpeed();
	}

	@Override
	public void setCarSpeed(int carSpeed) {
		settings.setCarSpeed(carSpeed);

	}





}
