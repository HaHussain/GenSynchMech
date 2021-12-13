/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.hh497.syncframe;

import java.util.concurrent.Semaphore;

/**
 *
 * @author icerus
 */
public class SyncMechSemaphore extends SyncMech{
    private Object SyncedObject;
    private final Semaphore ObjectFlag;
    static{
        System.out.println("Sync Semaphore registering");
        Synchroniser.RegisterNewSyncMech(SyncTest.class);
    }
    
    public SyncMechSemaphore(Object o, int i){
        super(o);
        ObjectFlag = new Semaphore(i);
    }

    @Override
    String getName() {
        return "Semaphore";
    }

    @Override
    Object Read() {
        try {
            ObjectFlag.acquire();
            return SyncedObject;
        } catch (InterruptedException ex) {
            return null;
        } finally{
            ObjectFlag.release();
        }
    }

    @Override
    boolean Write(Object o, Object n) {
        try {
            ObjectFlag.acquire();
            SyncedObject = n;
            return true;
        } catch (InterruptedException ex) {
            return false;
        } finally {
            ObjectFlag.release();
        }
    }

    @Override
    boolean CleanUp() {
        return true;
    }
    
}
