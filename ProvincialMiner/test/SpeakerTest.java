/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Provincial_Miner.application.Content;
import Provincial_Miner.application.Session;
import Provincial_Miner.application.Speaker;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Weston Dransfield
 */
public class SpeakerTest {

    Speaker speaker = new Speaker();

    Speaker speakerNamed = new Speaker("John Smith");

    public SpeakerTest() {

    }

    @Test
    public void nameTest() {
        System.out.println(speakerNamed.getFirstName());
        System.out.println(speakerNamed.getLastName());
    }

    @Test
    public void topicsTest() {
        Content banana = new Content();
        banana.setContent("banana");
        
        Content apple = new Content();
        apple.setContent("apple");
        
        Content orange = new Content();
        orange.setContent("orange");
        
        speaker.addContent("Fruit", orange);
        speaker.addContent("Fruit", apple);
        speaker.addContent("Fruit", banana);
        
        Content goat = new Content();
        goat.setContent("goat");
        
        Content dog = new Content();
        dog.setContent("dog");
        
        speaker.addContent("Animals", goat);
        speaker.addContent("Animals", dog);
        
        HashMap<String, List<Content>> map = speaker.getTopics();
        for (List<Content> topic : map.values()) {
            for (Content content : topic) {
                System.out.println(content.getContent());
            }
        }
        
        //print out the keys
        System.out.println("=== keys ===");
        for (String key : map.keySet()) {
            System.out.println(key);
        }
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
