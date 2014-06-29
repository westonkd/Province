/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Provincial_Miner.application.Session;
import Provincial_Miner.application.Speaker;
import Provincial_Miner.application.Topic;
import Provincial_Miner.system.WriteFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author cameronthomas
 */
public class WriteFileTest {
    
    
    
    @Test
    public WriteFileTest() {
        String theSpeaker = "Cameron0";
        String topic = "CS 246";
        String content = "This is the content";
        //HashMap<String, List<Session>> topicMap = new HashMap<>();
        
        ArrayList<Speaker> listOfSpeakers = new ArrayList<Speaker>();
    
        // Loop to add new speakers to hasmap
        for (int i = 0; i < 5; i++) {
          listOfSpeakers.add(new Speaker(theSpeaker));
          
          // Loop to add new topics
          for (int j = 0; j < 5; j++) {
              listOfSpeakers.get(i).getTopics().put(topic, new ArrayList<Session>());
              
              // Loop to add session to each topic
              for (int k = 0; k < 5; k++) {
                 listOfSpeakers.get(i).getTopics().get(topic).add(new Session());            
              }
              
              // Loop to set content and date in each session
              for (Session testSession: listOfSpeakers.get(i).getTopics().get(topic)) {
                  testSession.setContent(content);
                  testSession.setDate(2014, 12, 1);                
              }           
              topic += "t";                       
          }                
          theSpeaker+= (i +1);
        }
        
        new WriteFile().PersonXmlWriter(listOfSpeakers);
        
        Session testSession = new Session();
       
       testSession.setContent("New Content!");
       
       Assert.assertEquals("New Content!", testSession.getContent());  
                
    }
    
    @Test
    public void TopicToSpeakerWriterTest() {
        String theTopic = "War0";
        String speaker = "Cameron";
        String content = "This is the content";
        //HashMap<String, List<Session>> topicMap = new HashMap<>();
        
        ArrayList<Topic> listOfTopics = new ArrayList<Topic>();
    
        // Loop to add new topic to hasmap
        for (int i = 0; i < 5; i++) {
          listOfTopics.add(new Topic(theTopic));
          
          // Loop to add new speakers
          for (int j = 0; j < 5; j++) {
              listOfTopics.get(i).getSpeakers().put(speaker, new ArrayList<Session>());
              
              // Loop to add session to each speaker
              for (int k = 0; k < 5; k++) {
                 listOfTopics.get(i).getSpeakers().get(speaker).add(new Session());            
              }
              
              // Loop to set content and date in each session
              for (Session testSession: listOfTopics.get(i).getSpeakers().get(speaker)) {
                  testSession.setContent(content);
                  testSession.setDate(2014, 12, 1);                
              }           
              speaker += "t";                       
          }                
          theTopic+= (i +1);
        }
        
        new WriteFile().TopicXmlWriter(listOfTopics);
        
        Session testSession = new Session();
       
       testSession.setContent("New Content!");
       
       Assert.assertEquals("New Content!", testSession.getContent()); 
        
        
    }
}
