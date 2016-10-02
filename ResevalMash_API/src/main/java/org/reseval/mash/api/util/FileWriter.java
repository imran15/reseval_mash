/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.util;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author admin
 */
public class FileWriter {
    
 
    BufferedWriter out;
    public  void writeToFile(String text){
        try {
            out = new BufferedWriter(new java.io.FileWriter("mashlog.txt", true));
            out.write(text);
            out.newLine(); 
            out.close();
        }catch(IOException e){
            System.out.println("There was a problem:" + e);
        }
    }
}
