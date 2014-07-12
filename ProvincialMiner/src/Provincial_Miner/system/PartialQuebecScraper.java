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
import com.memetix.mst.translate.Translate;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
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
     * @param session the session formated as a query string (i.e.
     * &Session=rd39l1se).
     * @return a list of all names from the specified session
     */
    public ArrayList<String> getNames(char firstLetter, String session) {
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

                    //create a new Person and add the list for later use
                    Speaker newSpeaker = new Speaker(name);

                    //set the speaker's url
                    newSpeaker.setURL(domain + speaker.select("a").attr("href"));

                    //add to the list
                    searchedSpeakers.put(name, newSpeaker);
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
                    if (!topics.contains(topicName)) {
                        topics.add(topicName);
                    }

                    if (indexContent) {
                        //visit each topic page and scrape the content
                        Document contentPage = Jsoup.connect(domain + anchor.attr("href")).get();

                        //create a new Content for the Speaker
                        Content newContent = new Content();

                        //set the content date
                        newContent.setDate(getDate(contentPage.select("h4").text()));

                        //TODO: add the content to content and then add the Content to the person we are on
                        Elements paragraphs = contentPage.select(".indexJD").select("p");

                        //loop through bolded names and find content from given speaker
//                        for (Element bold : bolded) {
//                            if (bold.text().contains(searchedSpeakers.get(name).getLastName())) {
//                                //then this bold's parent's own text is content we want!
//                                String content = bold.parent().ownText();
//
//                                //translate the content to English
//                                Translator translate = Translator.getInstance();
//                                content = translate.translate(content, Language.FRENCH, Language.ENGLISH);
//
//                                //add it to the new Content object
//                                System.out.println(content);
//                                newContent.setContent(content);
//
//                                //add the content to the current person
//                                searchedSpeakers.get(name).addContent(topicName, newContent);
//                            }
//                        }
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

                        //translate the content to French
                        Translate.setClientId("xid7777");
                        Translate.setClientSecret("kzbpT0A+Ogrd2exnN0aJh3wiGF4eJNRTcKPjg264EJA=");

                        // From French -> English 
                        try {
                            content = Translate.execute(content, com.memetix.mst.language.Language.FRENCH, com.memetix.mst.language.Language.ENGLISH);
                        } catch (Exception ex) {
                            System.out.println("Error translating content");
                        }
                        
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
        ArrayList<String> names = getNames('a', session);

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

    private String getName(String url) {
        url = url.substring(url.indexOf("_") + 1);

        //format topic name
        url = url.replace('+', ' ').replace('_', ' ');

        Translator translate = Translator.getInstance();
        url = translate.translate(url, Language.FRENCH, Language.ENGLISH);

        //remove all digits
        for (int i = 0; i < 10; i++) {
            url = url.replace(Integer.toString(i), "");
        }

        return url;
    }

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

}
