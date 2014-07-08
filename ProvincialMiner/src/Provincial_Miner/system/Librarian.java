/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Provincial_Miner.system;

import java.time.LocalDate;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 *
 * @author Stephen
 */
public class Librarian {

    String result;
    String fileName = ("C:\\Users\\Stephen\\Desktop\\speakerFile.xml");

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = "C:\\Users\\Stephen\\Desktop\\SpeakerFile_files\\" + fileName ;
    }

    public Librarian() {

    }

    /**
     * This will search through the topic directory and find the relevant info
     * based on the search parameters entered in. Uses DOM XML parser
     *
     * @param topic
     * @param startDate
     * @param endDate
     * @return
     */
    public String searchTopic(String topic, LocalDate startDate, LocalDate endDate) {
        // the resulting string to be sent to the file writer for final formatting
        String result = "";
        try {
            // open the file
            File xmlPerson = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            // uses DOM to parse the xml
            Document doc = dBuilder.parse(xmlPerson);

            doc.getDocumentElement().normalize();

// searches for the topic tags
            NodeList nList = doc.getElementsByTagName("Person");

            for (int i = 0; i < nList.getLength(); i++) {
                // main element (topic)
                Node nNode = nList.item(i);
                // sublist of topic (sessions)
                NodeList sublist = nNode.getChildNodes();
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    // if the elements attribute equals the search parameter
                    //  if (eElement.getAttribute("name").equals(person)) {
                    // display the topic subject
// loop through the topics sessions for dates
                    for (int j = 0; j < sublist.getLength(); j++) {
                        Node cNode = (Node) sublist.item(j);
                        NodeList dateNode = cNode.getChildNodes();
                        if (cNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element topicElement = (Element) cNode;
                            if (topicElement.getAttribute("subject").equals(topic)) {
                                // changes the string date into a localDate
                                for (int k = 0; k < dateNode.getLength(); k++) {
                                    Node kNode = (Node) dateNode.item(k);
                                    if (kNode.getNodeType() == Node.ELEMENT_NODE) {
                                        Element kElement = (Element) kNode;
                                        String date = kElement.getAttribute("date");
                                        String[] parts = date.split("-");
                                        int year = Integer.parseInt(parts[0]);
                                        int month = Integer.parseInt(parts[1]);
                                        int day = Integer.parseInt(parts[2]);
                                        LocalDate dateCheck = null;
                                        // sets local date 
                                        dateCheck = dateCheck.of(year, month, day);
                                        // make sure its within the search range
                                        if ((dateCheck.isAfter(startDate) || dateCheck.isEqual(startDate))
                                                && (dateCheck.isBefore(endDate) || dateCheck.isEqual(endDate))) {
                                            result = result + "-" + eElement.getAttribute("name") + ": "
                                                    + kElement.getAttribute("date") + "\n"
                                                    + kElement.getTextContent() + "\n";

                                        } else {
                                        }

                                    }
                                }
                            }
                        } else {
                        }

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Searches just based on the person specified
     *
     * @param person
     * @param startDate
     * @param endDate
     * @return
     */
    public String searchPerson(String person, LocalDate startDate, LocalDate endDate) {
        // the resulting string to be sent to the file writer for final formatting
        String result = "";

        try {
            // open the file
            File xmlPerson = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            // uses DOM to parse the xml
            Document doc = dBuilder.parse(xmlPerson);

            doc.getDocumentElement().normalize();

// searches for the topic tags
            NodeList nList = doc.getElementsByTagName("Person");

            for (int i = 0; i < nList.getLength(); i++) {
                // main element (topic)
                Node nNode = nList.item(i);
                // sublist of topic (sessions)
                NodeList sublist = nNode.getChildNodes();
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    // if the elements attribute equals the search parameter
                    if (eElement.getAttribute("name").equals(person)) {
                        // display the topic subject
// loop through the topics sessions for dates
                        for (int j = 0; j < sublist.getLength(); j++) {
                            Node cNode = (Node) sublist.item(j);
                            NodeList dateNode = cNode.getChildNodes();
                            if (cNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element topicElement = (Element) cNode;
                                // changes the string date into a localDate
                                for (int k = 0; k < dateNode.getLength(); k++) {
                                    Node kNode = (Node) dateNode.item(k);
                                    if (kNode.getNodeType() == Node.ELEMENT_NODE) {
                                        Element kElement = (Element) kNode;
                                        String date = kElement.getAttribute("date");
                                        String[] parts = date.split("-");
                                        int year = Integer.parseInt(parts[0]);
                                        int month = Integer.parseInt(parts[1]);
                                        int day = Integer.parseInt(parts[2]);
                                        LocalDate dateCheck = null;
                                        // sets local date 
                                        dateCheck = dateCheck.of(year, month, day);
                                        // make sure its within the search range
                                        if ((dateCheck.isAfter(startDate) || dateCheck.isEqual(startDate))
                                                && (dateCheck.isBefore(endDate) || dateCheck.isEqual(endDate))) {
                                            result = result + "-" + topicElement.getAttribute("subject") + ": "
                                                    + kElement.getAttribute("date") + "\n"
                                                    + kElement.getTextContent() + "\n";

                                        } else {
                                        }
                                    }
                                }
                            }
                        }
                    } else {

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * Searches both a person and a topic within a specified date range
     *
     * @param person
     * @param topic
     * @param startDate
     * @param endDate
     * @return
     */
    public String searchBoth(String person, String topic, LocalDate startDate, LocalDate endDate) {
        // the resulting string to be sent to the file writer for final formatting
        String result = "";

        try {
            // open the file
            File xmlPerson = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            // uses DOM to parse the xml
            Document doc = dBuilder.parse(xmlPerson);

            doc.getDocumentElement().normalize();

            //  System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
// searches for the topic tags
            NodeList nList = doc.getElementsByTagName("Person");

            for (int i = 0; i < nList.getLength(); i++) {
                // main element (topic)
                Node nNode = nList.item(i);
                // sublist of topic (sessions)
                NodeList sublist = nNode.getChildNodes();
                //    System.out.println("\n Current Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    // if the elements attribute equals the search parameter
                    if (eElement.getAttribute("name").equals(person)) {
                        // display the topic subject
// loop through the topics sessions for dates
                        for (int j = 0; j < sublist.getLength(); j++) {
                            Node cNode = (Node) sublist.item(j);
                            NodeList dateNode = cNode.getChildNodes();
                            if (cNode.getNodeType() == Node.ELEMENT_NODE) {

                                Element cElement = (Element) cNode;
                                if (cElement.getAttribute("subject").equals(topic)) {
                                    // changes the string date into a localDate
                                    for (int k = 0; k < dateNode.getLength(); k++) {
                                        Node kNode = (Node) dateNode.item(k);
                                        if (kNode.getNodeType() == Node.ELEMENT_NODE) {
                                            Element kElement = (Element) kNode;
                                            String date = kElement.getAttribute("date");
                                            String[] parts = date.split("-");
                                            int year = Integer.parseInt(parts[0]);
                                            int month = Integer.parseInt(parts[1]);
                                            int day = Integer.parseInt(parts[2]);
                                            LocalDate dateCheck = null;
                                            // sets local date 
                                            dateCheck = dateCheck.of(year, month, day);
                                            // make sure its within the search range
                                            if ((dateCheck.isAfter(startDate) || dateCheck.isEqual(startDate))
                                                    && (dateCheck.isBefore(endDate) || dateCheck.isEqual(endDate))) {
                                                result = result + "-" + kElement.getAttribute("date") + "\n"
                                                        + kElement.getTextContent() + "\n";

                                            } else {
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
}
