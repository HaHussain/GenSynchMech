/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.syncframe;

import uk.ac.cam.cl.syncframe.Synchroniser;
import uk.ac.cam.cl.syncframe.SyncMechLock;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author icerus
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       int y = 0;
       SyncMechLock<Integer> k = new SyncMechLock<>(y);
        Synchroniser.Initialise(y, k);
        Runnable Ex1 = () -> {
            k.LockAcquire();
            int x = k.LockedRead() + 1;
            k.LockedWrite(y, x);
            System.out.println(x);
            k.LockRelease();
        };
        Runnable Ex2 = () -> {
            k.LockAcquire();
            int x = k.LockedRead() - 1;
            k.LockedWrite(y, x);
            System.out.println(x);
            k.LockRelease();
        };
        
        Thread[] threadArray1 = new Thread[100];
        Thread[] threadArray2 = new Thread[100];
        for(int i = 0; i < threadArray1.length; i++){
            threadArray1[i] = new Thread(Ex1);
            threadArray2[i] = new Thread(Ex2);
        }
        for(int i = 0; i < threadArray1.length; i++){
            threadArray1[i].start();
            threadArray2[i].start();
        }
        try{
            for(int i = 0; i < threadArray1.length; i++){
                threadArray1[i].join();
                threadArray2[i].join();
            }
        }catch(InterruptedException e){
            System.out.println(e);
        }
        
        System.out.println("Final value:" + y);
        assertEquals(y, 0);
    }
    
}
