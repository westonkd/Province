
import Provincial_Miner.application.Speaker;
import Provincial_Miner.system.PartialQuebecScraper;
import Provincial_Miner.system.WriteFile;
import java.util.ArrayList;
import org.testng.annotations.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stephen
 */
public class MassDownload implements Runnable {
    
     PartialQuebecScraper scraper = new PartialQuebecScraper();
int is = 0;
int js = 0;

    public int getIs() {
        return is;
    }

    public void setIs(int is) {
        this.is = is;
    }

    public int getJs() {
        return js;
    }

    public void setJs(int js) {
        this.js = js;
    }
    public MassDownload() {
    }


    public void run() {
       
                if (scraper.sessionExists(is, js)) {
                    System.out.println("Downloading: " + is + "-" + js);
                    
                    //download the session
                    ArrayList<Speaker> speakers = scraper.getSession(scraper.getSessionQuery(is, js));

                    //write the session to an XML file
                    new WriteFile().PersonXmlWriter(speakers);
               
        }
    }
    public static void main(String args[]){
        
          for (int i = 17; i < 42; i++) {
            if (i == 21) {
                i = 36;
            }
            
            for ( int j = 1; j <= 4; j++) {
                MassDownload md = new MassDownload();
                md.change(i,j);
                Thread download = new Thread(md);
                download.start();
                
            }
    }
    }
    
    public void change(int i, int j){
        is = i;
        js = j;
    }
}
    

