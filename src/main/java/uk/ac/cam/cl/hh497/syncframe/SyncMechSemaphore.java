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
public class SyncMechSemaphore<T> extends SyncMech{
    private T SyncedObject;
    private final Semaphore ObjectFlag;
    static{
        System.out.println("Sync Semaphore registering");
        Synchroniser.RegisterNewSyncMech(getName());
    }
    
    public SyncMechSemaphore(Object o, int i){
        super(o);
        ObjectFlag = new Semaphore(i);
    }

    static String getName() {
        return "Semaphore";
    }

    @Override
    T Read() {
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
    boolean Write(T o, T n) {
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
