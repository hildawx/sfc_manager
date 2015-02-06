/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ambimmort.sfcmanager.service;

import com.ambimmort.sfcmanager.util.Config;
import com.ambimmort.sfcmanager.util.RestClient;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
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

    public boolean addParentAccount(String config) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(Config.getInstance().get("classifier.host")).append("/gn/classifier/account/config/parent/json");
        try {
            String resp = RestClient.getInstance().post(sb.toString(), config);
            if (resp != null) {
                JSONObject rs = JSONObject.fromObject(resp);
                if ("success".equals(rs.getString("status"))) {
                    return true;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClassifierService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean addBusinessChain(String ip, String ipType, String chain) {
        JSONObject data = new JSONObject();
        data.put("type", ipType);
        data.put(ip, JSONArray.fromObject(chain));
        
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(Config.getInstance().get("controller.host")).append("/gn/sfc/controller/classifier/user/chainconfig/json");
        try {
            String resp = RestClient.getInstance().post(sb.toString(), data.toString());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(ClassifierService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean delParentAccount(String pid) {
        JSONObject data = new JSONObject();
        data.put("pid", pid);
        
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(Config.getInstance().get("classifier.host")).append("/gn/classifier/account/config/parent/json");
        try {
            String resp = RestClient.getInstance().delete(sb.toString(), data.toString());
            if (resp != null) {
                JSONObject rs = JSONObject.fromObject(resp);
                if ("success".equals(rs.getString("status"))) {
                    return true;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClassifierService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public JSONObject getParentAccountList() {
        JSONObject rtObj = new JSONObject();
        JSONArray accounts = new JSONArray();
        
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(Config.getInstance().get("classifier.host")).append("/gn/classifier/account/config/parent/json");
        try {
            String resp = RestClient.getInstance().get(sb.toString());
            if (resp != null) {
                JSONArray rs = JSONArray.fromObject(resp);
                Iterator it = rs.iterator();
                while (it.hasNext()) {
                    JSONObject o = (JSONObject) it.next();
                    JSONArray item = new JSONArray();
                    item.add(o.get("pid"));
                    item.add(o.get("username"));
                    item.add(o.get("passwd"));
                    accounts.add(item);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClassifierService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        rtObj.put("aaData", accounts);
        return rtObj;
    }
    
}
