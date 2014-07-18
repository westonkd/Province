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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Weston Dransfield
 */
public class QuebecScraper implements Scraper {

    //query strings for known available sessions
    private String[] sessionQueries = {"&Session=rd11l4se", "&Session=rd12l1se",
        "&Session=rd12l2se", "&Session=rd12l3se",
        "&Session=rd12l4se", "&Session=rd13l1se",
        "&Session=rd13l2se", "&Session=rd13l3se",
        "&Session=rd13l4se", "&Session=rd14l1se",
        "&Session=rd14l2se", "&Session=rd14l3se",
        "&Session=rd15l1se", "&Session=rd15l2se",
        "&Session=rd15l3se", "&Session=rd15l4se",
        "&Session=rd16l1se", "&Session=rd16l3se",
        "&Session=rd16l4se", "&Session=rd17l1se",
        "&Session=rd17l2se", "&Session=rd18l1se",
        "&Session=rd18l2se", "&Session=rd20l1se",
            
        "&Session=rd34l2se", "&Session=rd34l3se",
        "&Session=rd35l1se", "&Session=rd35l2se",
        "&Session=rd36l1se", "&Session=rd36l2se",
        "&Session=rd37l1se", "&Session=rd37l2se",
        "&Session=rd38l1se", "&Session=rd39l1se",
        "&Session=rd40l1se"};
    

    private final String firstMemberURL = "http://www.assnat.qc.ca/fr/travaux-parlementaires/journaux-debats/index-jd/recherche.html?cat=v";
    private final String lastMemberURL = "&Section=particip&Requete=";
    private final String domain = "http://www.assnat.qc.ca/";

    /**
     *
     */
    @Override
    public void scrape() {
        //scrape from 'a' to 'z'
        scrape('a');
    }

    /**
     *
     * @param firstLetter
     */
    @Override
    public void scrape(char firstLetter) {
        //loop through each sessiont
        for (String session : sessionQueries) {
            //loop through each member in the session
            for (int i = firstLetter; i <= 'z'; i++) {
                try {
                    scrapeMembers(firstMemberURL + session + lastMemberURL + (char) i, session);
                } catch (IOException ex) {
                    System.out.println("Error connecting to url: " + session);
                }
            }
        }
    }

    private void scrapeMembers(String urlToScrape, String query) throws IOException {
        //attempt to open the url
        Document doc = Jsoup.connect(urlToScrape).get();

        //get all dd elements (each member is contained in one dd element)
        Elements members = doc.select("dd");

        //loop through each members "topics" page
        for (Element member : members) {
            //get the new speakers name
            String name = member.text();
            name = name.substring(0, name.indexOf("("));

            //create the new speaker
            Speaker newSpeaker = new Speaker(name);

            //set the speaker's url
            newSpeaker.setURL(domain + member.select("a").attr("href"));

            //get each topic the speaker has spoken on
            Document memberPage = Jsoup.connect(newSpeaker.getURL()).get();
            
            //get all anchor tags with a topic in the url
            Elements sessionLinks = memberPage.select("a");
            for (Element link : sessionLinks) {
                if (link.attr("href").contains(query)) {
                    //get the topic name
                    String topicName = getName(link.attr("href"));
                    
                    //get all content for the given topic
                    Document sessionPage = Jsoup.connect(domain + link.attr("href")).get();
                    Elements bolded = sessionPage.select("b");
                    
                    
                    //loop through bolded names and find content from given speaker
                    for (Element bold : bolded) {
                        if (bold.text().contains(newSpeaker.getLastName())) {
                            //then this bold's next sib is content we want!
                            System.out.println(bold.text());
                        }
                    }
                }
            }

        }
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
