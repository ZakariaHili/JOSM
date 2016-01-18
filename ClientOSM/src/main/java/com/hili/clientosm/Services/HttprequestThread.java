package com.hili.clientosm.Services;


public interface HttprequestThread {
	
	void setHttpServices(HttpServices httpServices);
	HttpServices getHttpServices();
	void setAll(String url,int maxRoutes);
	void setUrl(String url);
	void arret();
	void demarrer();
	boolean isRunning ();

}
