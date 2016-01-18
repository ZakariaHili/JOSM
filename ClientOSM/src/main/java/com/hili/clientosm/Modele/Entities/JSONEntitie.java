package com.hili.clientosm.Modele.Entities;


import java.util.LinkedList;

/**
 * Created by HILI on 05/11/2015.
 */
public class JSONEntitie {
	
    private String Id;
    private LinkedList<String[]> positions;
    private boolean isServer;
    private LinkedList<String> servers;
    private String defaultserver;

    
    
    
    public JSONEntitie() {
    	this.servers = new LinkedList<String>();
    }


    public JSONEntitie(String id, LinkedList<String[]>positions, boolean isServer) {

        this.Id = id;
        
        this.isServer = isServer;
        this.servers = new LinkedList<String>();
        this.positions=positions;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setIsServer(boolean isServer) {
        this.isServer = isServer;
    }

    public void setServers(LinkedList<String> servers) {
        this.servers = servers;
    }
    public void addServer(int id,String server){
        this.servers.add(id,server);
    }
    public void addServer(String server){
        this.servers.add(server);
    }
    public String getId() {
        return Id;
    }

    public boolean isServer() {
        return isServer;
    }

    public LinkedList<String> getServers() {
        return servers;
    }
    public void removeServer(String server){
        servers.remove(server);
    }

    public String getDefaultserver() {
        return defaultserver;
    }

    public void setDefaultserver(String defaultserver) {
        this.defaultserver = defaultserver;
    }

    public boolean isServerIn(String server){
        for(int i=0;i<servers.size();i++){
            if(servers.get(i).compareTo(server)==0)
                return true;
        }
        return false;
    }
    public void newDefaultServer(){
        defaultserver=servers.getLast();
    }

	public LinkedList<String[]> getPositions() {
		return positions;
	}
	public void restPositions(){
		positions = new LinkedList<String[]>();
	}

	public void setPositions(LinkedList<String[]> linkedList) {
		this.positions = linkedList;
	}

	public void setServer(boolean isServer) {
		this.isServer = isServer;
	}


    
}
