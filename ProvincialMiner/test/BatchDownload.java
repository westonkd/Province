/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Provincial_Miner.application.Speaker;
import Provincial_Miner.system.PartialQuebecScraper;
import Provincial_Miner.system.WriteFile;
import java.util.ArrayList;
import org.testng.annotations.Test;

/**
 *
 * @author Darin
 */
public class BatchDownload {

    PartialQuebecScraper scraper = new PartialQuebecScraper();

    public BatchDownload() {
    }

    @Test
    public void batchDownload() {
        for (int i = 40; i < 42; i++) {
            if (i == 21) {
                i = 36;
            }
            
            for (int j = 1; j <= 4; j++) {
                if (scraper.sessionExists(i, j)) {
                    System.out.println("Downloading: " + i + "-" + j);
                    
                    //download the session
                    ArrayList<Speaker> speakers = scraper.getSession(scraper.getSessionQuery(i, j));

                    //write the session to an XML file
                    new WriteFile().PersonXmlWriter(speakers);
                }
            }
        }
    }
}
