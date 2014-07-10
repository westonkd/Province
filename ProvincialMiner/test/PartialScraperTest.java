/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Provincial_Miner.application.Speaker;
import Provincial_Miner.system.PartialQuebecScraper;
import java.util.ArrayList;
import org.testng.annotations.Test;

/**
 *
 * @author user
 */
public class PartialScraperTest {

    PartialQuebecScraper scraper = new PartialQuebecScraper();
    ArrayList<String> names = scraper.getNames('a', "&Session=rd11l4se");

    public PartialScraperTest() {
    }

    @Test
    public void getSession() {
        ArrayList<Speaker> speakers = scraper.getSession("&Session=rd11l4se");
        
        for(Speaker speaker : speakers) {
            System.out.println(speaker.getLastName());
        }
    }
    
 

}
