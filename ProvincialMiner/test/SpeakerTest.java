/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Provincial_Miner.application.Session;
import Provincial_Miner.application.Speaker;
import java.util.HashMap;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author user
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
        Session banana = new Session();
        banana.setContent("banana");
        
        Session apple = new Session();
        apple.setContent("apple");
        
        Session orange = new Session();
        orange.setContent("orange");
        
        speaker.addSession("Fruit", orange);
        speaker.addSession("Fruit", apple);
        speaker.addSession("Fruit", banana);
        
        Session goat = new Session();
        goat.setContent("goat");
        
        Session dog = new Session();
        dog.setContent("dog");
        
        speaker.addSession("Animals", goat);
        speaker.addSession("Animals", dog);
        
        HashMap<String, List<Session>> map = speaker.getTopics();
        for (List<Session> topic : map.values()) {
            for (Session content : topic) {
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
