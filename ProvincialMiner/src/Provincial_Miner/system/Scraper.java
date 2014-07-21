/*
 * Interface of type Scaper
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
     * This method gets all the names from the specifies session starting at the
     * given letter. Note this method takes the session number in the form of
     * the query string (i.e. &Session=rd39l1se). Note that this method creates
     * a speaker object for each name pulled for later use.
     *
     * @param firstLetter the letter to begin the scrape at.
     *
     * @param session the session formated as a query string (i.e.
     * &Session=rd39l1se).
     *
     * @param indexPeople set to true if you desire the scraper to begin buildig
     * a database of people (Makes getting a session later faster).
     *
     * @return a list of all names from the specified session
     */
    public ArrayList<String> getNames(char firstLetter, String session, boolean indexPeople);

    /**
     * Gets all the topics spoken on by the provided person in the specified
     * session.
     *
     * @param name name of person to get topics for
     * @param session session to search formated as query string(i.e.
     * &Session=rd39l1se).
     * @param indexContent
     * @return List of all topics from the given speaker.
     */
    public ArrayList<String> getTopics(String name, String session, boolean indexContent);

     /**
     * This method generates a list of Speakers for a given session
     *
     * @param session
     * @return speakers - a list of speakers
     */
    public ArrayList<Speaker> getSession(String session);

    /**
     * This method checks to see if the session data exists
     *
     * @param session - main session number
     * @param subsession - subsession number
     * @return
     */
    public boolean sessionExists(int session, int subsession);
}
