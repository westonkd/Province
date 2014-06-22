/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Provincial_Miner.system;

import Provincial_Miner.application.Speaker;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * Class to write xml file used for searching, .PRO file for Brother Carter's
 * profiler program, html file,  and txt file.
 * @author cameronthomas
 */
public class WriteFile { 
  
    /**
     * This function writes an XML file organized by person that can be used to
     * search.
     * @param HashMap<Speaker> - HashMap filled with objects for each speaker
     * @return 
     */
    public void PersonXmlWriter (HashMap<String, Speaker> speakerList) {
        
        
    }
    
     /**
     * This function writes an XML file organized by topic that can be used to
     * search.
     * @param HashMap<Speaker> - HashMap filled with objects for each topic
     * @return Nothing
     */
    public void TopicXmlWriter (HashMap<String, Speaker> topicList) {
        
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

