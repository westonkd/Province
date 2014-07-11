/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import Provincial_Miner.application.Content;
import Provincial_Miner.application.Speaker;
import Provincial_Miner.system.PartialQuebecScraper;
import Provincial_Miner.system.WriteFile;
import java.io.File;
import java.util.ArrayList;
import org.testng.annotations.Test;

/**
 *
 * @author cameronthomas
 */
public class WriteFileTest {   
    @Test
    public void SpeakerToTopicWriterTest() {
        String theSpeaker = "Cameron Thomas";
        String topic = "CS 246";
        String base = "CS 246";
        String content = "This is the content";
        //HashMap<String, List<Session>> topicMap = new HashMap<>();
        
      PartialQuebecScraper scraper = new PartialQuebecScraper();
        
        
       ArrayList<Speaker> listOfSpeakers = scraper.getSession("&Session=rd11l4se");
        
    //    ArrayList<Speaker> listOfSpeakers = new ArrayList();
    /*
        // Loop to add new speakers to hasmap
        for (int i = 0; i < 10; i++) {
          listOfSpeakers.add(new Speaker(theSpeaker));
              
          // Loop to add new topics
          for (int j = 0; j < 10; j++) {
              listOfSpeakers.get(i).getTopics().put(topic, new ArrayList<Content>());
              
              // Loop to add session to each topic
              for (int k = 0; k < 3; k++) {
                 listOfSpeakers.get(i).getTopics().get(topic).add(new Content());            
              }
              
              // Loop to set content and date in each session
              for (Content testSession: listOfSpeakers.get(i).getTopics().get(topic)) {
                  testSession.setContent(content);
                  testSession.setDate(2012, 12, 4);                
              }           
              
              topic = "topic" + j;
          }   
          
         theSpeaker = "Cameron Thomas" + i;   
        }
        */
        
        
        new WriteFile().PersonXmlWriter(listOfSpeakers);
      //  new WriteFile().writeDataFile("This is the content", "Cameron Thomas", "War");
    }
     
    @Test
    public void TopicToSpeakerWriterTest() {
        /*
       
        String theTopic = "War0";
        String speaker = "Cameron";
        String content = "This is the content";
        //HashMap<String, List<Session>> topicMap = new HashMap<>();
        
        ArrayList<Topic> listOfTopics = new ArrayList<Topic>();
    
        // Loop to add new topic to hasmap
        for (int i = 0; i < 10; i++) {
          listOfTopics.add(new Topic(theTopic));
          
          // Loop to add new speakers
          for (int j = 0; j < 5; j++) {
              listOfTopics.get(i).getSpeakers().put(speaker, new ArrayList<Session>());
              
              // Loop to add session to each speaker
              for (int k = 0; k < 10; k++) {
                 listOfTopics.get(i).getSpeakers().get(speaker).add(new Content());            
              }
              
              // Loop to set content and date in each session
              for (Content testSession: listOfTopics.get(i).getSpeakers().get(speaker)) {
                  testSession.setContent(content);
                  testSession.setDate(2014, 12, 1);                
              }           
              speaker += "t";                       
          }                
          theTopic+= (i +1);
        }
        
       new WriteFile().TopicXmlWriter(listOfTopics);
        
        Content testSession = new Content();
       
       testSession.setContent("New Content!");
       
       Assert.assertEquals("New Content!", testSession.getContent()); 
        */
        
    }
}
