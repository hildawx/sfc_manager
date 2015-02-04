/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ambimmort.sfcmanager.service;

import com.ambimmort.sfcmanager.entity.Device;
import com.ambimmort.sfcmanager.util.ControllerManager;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class DeviceService {

    public boolean addDevice(String ip, String port, String type) {
        try {
            ControllerManager.getInstance().addController(ip, port, type);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(DeviceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public JSONObject getAllDevice() {
        Iterator it = ControllerManager.getInstance().getControllers().iterator();
        JSONArray data = new JSONArray();
        while (it.hasNext()) {
            JSONArray item = new JSONArray();
            Device d = (Device) it.next();
            item.add(d.getIp());
            item.add(d.getPort());
            item.add(d.getType());
            data.add(item);
        }
        JSONObject ob = new JSONObject();
        ob.put("aaData", data);
        return ob;
    }

    public JSONObject getDeviceByType(String deviceType) {
        Iterator it = ControllerManager.getInstance().getControllersByType(deviceType).iterator();
        JSONArray data = new JSONArray();
        while (it.hasNext()) {
            JSONArray item = new JSONArray();
            Device d = (Device) it.next();
            item.add(d.getIp());
            item.add(d.getPort());
            item.add(d.getType());
            data.add(item);
        }
        JSONObject ob = new JSONObject();
        ob.put("aaData", data);
        return ob;
    }

    public boolean delDevice(String ip, String port, String type) {
        try {
            ControllerManager.getInstance().removeController(ip, port, type);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(DeviceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
