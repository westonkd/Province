/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Provincial_Miner.system;

import Provincial_Miner.application.Content;
import Provincial_Miner.application.Speaker;
import com.gtranslate.Language;
import com.gtranslate.Translator;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * PartialQuebecScraper
 * 
 * @author Weston Dransfield
 */
public class PartialQuebecScraper {

    private final String firstMemberURL = "http://www.assnat.qc.ca/fr/travaux-parlementaires/journaux-debats/index-jd/recherche.html?cat=v";
    private final String lastMemberURL = "&Section=particip&Requete=";
    private final String domain = "http://www.assnat.qc.ca/";

    private HashMap<String, Speaker> searchedSpeakers = new HashMap<>();

    ArrayList<String> months = new ArrayList(Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));

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
     * @param indexPeople set to true if you desire the scraper to begin
     * buildig a database of people (Makes getting a session later faster).
     * 
     * @return a list of all names from the specified session
     */
    public ArrayList<String> getNames(char firstLetter, String session, boolean indexPeople) {
        //create list to store the names
        ArrayList<String> speakerList = new ArrayList<>();

        //loop through each member of specified session
        for (int i = firstLetter; i <= 'z'; i++) {
            try {
                System.out.println((char) i);

                //connect to the page of names for the given letter
                Document letterIndex = Jsoup.connect(firstMemberURL + session + lastMemberURL + (char) i).get();

                //get all the names
                Elements speakers = letterIndex.select("dd");

                //extract the names and store int the array
                for (Element speaker : speakers) {
                    String name = speaker.text();

                    //format the name
                    name = name.toUpperCase();
                    name = name.substring(0, name.indexOf("("));

                    speakerList.add(name);

                    if (indexPeople) {
                        //create a new Person and add the list for later use
                        Speaker newSpeaker = new Speaker(name);

                        //set the speaker's url
                        newSpeaker.setURL(domain + speaker.select("a").attr("href"));

                        //add to the list
                        searchedSpeakers.put(name, newSpeaker);
                    }
                }

            } catch (IOException ex) {
                System.out.println(firstMemberURL + session + lastMemberURL + (char) i);
                System.out.println("Error on letter index");
            }
        }
        return speakerList;
    }

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
    public ArrayList<String> getTopics(String name, String session, boolean indexContent) {
        System.out.println("working on " + name);

        //string for the url
        String url = new String();

        //list of topics
        ArrayList<String> topics = new ArrayList<>();

        //if the URL has not been found already
        if (!searchedSpeakers.containsKey(name)) {
            //TODO: Make this work if the person has not been indexed
            //go get all the topics :/
            System.out.println("speaker not in hashmap");
        } else {
            url = searchedSpeakers.get(name).getURL();
        }

        //get all links on the member page, then extract the topic names
        try {
            Document speakerPage = Jsoup.connect(url).get();
            Elements anchors = speakerPage.select("a");

            for (Element anchor : anchors) {
                //if the anchor tag is a link to topic content
                if (anchor.attr("href").contains(session)) {
                    //get the topic name
                    String topicName = getName(anchor.attr("href"));

                    //add the topic to the list
                    if (!topics.contains(topicName) && !topicName.equals("Petition Filing ")) {
                        topics.add(topicName);
                    }

                    if (indexContent && !topicName.equals("Petition Filing ")) {
                        //visit each topic page and scrape the content
                        Document contentPage = Jsoup.connect(domain + anchor.attr("href")).get();

                        //create a new Content for the Speaker
                        Content newContent = new Content();

                        //set the content date
                        newContent.setDate(getDate(contentPage.select("h4").text()));

                        //add the content to content and then add the Content to the person we are on
                        Elements paragraphs = contentPage.select(".indexJD").select("p");

                        String content = new String();
                        boolean collectContent = false;
                        for (int i = 0; i < paragraphs.size(); i++) {
                            //get the bolded text (if any)
                            String bold = paragraphs.get(i).select("b").text();

                            if (bold.contains(searchedSpeakers.get(name).getLastName())) {
                                collectContent = true;
                            } else if (!bold.equals("")) {
                                collectContent = false;
                            }

                            //if the p tag contains the info we want
                            if (collectContent) {
                                content += " " + paragraphs.get(i).ownText();
                            }
                        }

                        //translate the content
                        content = translateContent(content);
                        System.out.println(content);

                        newContent.setContent(content);

                        //add the content to the current person
                        searchedSpeakers.get(name).addContent(topicName, newContent);
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Error connecting to member topic page");
        }

        return topics;
    }

    /**
     * This method generates a list of Speakers for a given session
     *
     * @param session
     * @return speakers - a list of speakers
     */
    public ArrayList<Speaker> getSession(String session) {
        //creat a new list for speakers
        ArrayList<Speaker> speakers = new ArrayList<>();

        //clear out any old speakers
        searchedSpeakers = new HashMap<>();

        //get all the names in the session
        ArrayList<String> names = getNames('a', session, true);

        //fill in the content of each person
        for (String name : names) {
            getTopics(name, session, true);
        }

        //get all the speakers
        for (Speaker speaker : searchedSpeakers.values()) {
            speakers.add(speaker);
        }

        return speakers;
    }

    /**
     * This method checks to see if the session data exists
     * @param session
     * @param subsession
     * @return
     */
    public boolean sessionExists(int session, int subsession) {
        //build the url
        final String commonURL = "http://www.assnat.qc.ca/fr/travaux-parlementaires/journaux-debats/index-jd/";
        String url = commonURL + session + "-" + subsession + ".html";
        
        //attempt to connect
        try {
            Document sessionPage = Jsoup.connect(url).get();
            String message = sessionPage.select(".imbGauche").text();

            //if their is no session data
            if (message.contains("Aucune séance")) {
                return false;
            }
            
        } catch (Exception e) {
            //if page does not exist, return false
            return false;
        }
        
        //the page exists
        return true;
    }
    
    /**
     *
     * @param session
     * @param subsession
     * @return
     */
    public String getSessionQuery(int session, int subsession) {
        String query = new String();
        
        //piece together the query string
        if (sessionExists(session, subsession)) {
            query = "&Session=" + ((session <= 20) ? "rd" : "jd") + session + "l" + subsession + "se";
        } else {
            query = "Session data does not exist";
        }
        
        return query;
    }
    
    /**
     * Returns the name of a topic after being passed the URL to the topic page.
     * @param url
     * @return 
     */
    private String getName(String url) {
        url = url.substring(url.indexOf("_") + 1);

        //format topic name
        url = url.replace('+', ' ').replace('_', ' ');

        try {
            Translator translate = Translator.getInstance();
            url = translate.translate(url, Language.FRENCH, Language.ENGLISH);
        } catch (Exception e) {
            //if we get an error here, return empty string
            System.out.println("Error translating topic name");
            return "";
        }

        //remove all digits
        for (int i = 0; i < 10; i++) {
            url = url.replace(Integer.toString(i), "");
        }

        return url;
    }

    /**
     * This method returns a LocalDate object with data parsed from the 
     * string passed in. This string must be in the form on the content pages i.e.
     * @param toParse
     * @return 
     */
    private LocalDate getDate(String toParse) {
        //create a new date
        LocalDate newDate = LocalDate.now();

        //get the line with the date
        toParse = toParse.substring(toParse.indexOf(",") + 2);

        //get the day
        int day = Integer.parseInt(toParse.substring(0, toParse.indexOf(" ")));
        toParse = toParse.substring(toParse.indexOf(" ") + 1);

        //get the month
        //int month = Integer.parseInt(toParse.substring(0, toParse.indexOf(" ")));
        String month = toParse.substring(0, toParse.indexOf(" "));

        //translate the month to English
        Translator translate = Translator.getInstance();
        month = translate.translate(month, Language.FRENCH, Language.ENGLISH);

        //convert string to int month
        int monthInt = months.indexOf(month) + 1;
        toParse = toParse.substring(toParse.indexOf(" ") + 1);

        //get the year
        int year = Integer.parseInt(toParse.substring(0, toParse.indexOf(",")));

        //set the date
        newDate = LocalDate.of(year, monthInt, day);

        return newDate;
    }

    /**
     * This method translates content to french
     * @param content the text to be translated.
     * @return the translated text.
     */
    private String translateContent(String content) {
        
        try {
        //create new translator and translate the text
        Translator translate = Translator.getInstance();
        content = translate.translate(content, Language.FRENCH, Language.ENGLISH);
        } catch (Exception e) {
            System.out.println("Error translatinng content");
            return "";
        }
        
        return content;
    }

}

