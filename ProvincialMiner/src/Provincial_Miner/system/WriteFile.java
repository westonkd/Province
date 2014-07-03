/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Provincial_Miner.system;

import Provincial_Miner.application.Content;
import Provincial_Miner.application.Speaker;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        //Creates a folder on the desktop named "XML Database"
        /*
      final File homeDir = new File(System.getProperty("user.home"),"Desktop");
      File dir3 = new File(homeDir, "XML Database");
      dir3.mkdir();
        */
        
        try { 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Element personToAdd;
                Element topicToadd;
                Element sessionToAdd;
 
		// Create root element
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("SpeakersToTopics");
		doc.appendChild(rootElement);
                                          
                // Loop to add speakers to rootElement in xml
                for (Speaker speaker: speakerList) {
                    // staff elements
                    personToAdd = doc.createElement("Person");
                    personToAdd.setAttribute("name", speaker.getFirstName() + " " + speaker.getLastName());
                    rootElement.appendChild(personToAdd);

                    // Loop to add topics to xml
                    for (Map.Entry<String, List<Content>> topic : speaker.getTopics().entrySet()) {
                        topicToadd = doc.createElement("Topic");
                        topicToadd.setAttribute("subject", topic.getKey());
                        personToAdd.appendChild(topicToadd);
    
                        // loop to add session to topic to xml
                        for (Content session: topic.getValue()) {
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
		StreamResult result = new StreamResult(new File("/Users/cameronthomas/Desktop/"
                                                                + "speakerFile.xml"));
 
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
    /*
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
                        for (Content session: speaker.getValue()) {
                            sessionToAdd = doc.createElement("Content");
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
    */
    
    /**
     * 
     */
    public void writeFile(String content) {
        String fileName = "";
        String folderName = "";
        String contentToWrite = "";
        
        // Create folder to store .PRO, .html, .txt, and .docx files in
        final File homeDir = new File(System.getProperty("user.home"),"Desktop");
        File dir2 = new File(homeDir, "XML Database");
        dir2.mkdir();
            
        // Write .PRO file
        writeToFile(folderName, fileName + ".PRO", contentToWrite);
        
        // Write .html file
        writeToFile(folderName, fileName + ".html", contentToWrite);
        
        // Write .txt file
        writeToFile(folderName, fileName + ".txt", contentToWrite);
        
        // Write .docx file - Microsoft word file
        
    }
    
    /**
     * 
     * @param folderName
     * @param fileName
     * @param content 
     */
    private void writeToFile(String folderName, String fileName, String content) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write("");
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(WriteFile.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
      
    /**
     * This function writes a file with a .PRO extention that Brother Carter 
     * can use for his profiler program.
     * @param xml file will can be parsed to get information to write to a file.
     * @return
     */
    public void ProWriter() {
         //Creates a folder on the desktop named "Mining for Told"
        /*
      final File homeDir = new File(System.getProperty("user.home"),"Desktop");
      File dir3 = new File(homeDir, "Mining for Told");
      dir3.mkdir();

      //creates a folder in "Mining for Told" named the topics and person's name
      File dir2 = new File(dir3, pName);
      dir2.mkdir();
      */
      final File homeDir = new File(System.getProperty("user.home"),"Desktop");
      File dir2 = new File(homeDir, "XML Database");
      dir2.mkdir();
      
      String pFileName = "/Users/cameronthomas/Desktop/XML Database/speakerFile.txt";
      String pData = "This is the data";
        
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(pFileName));
            bw.write("<html><head><title>New Page</title></head><body><p>This is Body</p></body></html>");
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(WriteFile.class.getName()).log(Level.SEVERE, null, ex);
        }     
   }
}        
    
    
   

