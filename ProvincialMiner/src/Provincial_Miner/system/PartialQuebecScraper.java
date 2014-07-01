/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Provincial_Miner.system;

import Provincial_Miner.application.Speaker;
import com.gtranslate.Language;
import com.gtranslate.Translator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static jdk.nashorn.internal.objects.NativeArray.map;
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

    /**
     * @param firstLetter
     * @param session
     * @return
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
                    System.out.println();
                    Speaker newSpeaker = new Speaker(name);

                    //set the speaker's url
                    newSpeaker.setURL(domain + speaker.select("a").attr("href"));

                    //add to the list
                    searchedSpeakers.put(name, newSpeaker);
                }

            } catch (IOException ex) {
                System.out.println("Error on letter index");
            }
        }

        return speakerList;
    }

    /**
     *
     * @param name
     * @param session
     * @return
     */
    public ArrayList<String> getTopics(String name, String session) {
        //string for the url
        String url = new String();

        //list of topics
        ArrayList<String> topics = new ArrayList<>();

        //if the URL has not been found already
        if (!searchedSpeakers.containsKey(name)) {
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
                }
            }
        } catch (IOException ex) {
            System.out.println("Error connecting to member topic page");
        }

        return topics;
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

}
