/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Provincial_Miner.system;

import Provincial_Miner.application.Speaker;
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

    /**
     *
     */
    @Override
    public void scrape() {

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
                    scrapeMembers(firstMemberURL + session + lastMemberURL + (char) i);
                } catch (IOException ex) {
                    System.out.println("Error connecting to url: " + session);
                }
            }
        }

    }
    
    private void scrapeMembers(String urlToScrape) throws IOException {
        //attempt to open the url
        Document doc = Jsoup.connect(urlToScrape).get();
        
        //get all dd elements (each member is contained in one dd element)
        Elements members = doc.select("dd");
        
        //loop through each members "topics" page
        for (Element member : members) {
            //get the new speakers name
            String name = member.text();
            name = name.substring(0,name.indexOf("("));
            
            //create the new speaker
            Speaker newSpeaker = new Speaker(name);
            
            //fi
            String memerURL = member.select("a").attr("href");
            System.out.println(newspeaker.getName());
        }
        
    }

}
