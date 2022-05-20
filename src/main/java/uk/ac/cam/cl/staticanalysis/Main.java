/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.staticanalysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author icerus
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length < 1){
            System.out.println("Please give a path to a java file you wish to generate synchronisation mechanisms for.");
            return;
        }
        
        File OriginalFile = new File(args[0]);
        String Text = "";
        try(Scanner myReader = new Scanner(OriginalFile)){
            while(myReader.hasNextLine()) {
                Text += myReader.nextLine();
                myReader.close();
            }
        } catch (FileNotFoundException e) {
          System.err.println("Cannot find the file described by that path.");
          return;
        }
        
        Tree AST = new Tree(Text);
        GroupAnalyser.GenerateSyncMechs(AST);
        
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(OriginalFile.getName()+"Synchronised"));
            writer.write(AST.toString());
            writer.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
}
