/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Provincial_Miner.system;

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
    
    final String memberURL = "http://www.assnat.qc.ca/fr/travaux-parlementaires/journaux-debats/index-jd/recherche.html?cat=v&Session=jd36l1se&Section=particip&Requete=";
    
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
        
        Document memberPage;
        Elements membersOnPage;
        
        for (int i = firstLetter; i <= 'z'; i++) {
            System.out.println((char) i + "...");
            try {
                //get the next page in alphabetical order
                memberPage = Jsoup.connect(memberURL + (char) i).get();
                
                //get all members on the page
                membersOnPage = memberPage.select("dd");
                
                //add the members to the memers map with their various statements
                for (Element ember : membersOnPage) {
                    
                }
                
                
            } catch (IOException ex) {
                
            }
        }
    }
    
    /**
     *
     */
    @Override
    public void parseMembers() {
        
    }
    
    /**
     *
     */
    @Override
     public void parseTopics() {
        
    }

}
