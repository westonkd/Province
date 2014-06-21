/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Provincial_Miner.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Weston Dransfield
 */
public class Speaker {
    
    //full name of the speaker (i.e. John Smith)
    String name;
    
    //Mapping of topics to a list of sessions
    HashMap<String, List<Session>> topics = new HashMap<>();

    /**
     * Initializes Speaker with an empty name.
     */
    public Speaker() {
        this.name = new String();
    }
    
    /**
     * Initializes a speaker with a name equal to name parameter.
     * @param name name of the Speaker.
     */
    public Speaker(String name) {
        this.name = name;
    }
    
    /**
     * Gets the full name of the speaker (i.e. John Smith).
     * @return name of speaker
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of the speaker to name.
     * @param name new name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the map of topics to Lists of Sessions.
     * @return
     */
    public HashMap<String, List<Session>> getTopics() {
        return topics;
    }
    
    /**
     * Adds a session to the list under the given topic.
     * @param topic topic to insert session to
     * @param session session to insert
     */
    public void addSession(String topic, Session session) {
        //if the topics map does not contain the topic
        if (!topics.containsKey(topic)) {
            //create the new topic list
            topics.put(topic, new ArrayList<>());
        }
        
        //add the new session to the end of the topic list
        topics.get(topic).add(session);
    }
}
