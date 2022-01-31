/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.hh497.syncframe;

        
        
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println(SyncTest.getName());
        int i = 5;
        SyncTest s = new SyncTest<Integer>(5);
        //Synchroniser.Initialise(i, );
        System.out.println(Synchroniser.Read(i));
    }
    
}
