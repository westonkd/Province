/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Provincial_Miner.application.Session;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author user
 */
public class SessionTest {
    
    public SessionTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

   @Test
   public static void testContent() {
       Session testSession = new Session();
       
       testSession.setContent("New Content!");
       
       Assert.assertEquals("New Content!", testSession.getContent());
   }
   
   @Test
   public static void testDate() {
       Session testSession = new Session();
       
       testSession.setDate(2002, 5,5);
       
       Assert.assertEquals(testSession.getDate().toString(),"2002-05-05");
   }
}
