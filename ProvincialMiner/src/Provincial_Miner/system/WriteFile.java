package Provincial_Miner.system;

import Provincial_Miner.application.Content;
import Provincial_Miner.application.Speaker;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
     * Uses ArrayList passed in to create a Document Object Model(DOM) with is 
     * used to write XML file.
     * @param speakerList 
     */
    public void PersonXmlWriter(ArrayList<Speaker> speakerList) {       
        // String to hold date
        String tempDate = "";
        
        //Creates a folder on the desktop named "XML Database"
        final File homeDir = new File(System.getProperty("user.home"),"Desktop");
        File dir1 = new File(homeDir, "SpeakerFile_files");
        dir1.mkdir();
              
        try { 
               // Creates objects needed to create DOM structure
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Element personToAdd;
                Element topicToadd;
                Element sessionToAdd;
 
		// Creates root element in DOM structure
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("SpeakersToTopics");
		doc.appendChild(rootElement);
                                          
                // Loop to add speakers to rootElement 
                for (Speaker speaker: speakerList) {
                    // staff elements
                    personToAdd = doc.createElement("Person");
                    personToAdd.setAttribute("name", speaker.getFirstName() + " " + speaker.getLastName());
                    rootElement.appendChild(personToAdd);

                    // Loop to add topics to speaker
                    for (Map.Entry<String, List<Content>> topic : speaker.getTopics().entrySet()) {
                        topicToadd = doc.createElement("Topic");
                        topicToadd.setAttribute("subject", topic.getKey());
                        personToAdd.appendChild(topicToadd);
    
                        // loop to add session to topic
                        for (Content session: topic.getValue()) {
                            sessionToAdd = doc.createElement("Content");
                            sessionToAdd.setAttribute("date", session.getDate().toString());
                            sessionToAdd.setTextContent(session.getContent());
                            topicToadd.appendChild(sessionToAdd);  
                            tempDate = session.getDate().toString();
                        }                   
                    }                         
                }
                
		// Creates objects need to write DOM to XML file               
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
                 
                // Creates stream that specifies name of file and folder to save
                // file in.
		StreamResult result = new StreamResult(new File(dir1, "Session." + tempDate + ".xml"));
         
                // Sets formating for XML file
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                
                // Write XML file
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
     * This function creates a new directory on the desktop and then additional
     * folders in this folder for each topic
     * @param content 
     */
    public void writeDataFile(String contentToWrite, String personName, String topic) {
        // Checks if personName is null of empty
        if (personName == null || personName.isEmpty()) {
            personName = "All Speakers";
        }        

        // Checks if topic is null or empty
        if (topic == null || topic.isEmpty()) {
            topic = "All Topics";
        }
            
        String fileName = personName + "_" + topic;

        // Create folder to store .PRO, .html, .txt, and .docx files in
        final File homeDir = new File(System.getProperty("user.home"),"Desktop");
        File dir1 = new File(homeDir, "Quebec");
        dir1.mkdir();
        
        //Creates a folder in previously created directoy named the person's
        //name followed by the topic
        File dir2 = new File(dir1, fileName);
        dir2.mkdir();
        
        // Replace generic tags with <ignore> tags needed for .PRO and .txt files
        contentToWrite = contentToWrite.replace("<name>", "<ignore>");
        contentToWrite = contentToWrite.replace("</name>", "</ignore>");
        contentToWrite = contentToWrite.replace("<topic>", "<ignore>");   
        contentToWrite = contentToWrite.replace("</topic>", "</ignore>");
        contentToWrite = contentToWrite.replace("<date>", "<ignore>");
        contentToWrite = contentToWrite.replace("</date>", "</ignore>");
        // Write .PRO file  
        writeToFile(dir2, fileName + ".PRO", contentToWrite );
        
        // Write .txt file
        writeToFile(dir2, fileName + ".txt", contentToWrite);
        
        // Replaces <ignore> tags with <p> html tags
        contentToWrite = contentToWrite.replace("<ignore>", "<p>");
        contentToWrite = contentToWrite.replace("</ignore>", "</p>");
             
        // Write .html file
        writeToFile(dir2, fileName + ".html", contentToWrite);     
    }
    
    /**
     * Writes .PRO, .html, and .txt files.
     * @param FolderToWrite
     * @param fileName
     * @param content 
     */
    private void writeToFile(File folderToWrite, String fileName, String content) {
        
        // Write file to topic folder
        try {   
            File file = new File(folderToWrite, fileName);
            FileOutputStream output = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(output);    
            Writer writer = new BufferedWriter(osw);
            writer.write(content);
            writer.close();         
        } 
        catch (IOException ex) {
            Logger.getLogger(WriteFile.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
      
    /**
     * This function writes a file with a .PRO extention that Brother Carter 
     * can use for his profiler program.
     * @param xml file will can be parsed to get information to write to a file.
     * @return
     */
    /*
    public void ProWriter() { 
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
    */
}        
    
    
   

