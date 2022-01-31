/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.hh497.staticanalysis;

import java.io.File;

/**
 *
 * @author icerus
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File f = new File(args[0]);
        JavaFile j = new JavaFile(f);
        System.out.println(j.getTokens());
        j.WriteTokens(new File("./Test2"));
    }
    
}
