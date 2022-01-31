/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.hh497.staticanalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author icerus
 */
public class JavaFile {
    private final File origFile;
    private final ArrayList<ArrayList<String>> tokens = new ArrayList<>();
    
    JavaFile(File f){
        origFile = f;
        try(Scanner myReader = new Scanner(f).useDelimiter("[;{}]")) { //Delimiter not actually used
          while(myReader.hasNextLine()) {
            String data = myReader.nextLine();
            tokens.add(new ArrayList<>(Arrays.asList(data.split(" "))));
          }
          myReader.close();
        } catch (FileNotFoundException e) {
          System.err.println("An error occurred.");
        }
    }
    
    JavaFile(File f, ArrayList<ArrayList<String>> t){
        origFile = f;
        tokens.addAll(t);
    }
    
    public ArrayList getTokens(){
        return tokens;
    }
    
    public File getOrigFile(){
        return origFile;
    }
    
    public void WriteTokens(File f){
        try(FileWriter fw = new FileWriter(f)){
            for(ArrayList s : tokens){
                fw.write(String.join(" ", s) + "\n");
            }
            fw.close();
        }catch(IOException ex){
            System.err.println(ex);
        }
    }
}
