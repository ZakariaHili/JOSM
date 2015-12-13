package com.hili.clientosm.Services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import com.hili.clientosm.Entities.JSONEntitie;

/**
 * Created by HILI on 05/11/2015.
 */
public interface HttpServices {

    void sendPostHttpRequest(JSONEntitie jsonEntitie,String url) throws IOException;
    LinkedList<String[]> sendGetHttpRequest(String url) throws IOException;
}
