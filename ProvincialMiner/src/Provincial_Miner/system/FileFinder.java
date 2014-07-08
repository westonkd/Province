/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Provincial_Miner.system;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import java.io.File;
import java.io.FilenameFilter;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Stephen
 */
public class FileFinder {
    //ArrayList<String> allFiles;
    //ArrayList<String> validFiles;
/**
 * 
 * @param startDate
 * @param endDate
 * @return 
 */
    public ArrayList<String> findFiles(LocalDate startDate,LocalDate endDate) {
        ArrayList<String> validFiles = new ArrayList<String>();
        File dir = new File("C:\\Users\\Stephen\\Desktop\\SpeakerFile_files");

        String[] children = dir.list();
        if (children == null) {
            System.out.println("Either dir does not exist or is not a directory");
        } else {
            for (int i = 0; i < children.length; i++) {
                String filename = children[i];
                
                String[] parts = filename.split("\\.");
              
                String[] date = parts[1].split("-");
                int year = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int day = Integer.parseInt(date[2]);
                System.out.println(filename);
                LocalDate dateCheck = null;
                // sets local date 
                dateCheck = dateCheck.of(year, month, day);
                if ((dateCheck.isAfter(startDate) || dateCheck.isEqual(startDate))
                        && (dateCheck.isBefore(endDate) || dateCheck.isEqual(endDate))){
                    validFiles.add(filename);
             
            }
        }
    }
        return validFiles;
    }
}



