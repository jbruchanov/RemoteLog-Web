package com.scurab.gwt.rlw.server.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.scurab.gwt.rlw.server.Application;
import com.scurab.gwt.rlw.server.Queries;
import com.scurab.gwt.rlw.shared.model.PushMessage;

public class XmlLoader {

    public static PushMessage[] loadMessages(){
        List<PushMessage> subResult = new ArrayList<PushMessage>();
        
        String file = "/messages.xml";
        System.out.println("Start loading PushMessages");
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
                
                String content = null;
                if(hasParam){
                    //optional param example
                    content = n.getTextContent().trim();                
                }
                
                //optional param onlyForApp
                Node onlyForAppNode = n.getAttributes().getNamedItem("forApp");
                boolean onlyForApp = false;
                if(onlyForAppNode != null){
                    onlyForApp = Boolean.parseBoolean(onlyForAppNode.getNodeValue());
                }
                
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
                pm.setParamExample(content);
                pm.setOnlyForApp(onlyForApp);
                
                subResult.add(pm);
                System.out.println("Loaded PushMessage:" + Application.GSON.toJson(pm));
            }
        } catch (Exception e) {
            System.err.println("UNABLE TO LOAD messages.xml!");
            e.printStackTrace();
        }
        System.out.println("Finish loading PushMessages");
        return subResult.toArray(new PushMessage[subResult.size()]);
    }       
}
