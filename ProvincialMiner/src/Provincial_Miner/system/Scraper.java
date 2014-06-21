/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Provincial_Miner.system;

import java.util.HashMap;

/**
 *
 * @author Weston Dransfield
 */
public interface Scraper {
    
    /**
     * member variable which maps speakers to the topics they have spoken on.
     */
    HashMap<String, String> membersToTopics = new HashMap();
    
    /**
     * member variable which maps topics to speakers who have spoken on them.
     */
    HashMap<String, String> topicsToMembers = new HashMap();
    
    public void scrape();
    
    public void scrape(char firstLetter);
    
    void parseMembers();
    
    void parseTopics();
    
    
    
}
