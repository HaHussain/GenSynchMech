/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.syncframe;

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
    
    public SyncMechSemaphore(T o, int i){
        super(o);
        SyncedObject = o;
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
    boolean Write(Object o, Object n) {
        try {
            ObjectFlag.acquire();
            SyncedObject = (T) n;
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
