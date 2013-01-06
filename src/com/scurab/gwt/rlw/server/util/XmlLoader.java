package com.scurab.gwt.rlw.server.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.scurab.gwt.rlw.server.Queries;
import com.scurab.gwt.rlw.shared.model.PushMessage;

public class XmlLoader {

    public static PushMessage[] loadMessages(){
        List<PushMessage> subResult = new ArrayList<PushMessage>();
        
        String file = "/messages.xml";
        try {
            InputStream inputStream = Queries.class.getResourceAsStream(file);
    
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder;
        
            docBuilder = docBuilderFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = docBuilder.parse(inputStream);
            NodeList messages = doc.getElementsByTagName("Message");
            for (int i = 0; i < messages.getLength(); i++) {
                //parse
                Node n = messages.item(i);
                String name = n.getAttributes().getNamedItem("name").getNodeValue();
                String hasParamStr = n.getAttributes().getNamedItem("param").getNodeValue();
                //optional platform
                String platform = null;
                Node platformNode = n.getAttributes().getNamedItem("platform");
                if(platformNode != null){
                    platform = platformNode.getNodeValue(); 
                }
                boolean hasParam = Boolean.parseBoolean(hasParamStr);
                //create object
                PushMessage pm = new PushMessage();
                pm.setName(name);
                pm.setHasParams(hasParam);
                if(platform != null){
                    if(platform.contains("|")){
                        pm.setPlatforms(platform.split("\\|"));
                    }else{
                        pm.setPlatforms(new String[] {platform});
                    }
                }
                subResult.add(pm);
            }
        } catch (Exception e) {
            System.err.println("UNABLE TO LOAD messages.xml!");
            e.printStackTrace();
        }
        return subResult.toArray(new PushMessage[subResult.size()]);
    }       
}
