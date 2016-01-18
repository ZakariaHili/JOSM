package com.hili.clientosm.Modele.Dao;

import java.util.LinkedList;

import com.hili.clientosm.Modele.Entities.JSONEntitie;

public class JSONEntitieDAOImpl implements JSONEntitieDAO {

	JSONEntitie jsonEntitie;

	public JSONEntitieDAOImpl() {
		jsonEntitie= new JSONEntitie();
	}

	@Override
	public void setId(String id) {
		jsonEntitie.setId(id);

	}

	@Override
	public void setIsServer(boolean isServer) {
		jsonEntitie.setIsServer(isServer);

	}

	@Override
	public void setServers(LinkedList<String> servers) {
		jsonEntitie.setServers(servers);

	}

	@Override
	public void addServer(int id, String server) {
		jsonEntitie.addServer(server);

	}

	@Override
	public void addServer(String server) {
		jsonEntitie.addServer( server);

	}

	@Override
	public String getId() {
		return jsonEntitie.getId();
	}

	@Override
	public boolean isServer() {
		return jsonEntitie.isServer();
	}

	@Override
	public LinkedList<String> getServers() {
		return jsonEntitie.getServers();
	}

	@Override
	public void removeServer(String server) {
		jsonEntitie.removeServer(server);

	}

	@Override
	public String getDefaultserver() {
		return jsonEntitie.getDefaultserver();
	}

	@Override
	public void setDefaultserver(String defaultserver) {
		jsonEntitie.setDefaultserver(defaultserver);

	}

	@Override
	public boolean isServerIn(String server) {
		return jsonEntitie.isServer();
	}

	@Override
	public void newDefaultServer() {
		jsonEntitie.newDefaultServer();

	}

	@Override
	public LinkedList<String[]> getPositions() {
		return jsonEntitie.getPositions();
	}

	@Override
	public void restPositions() {
		jsonEntitie.restPositions();

	}

	@Override
	public void setPositions(LinkedList<String[]> linkedList) {
		jsonEntitie.setPositions(linkedList);

	}

	@Override
	public void setServer(boolean isServer) {
		jsonEntitie.setServer(isServer);

	}

}
