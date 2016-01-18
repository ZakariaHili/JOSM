package com.hili.clientosm.Services;

import java.io.IOException;
import java.util.LinkedList;

import com.hili.clientosm.Modele.Metier.APIMetier;
import com.hili.clientosm.Modele.Metier.GPSMetier;

/**
 * Created by HILI on 05/11/2015.
 */
public interface HttpServices {

    void sendPostHttpRequest(String url) throws IOException;
    LinkedList<String[]> sendGetHttpRequest(String url,String nameAPI) throws IOException;
    void setMaxRoutes(int maxRoutes);
    int getMaxRoutes();
    int getCounter();
    int getCode();
    int getErrorAPI();
    void setCounter(int counter);
    int getCarSpeed() ;
    void setGpsMetier(GPSMetier gpsMetier);
    void setApiMetier(APIMetier apiMetier);
    GPSMetier getGpsMetier();
    APIMetier getApiMetier();
}
