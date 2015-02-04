/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ambimmort.sfcmanager.util;

import com.ambimmort.sfcmanager.entity.Device;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author Administrator
 */
public class ControllerManager {
    private static ControllerManager manager;
    private static final String CONTROLLER_FILE = "/devices.xml";
    private List<Device> controller;
    
    private ControllerManager() {
        this.controller = new ArrayList<Device>();
    }
    
    public List<Device> getControllers() {
        return controller;
    }
    
    public List<Device> getControllersByType(String type) {
        List rs = new ArrayList();
        Iterator<Device> it = controller.iterator();
        while (it.hasNext()) {
            Device dev = it.next();
            if (type.equals(dev.getType())) {
                rs.add(dev);
            }
        }
        return rs;
    }
    
    public void addController(String ip, String port, String type) throws IOException {
        Device o = new Device();
        o.setIp(ip);
        o.setPort(port);
        o.setType(type);
        controller.add(o);
        saveController();
    }
    
    public void removeController(String ip, String port, String type) throws IOException {
        Device d = new Device();
        d.setIp(ip);
        d.setPort(port);
        d.setType(type);
        
        Iterator<Device> it = controller.iterator();
        while (it.hasNext()) {
            Device o = it.next();
            if (o.equals(d)) {
                it.remove();
                break;
            }
        }
        saveController();
    }
    
    private void loadController() throws FileNotFoundException {
        String f = Config.getInstance().get("local_path") + CONTROLLER_FILE;
        InputStream is = new FileInputStream(f);
        SAXReader saxReader = new SAXReader();
        try {
            Document doc = saxReader.read(is);
            Element root = doc.getRootElement();

            Iterator<Element> it = root.elementIterator("device");
            while (it.hasNext()) {
                Element ele = it.next();
                Device d = new Device();
                d.setIp(ele.elementText("ip"));
                d.setPort(ele.elementText("port"));
                d.setType(ele.elementText("type"));
                controller.add(d);
            }
        } catch (DocumentException ex) {
            Logger.getLogger(ControllerManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(ControllerManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void saveController() throws IOException {
        String f = Config.getInstance().get("local_path") + CONTROLLER_FILE;
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(new FileWriter(f), format);
        
        Document doc = DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement("devices");
        doc.setRootElement(root);
        
        Iterator<Device> it = controller.iterator();
        while (it.hasNext()) {
            Device o = it.next();
            Element device = DocumentHelper.createElement("device");
            device.addElement("ip").setText(o.getIp());
            device.addElement("port").setText(o.getPort());
            device.addElement("type").setText(o.getType());
            root.add(device);
        }
        writer.write(doc);
        writer.close();
    }
    
    public static ControllerManager getInstance() {
        if (manager == null) {
            manager = new ControllerManager();
            try {
                manager.loadController();
            } catch (IOException ex) {
                Logger.getLogger(ControllerManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return manager;
    }
}
