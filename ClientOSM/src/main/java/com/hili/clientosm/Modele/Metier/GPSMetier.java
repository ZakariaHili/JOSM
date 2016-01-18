package com.hili.clientosm.Modele.Metier;

import java.util.LinkedList;


import com.hili.clientosm.Modele.Entities.JSONEntitie;

/**
 * Created by HILI on 05/11/2015.
 */
public interface GPSMetier {
    JSONEntitie createJSON(String Id, LinkedList<String[]> positions, boolean isServer);
    String jsonGPSCoordinateToString(JSONEntitie jsonEntitie,int counter);
    int stringToJSON(JSONEntitie jsonEntitie,String jsonString, int codeResponse);
    LinkedList<String[]> jsontoLinkedList(String jsonString);
    String makeLinkForRoutes(String lat1,String lon1,String lat2,String lon2);
    String makeLinkForRoutesGoogle(String lat1, String lon1, String lat2, String lon2);
    LinkedList<String[]> jsontoLinkedListGoogle(String jsonString);
    String getMacAddress();
}
