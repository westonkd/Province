/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Provincial_Miner.system.QuebecScraper;
import Provincial_Miner.system.Scraper;
import org.testng.annotations.Test;

/**
 *
 * @author user
 */
public class ScraperTest {
    
    public ScraperTest() {

        
    }

    @Test
    public void urlTest() {
        Scraper qTest = new QuebecScraper();
        qTest.scrape('a');
    }
    
}
