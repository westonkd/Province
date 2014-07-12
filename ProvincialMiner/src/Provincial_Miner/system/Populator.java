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
 * Will go through all the data and gather information to populate the combo
 * boxes
 *
 * @author Stephen
 */
public class Populator {

    String fileName;

    public void setFileName(String fileName) {
        this.fileName = System.getProperty("user.home") + "/Desktop/SpeakerFile_files/" + fileName;
    }
    // list of people
    ArrayList<String> people = new ArrayList();
    //list of topics
    ArrayList<String> topics = new ArrayList();

    public ArrayList<String> getPeople() {
        return people;
    }

    public ArrayList<String> getTopics() {
        return topics;
    }

    /**
     * Gathers the data from the directory
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
                    // the person element
                    Element eElement = (Element) nNode;
                    people.add(eElement.getAttribute("name"));
                    for (int j = 0; j < sublist.getLength(); j++) {
                        Node cNode = (Node) sublist.item(j);
                        if (cNode.getNodeType() == Node.ELEMENT_NODE) {
                            // the topic element
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

    /**
     *
     * @param name
     * @return
     */
    public ArrayList<String> personToTopicPopulate(String name) {
        ArrayList<String> personToTopic = new ArrayList();
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
                    // the person element
                    Element eElement = (Element) nNode;
                    //  System.out.println(eElement);
                    if (name.equals(eElement.getAttribute("name"))) {

                        // people.add(eElement.getAttribute("name"));
                        for (int j = 0; j < sublist.getLength(); j++) {
                            Node cNode = (Node) sublist.item(j);
                            if (cNode.getNodeType() == Node.ELEMENT_NODE) {
                                // the topic element
                                Element cElement = (Element) cNode;
                                personToTopic.add(cElement.getAttribute("subject"));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return personToTopic;
    }

    public ArrayList<String> topicToPersonPopulate(String subject) {
        ArrayList<String> topicToPerson = new ArrayList();
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
                    // the person element
                    Element eElement = (Element) nNode;

                    // people.add(eElement.getAttribute("name"));
                    for (int j = 0; j < sublist.getLength(); j++) {
                        Node cNode = (Node) sublist.item(j);
                        if (cNode.getNodeType() == Node.ELEMENT_NODE) {
                            // the topic element
                            Element cElement = (Element) cNode;
                            if (cElement.getAttribute("subject").equals(subject)) {
                                topicToPerson.add(eElement.getAttribute("name"));
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return topicToPerson;
    }
}
