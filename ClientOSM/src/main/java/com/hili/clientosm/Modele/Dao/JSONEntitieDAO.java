package com.hili.clientosm.Modele.Dao;

import java.util.LinkedList;

public interface JSONEntitieDAO {
	
	
	void setId(String id) ;
	void setIsServer(boolean isServer);
	void setServers(LinkedList<String> servers);
	void addServer(int id,String server);
	void addServer(String server);
	String getId();
	boolean isServer();
	LinkedList<String> getServers();
	void removeServer(String server);
	String getDefaultserver() ;
	 void setDefaultserver(String defaultserver);
	 boolean isServerIn(String server);
	 void newDefaultServer();
	 LinkedList<String[]> getPositions() ;
	 void restPositions();
	 void setPositions(LinkedList<String[]> linkedList);
	 void setServer(boolean isServer);

}
