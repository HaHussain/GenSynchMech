/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.hh497.staticanalysis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author icerus
 */
public class Analyser {
    protected static String analyse(){
        return "Mutual Exclusion Lock";
    }
    
    protected static JavaFile convert(JavaFile t){
        ArrayList<ArrayList<String>> tokens = t.getTokens();
        Map<String, Integer> SyncVariables = new HashMap<>();
        
        
        for(int i = 0; i < tokens.size(); i++){
            for(int j = 0; j < tokens.get(i).size(); j++){
                String word = tokens.get(i).get(j);
                
                String SharedVar;
                if(word.contains("SharedRead(")){
                    SharedVar = word.subSequence(word.indexOf("SharedRead("), word.indexOf(")")).toString();
                }else if(word.contains("SharedWrite(")){
                    SharedVar = word.subSequence(word.indexOf("SharedWrite("), word.indexOf(")")).toString();
                }
                if(!SyncVariables.containsKey(SharedVar)){
                    SyncVariables.put(SharedVar, i);
                }
                
                word = word.replaceAll("SharedRead(", "Synchroniser.Read(");
                word = word.replaceAll("SharedWrite(", "Synchroniser.Write(");
                tokens.get(i).add(j, word);
            }
            
            if(tokens.get(i).contains("import")){
                continue;
            }
        }
        File newFile = new File(t.getOrigFile().getAbsolutePath()+"Parsed");
        return new JavaFile(newFile,tokens);
    }
}
