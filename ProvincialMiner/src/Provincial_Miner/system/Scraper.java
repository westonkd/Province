/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Provincial_Miner.system;

import Provincial_Miner.application.Speaker;
import Provincial_Miner.application.Topic;
import java.util.ArrayList;

/**
 *
 * @author Weston Dransfield
 */
public interface Scraper {
    
    /**
     * List of all speakrs
     */
    ArrayList<Speaker> speakers = new ArrayList<>();
    
    /**
     * List of all topics
     */
    ArrayList<Topic> topics = new ArrayList<>();
    
    public void scrape();
    
    public void scrape(char firstLetter); 
}
