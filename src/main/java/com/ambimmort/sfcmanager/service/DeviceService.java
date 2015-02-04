/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ambimmort.sfcmanager.service;

import com.ambimmort.sfcmanager.entity.Device;
import com.ambimmort.sfcmanager.util.ControllerManager;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;

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

    public JSONArray getAllDevice() {
        return ControllerManager.getInstance().getControllers();
    }

    public JSONArray getDeviceByType(String deviceType) {
        return ControllerManager.getInstance().getControllersByType(deviceType);
    }
    
}
