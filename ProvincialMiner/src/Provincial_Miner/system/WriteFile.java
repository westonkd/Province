/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Provincial_Miner.system;

import Provincial_Miner.application.Speaker;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Class to write xml file used for searching, .PRO file for Brother Carter's
 * profiler program, html file, docx file, and txt file.
 * @author cameronthomas
 */
public class WriteFile { 
  
    /**
     * This function writes an XML file organized by person that can be used to
     * search.
     * @param HashMap - HashMap filled with objects for each speaker
     * @return 
     */
    public void PersonXmlWriter (HashMap<String, ArrayList<String>> personList) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            
            //add elements to Document
            Element rootElement = doc.createElement("Person");
         
            doc.appendChild(rootElement);
            
             //create date, name, and content elements
            rootElement.appendChild(getPersonElements(doc, "date", "This is the date"));
            rootElement.appendChild(getPersonElements(doc, "Name", "This is the name"));
            rootElement.appendChild(getPersonElements(doc, "content", "This is the content"));
           
            //for output to file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            //File formating
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            
            //Stream to write data to file
            StreamResult file = new StreamResult(new File("/Users/cameronthomas/Desktop/test.xml"));
 
            //write data to file
            transformer.transform(source, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * utility method to create node
     * @param doc
     * @param name
     * @param value
     * @return Element Node
     */
    private static Node getPersonElements(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
    
     /**
     * This function writes an XML file organized by topic that can be used to
     * search.
     * @param HashMap - HashMap filled with objects for each topic
     * @return Nothing
     */
    public void TopicXmlWriter (HashMap<String, ArrayList<String>> topicList) {
        
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

