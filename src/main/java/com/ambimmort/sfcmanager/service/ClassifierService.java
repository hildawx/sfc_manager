/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ambimmort.sfcmanager.service;

import com.ambimmort.sfcmanager.util.RestClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class ClassifierService {
    
    public static final String ELEMENT_INFO = "/gn/sfc/controller/element/json";

    public JSONObject getClassifierInfo(String ip, String port) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(':').append("port").append(ELEMENT_INFO);
        
        try {
            String resp = RestClient.getInstance().get(sb.toString());
        } catch (IOException ex) {
            Logger.getLogger(ClassifierService.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
