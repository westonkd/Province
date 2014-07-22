package Provincial_Miner.system;

import Provincial_Miner.application.Content;
import Provincial_Miner.application.Speaker;
import java.awt.Desktop;
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
 * Class to write .xml, .html, .PRO, and .txt files.
 *
 * @author cameronthomas
 */
public class WriteFile {

    /**
     * Uses ArrayList passed in to create a Document Object Model(DOM) with is
     * used to write XML file.
     *
     * @param speakerList
     */
    public void PersonXmlWriter(ArrayList<Speaker> speakerList) {
        // String to hold date
        String tempDate = null;
        
        // Creates a folder in the documents folder to store xml file
        File dir1 = new File(System.getProperty("user.home") + "/Documents", "SpeakerFile_files");
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
            for (Speaker speaker : speakerList) {
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
                    for (Content session : topic.getValue()) {
                        sessionToAdd = doc.createElement("Content");
                        sessionToAdd.setAttribute("date", session.getDate().toString());
                        sessionToAdd.setTextContent(session.getContent());
                        topicToadd.appendChild(sessionToAdd);

                        if (tempDate == null) {
                                tempDate = session.getDate().toString();
                            }
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
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    /**
     * This function creates a new directory on the desktop and then additional
     * folders in this folder for each topic
     *
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
        if (fileName.endsWith(" ")){
               fileName = fileName.substring(0, fileName.length()-1);
            }

        // Create folder to store folers for each search

        File dir1 = new File(System.getProperty("user.home") + "/Desktop","Provincial Mining");

        dir1.mkdir();
        // Create folder to store .PRO, .html, .txt, and .docx files
        // for each search
        File dir2 = new File(dir1, "Quebec");
        dir2.mkdir();

        // Creates a folder in previously created directoy named the person's
        // name followed by the topic
        File dir3 = new File(dir2, fileName);
        dir3.mkdir();

        // Replace generic tags with <ignore> tags needed for .PRO and .txt files
        contentToWrite = contentToWrite.replace("<name>", "<ignore>");
        contentToWrite = contentToWrite.replace("</name>", "</ignore>");
        contentToWrite = contentToWrite.replace("<topic>", "<ignore>");
        contentToWrite = contentToWrite.replace("</topic>", "</ignore>");
        contentToWrite = contentToWrite.replace("<date>", "<ignore>");
        contentToWrite = contentToWrite.replace("</date>", "</ignore>");

        // Write .PRO file  
        writeToFile(dir3, fileName + ".PRO", contentToWrite);

        // Write .txt file
        writeToFile(dir3, fileName + ".txt", contentToWrite);

        // Replaces <ignore> tags with <p> html tags
        contentToWrite = contentToWrite.replace("<ignore>", "<p>");
        contentToWrite = contentToWrite.replace("</ignore>", "</p>");

        // Write .html file
        writeToFile(dir3, fileName + ".html", contentToWrite);

        // Open folder containing files just written
        try {
            Desktop.getDesktop().open(dir3);
        } catch (IOException ex) {
            Logger.getLogger(WriteFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Writes .PRO, .html, and .txt files.
     *
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
        } catch (IOException ex) {
            Logger.getLogger(WriteFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
