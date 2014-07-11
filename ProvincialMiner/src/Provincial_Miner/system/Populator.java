/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Provincial_Miner.system;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Will go through all the data and gather information to populate the comboboxes
 * @author Stephen
 */
public class Populator {

    String fileName;

    public void setFileName(String fileName) {
        this.fileName = System.getProperty("user.home") + "/Desktop/SpeakerFile_files/" + fileName;
    }

    ArrayList<String> people = new ArrayList();

    public ArrayList<String> getPeople() {
        return people;
    }

    public ArrayList<String> getTopics() {
        return topics;
    }
    ArrayList<String> topics = new ArrayList();

    /**
     * 
     */
    public void populate() {

        try {
            // open the file
            File xmlPerson = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            // uses DOM to parse the xml
            Document doc = dBuilder.parse(xmlPerson);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Person");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                NodeList sublist = nNode.getChildNodes();
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    people.add(eElement.getAttribute("name"));
                    for (int j = 0; j < sublist.getLength(); j++) {
                        Node cNode = (Node) sublist.item(j);
                        if (cNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element cElement = (Element) cNode;
                            topics.add(cElement.getAttribute("subject"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
