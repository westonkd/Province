/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Provincial_Miner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

/**
 *
 * @author Stephen
 */
public class Librarian {

    String result;

    public Librarian() {

    }

    /**
     *
     * @param topic
     * @param startDate
     * @param endDate
     * @return
     */
    public String searchTopic(String topic, LocalDate startDate, LocalDate endDate) {
        result = topic + "\n";
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Stephen\\Desktop\\topics.xml"))) {
            String sCurrentLine = "";
            boolean read = false;
            while (true) {
                sCurrentLine = br.readLine();
                if (sCurrentLine.equals("</" + topic + ">")) {
                    read = false;
                    break;
                }
                 if (read) {
                    result = result + sCurrentLine + "\n";
                }
               // System.out.println(sCurrentLine);
                if (sCurrentLine.equals("<" + topic + ">")) {
                    read = true;
                }
                
               
               
            }
System.out.print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String searchPerson(String person, LocalDate startDate, LocalDate endDate) {

        return result;
    }

    public String searchBoth(String person, String topic, LocalDate startDate, LocalDate endDate) {

        return result;
    }
}
