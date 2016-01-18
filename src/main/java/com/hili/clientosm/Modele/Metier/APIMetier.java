package com.hili.clientosm.Modele.Metier;

import java.util.LinkedList;

public interface APIMetier {
	
	 	LinkedList<String[]> jsontoLinkedListMapQuest(String jsonString,int errorAPI);
	    
	    String makeLinkForRoutesMapQuest(String lat1,String lon1,String lat2,String lon2);
	    
	    String makeLinkForRoutesGoogle(String lat1, String lon1, String lat2, String lon2);
	    
	    LinkedList<String[]> jsontoLinkedListGoogle(String jsonString,int errorAPI);

	    boolean isNoRoute();
}
