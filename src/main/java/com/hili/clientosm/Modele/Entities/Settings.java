package com.hili.clientosm.Modele.Entities;

public class Settings {
	public  String defaultServer;///172.20.0.69";//192.168.23.123"; //10.42.0.71";//"http://httpbin.org/post";
	private  int delay;
	private int carSpeed;

	
	public Settings() {
		// TODO Auto-generated constructor stub
	}

	public  String getDefaultServer() {
		return defaultServer;
	}

	public  void setDefaultServer(String defaultServer) {
		this.defaultServer = defaultServer;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getCarSpeed() {
		return carSpeed;
	}

	public void setCarSpeed(int carSpeed) {
		this.carSpeed = carSpeed;
	}


	

}
