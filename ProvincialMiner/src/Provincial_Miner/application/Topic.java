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
public class Topic  {
    
    //full name of the speaker (i.e. Job Market)
    String subject;
    
    //Mapping of speakers to a list of sessions
    HashMap<String, List<Session>> speakers = new HashMap<>();

    /**
     * Initializes Topic with an empty subject.
     */
    public Topic() {
        this.subject = new String();
    }
    
    /**
     * Initializes a topic with a name equal to subject parameter.
     * @param subject topic subject.
     */
    public Topic(String subject) {
        this.subject = subject;
    }
    
    /**
     * Gets the Topic (i.e. Job Market).
     * @return topic subject
     */
    public String getSubject() {
        return subject;
    }
    
    /**
     * Sets the subject of the topic to string parameter.
     * @param subject new subject to set
     */
    public void setName(String subject) {
        this.subject = subject;
    }
    
    /**
     * Gets the map of Speakers to Lists of Sessions.
     * @return
     */
    public HashMap<String, List<Session>> getSpeakers() {
        return speakers;
    }
    
    /**
     * Adds a session to the list under the given speaker.
     * @param topic speaker to insert session to
     * @param session session to insert
     */
    public void addSession(String speaker, Session session) {
        //if the speakers map does not contain the topic
        if (!speakers.containsKey(speaker)) {
            //create the new speaker list
            speakers.put(speaker, new ArrayList<>());
        }
        
        //add the new session to the end of the speaker list
        speakers.get(speaker).add(session);
    }
}
