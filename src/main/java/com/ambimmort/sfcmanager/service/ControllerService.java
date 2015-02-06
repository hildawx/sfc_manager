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
public class ControllerService {

    public JSONObject getBusinessChainList() {
        JSONObject data = new JSONObject();
        JSONArray chains = new JSONArray();
        
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(Config.getInstance().get("controller.host")).append("/gn/sfc/controller/classifier/user/chainconfig/json");
        try {
            String resp = RestClient.getInstance().get(sb.toString());
            if (resp!=null) {
                JSONArray arr = JSONArray.fromObject(resp);
                Iterator it = arr.iterator();
                while (it.hasNext()) {
                    JSONArray item = new JSONArray();
                    JSONObject obj = (JSONObject) it.next();
                    item.add(obj.get("type"));
                    Iterator kit = obj.keySet().iterator();
                    while (kit.hasNext()) {
                        String key = (String) kit.next();
                        if (!"type".equals(key)) {
                            item.add(key);
                            JSONArray eleArr = obj.getJSONArray(key);
                            StringBuilder eleBuilder = new StringBuilder();
                            for (int i = 0; i < eleArr.size(); i++) {
                                if (i != 0) {
                                    eleBuilder.append(',');
                                }
                                eleBuilder.append(eleArr.getJSONObject(i).getString("element"));
                            }
                            item.add(eleBuilder.toString());
                        }
                    }
                    chains.add(item);
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ClassifierService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        data.put("aaData", chains);
        return data;
    }

    
}
