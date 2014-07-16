/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Provincial_Miner.system;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import java.io.File;
import java.util.ArrayList;

/**
 * Gathers all the searchable files and returns an array of Strings with 
 * valid files
 * @author Stephen
 */
public class FileFinder {

    /**
     * grabs all the files in the directory that are valid for search
     * @return
     */
    public ArrayList<String> findFiles() {
        ArrayList<String> validFiles = new ArrayList<String>();
        File dir = new File((System.getProperty("user.home") + ("/Documents/SpeakerFile_files/")));
        // array of strings for file names
        String[] children = dir.list();
        //if there is no files in directory
        if (children == null) {
            System.out.println("Either dir does not exist or is not a directory");
        } else {
            for (int i = 0; i < children.length; i++) {
                String filename = children[i];
                //splits at the period to remove extension
                String[] parts = filename.split("\\.");
                // add to the arraylist
                if (parts[0].equals("Session")) {
                        validFiles.add(filename);
                }
            }
        }
        return validFiles;
    }
}
