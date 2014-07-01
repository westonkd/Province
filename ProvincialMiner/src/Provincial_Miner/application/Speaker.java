/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Provincial_Miner.application;

import com.sun.deploy.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Weston Dransfield
 */
public class Speaker {

    //first name of the speaker
    private String firstName;

    //last name of the speaker
    private String lastName;

    //url to speakers page
    private String url;

    //Mapping of topics to a list of sessions
    private HashMap<String, List<Session>> topics = new HashMap<>();

    /**
     * Initializes Speaker with an empty name.
     */
    public Speaker() {
        firstName = new String();
        lastName = new String();
    }

    /**
     * Initializes a speaker with a name equal to name parameter. Note last name
     * is expected first (i.e Smith John not John Smith).
     *
     * @param name name of the Speaker.
     */
    public Speaker(String name) {
        setName(name);
    }

    /**
     * Get the first name of the speaker.
     *
     * @return name of speaker
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get the last name of the speaker
     *
     * @return last name of speaker
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the name of the speaker to name.
     *
     * @param name new name to set
     */
    public void setName(String name) {
        if (name.indexOf(" ") != name.length() - 1) {
            lastName = name.substring(0, name.indexOf(" ")).toLowerCase();
            firstName = name.substring(name.indexOf(" ") + 1).toLowerCase();

            //capatalize the first letter of each name
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        } else {
            lastName = name.toLowerCase();
            lastName = name.substring(0,1).toUpperCase() + lastName.substring(1);
        }
    }

    /**
     * Gets the map of topics to Lists of Sessions.
     *
     * @return
     */
    public HashMap<String, List<Session>> getTopics() {
        return topics;
    }

    /**
     * Adds a session to the list under the given topic.
     *
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

    /**
     * set the url of the speaker's web page
     *
     * @param url
     */
    public void setURL(final String url) {
        this.url = url;
    }

    /**
     * get the URL to the speaker's web page.
     *
     * @return
     */
    public String getURL() {
        return url;
    }
}
