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
import static java.time.LocalDate.now;

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
        result = person + "\n";
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Stephen\\Desktop\\Person.txt"))) {
            String sCurrentLine = "";
            boolean personFound = false;
            boolean dateRange = false;
            LocalDate date = now();
            System.out.println("find person");
            while (true) {
                sCurrentLine = br.readLine();
                if (sCurrentLine.equals("</" + person + ">")) {
                    personFound = false;
                    break;
                }
                if (personFound) {
                    if (sCurrentLine.equals("<Date>")) {
                        sCurrentLine = br.readLine();
                        String[] parts = sCurrentLine.split("/");
                        int year = Integer.parseInt(parts[2]);
                        int month = Integer.parseInt(parts[0]);
                        int day = Integer.parseInt(parts[1]);
                        date = date.of(year, month, day);
                        br.readLine();
                        if ((date.isAfter(startDate) || date.isEqual(startDate))
                                && (date.isBefore(endDate) || date.isEqual(endDate))) {
                            dateRange = true;
                        } else{
                            dateRange = false;
                            
                        }

                    }
                    if (dateRange && personFound) {
                        result = result + sCurrentLine + "\n";
                    }
                }
                if (sCurrentLine.equals("<" + person + ">")) {
                    personFound = true;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(result);
        return result;
    }

    public String searchBoth(String person, String topic, LocalDate startDate, LocalDate endDate) {

        return result;
    }
}
