package uk.ac.cam.cl.syncframe;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import uk.ac.cam.cl.syncframe.Synchroniser;
import uk.ac.cam.cl.syncframe.SyncMechLock;
import java.util.concurrent.locks.ReentrantLock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author icerus
 */
public class LockTests {
    
    public LockTests() {
    }
    
    int y = 0;
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        y = 0;
    }
    
    @AfterEach
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void ReentryLockAddAndSubtract(){
        final ReentrantLock yLock = new ReentrantLock();
        Runnable Ex1 = () -> {
            try{yLock.lock();
            //System.out.println("Locked");
            
            y += 1;
            }finally{
            //System.out.println("Unlocked");
            yLock.unlock();
            }
            System.out.println(y);
        };
        Runnable Ex2 = () -> {
            try{yLock.lock();
            //System.out.println("Locked");
            
            y -= 1;
            }finally{
            //System.out.println("Unlocked");
            yLock.unlock();
            }
            System.out.println(y);
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
    
    @Test
    public void SyncroniserLockAddAndSubtract(){
        SyncMechLock<Integer> k = new SyncMechLock<>(y);
        Synchroniser.Initialise(y, k);
        Runnable Ex1 = () -> {
            k.LockAcquire();
            k.LockedWrite(y, k.LockedRead() + 1);
            k.LockRelease();
        };
        Runnable Ex2 = () -> {
            k.LockAcquire();
            k.LockedWrite(y, k.LockedRead() - 1);
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
