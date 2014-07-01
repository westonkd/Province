/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Provincial_Miner.system.PartialQuebecScraper;
import java.util.ArrayList;
import java.util.Map;
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
    //This is a test to get all name and topics in a given session
    public void showNames() {
        ArrayList<String> topics = new ArrayList<>();

        for (String name : names) {
            System.out.println("===================================");
            topics = scraper.getTopics(name, "&Session=rd11l4se");

            for (String topic : topics) {
                System.out.println(topic);
            }

            System.out.println(name);
        }
    }

    @Test
    //test for just one name
    public void singleName() {
        System.out.println("Starting single query");
        ArrayList<String> topics = scraper.getTopics(names.get(0), "&Session=rd11l4se");
        
        for (String topic : topics) {
            System.out.println(topic);
        }
    }

}
