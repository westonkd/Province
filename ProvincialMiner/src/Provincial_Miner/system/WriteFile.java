/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Provincial_Miner.system;

import Provincial_Miner.application.Session;
import Provincial_Miner.application.Speaker;
import Provincial_Miner.application.Topic;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author cameronthomas
 */
public class WriteFile {     
    /**
     * 
     * @param speakerList 
     */
    public void PersonXmlWriter(ArrayList<Speaker> speakerList) {
        try { 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Element personToAdd;
                Element topicToadd;
                Element sessionToAdd;
 
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("SpeakersToTopics");
		doc.appendChild(rootElement);
                                          
                // loop to add speakers to rootElement in xml
                for (Speaker speaker: speakerList) {
                    // staff elements
                    personToAdd = doc.createElement("Person");
                    personToAdd.setAttribute("name", speaker.getFirstName() + " " + speaker.getLastName());
                    rootElement.appendChild(personToAdd);

                    // Loop to add topics to xml
                    for (Map.Entry<String, List<Session>> topic : speaker.getTopics().entrySet()) {
                        topicToadd = doc.createElement("Topic");
                        topicToadd.setAttribute("subject", topic.getKey());
                        personToAdd.appendChild(topicToadd);
    
                        // loop to add session to topic in xml
                        for (Session session: topic.getValue()) {
                            sessionToAdd = doc.createElement("Session");
                            sessionToAdd.setAttribute("date", session.getDate().toString());
                            sessionToAdd.setTextContent(session.getContent());
                            topicToadd.appendChild(sessionToAdd);      
                        }                   
                    }                         
                }
                
		// write the content into xml file               
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("/Users/cameronthomas/Desktop/speakerFile.xml"));
 
                // Sets formating for xml file
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                
		transformer.transform(source, result);

 
	}
        catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	}
        catch (TransformerException tfe) {
		tfe.printStackTrace();
	}          
    }
    
    /**
     * 
     * @param speakerList 
     */
    public void TopicXmlWriter(ArrayList<Topic> topicList) {
        try { 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Element topicToAdd;
                Element speakerToadd;
                Element sessionToAdd;
 
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("TopicsToSpeakers");
		doc.appendChild(rootElement);
                                          
                // loop to add topics to rootElement in xml
                for (Topic topic: topicList) {
                    // staff elements
                    topicToAdd = doc.createElement("Topic");
                    topicToAdd.setAttribute("subject", topic.getSubject());
                    rootElement.appendChild(topicToAdd);

                    // Loop to add speakers to xml
                    for (Map.Entry<String, List<Session>> speaker : topic.getSpeakers().entrySet()) {
                        speakerToadd = doc.createElement("Speaker");
                        speakerToadd.setAttribute("name", speaker.getKey());
                        topicToAdd.appendChild(speakerToadd);
    
                        // loop to add session to speaker in xml
                        for (Session session: speaker.getValue()) {
                            sessionToAdd = doc.createElement("Session");
                            sessionToAdd.setAttribute("date", session.getDate().toString());
                            sessionToAdd.setTextContent(session.getContent());
                            speakerToadd.appendChild(sessionToAdd);      
                        }                   
                    }                         
                }
                
		// write the content into xml file               
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("/Users/cameronthomas/Desktop/topicFile.xml"));
 
                // Sets formating for xml file
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                
		transformer.transform(source, result);

 
	}
        catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	}
        catch (TransformerException tfe) {
		tfe.printStackTrace();
	}          
    }
    
    /**
     * This function writes a file with a .PRO extention that Brother Carter 
     * can use for his profiler program.
     * @param xml file will can be parsed to get information to write to a file.
     * @return
     */
    public void ProWriter() {
        
    }
    
    /**
     * This function writes a html file.
     * @param xml file will can be parsed to get information to write to a file.
     * @return
     */
    public void HtmlWriter() { 
        
    }
    
    /**
     * This function writes a txt file.
     * @param xml file will can be parsed to get information to write to a file.
     * @return
     */
    public void TxtWriter() {
        
    }
    
    /**
     * This function writes a Microsoft word file.
     * @param 
     * @return
     */
    public void DocxWriter() {
            
    }
}
    
   

