/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.hh497.syncframe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author icerus
 */
public class SyncMechLock<T> extends SyncMech{
    private T SyncedObject;
    private final Lock ObjectLock;
    static{
        System.out.println("Sync Lock registering");        
        Synchroniser.RegisterNewSyncMech(getName());
    }
    
    public SyncMechLock(Object o){
        super(o);
        ObjectLock = new ReentrantLock();
    }

    static String getName() {
        return "Mutual Exclusion Lock";
    }

    @Override
    T Read() {
        ObjectLock.lock();
        try {
            return SyncedObject;
        } finally {
            ObjectLock.unlock();
        }
    }

    @Override
    boolean Write(T o, T n) {
        ObjectLock.lock();
        try {
            SyncedObject = n;
            return true;
        } finally {
            ObjectLock.unlock();
        }
    }

    @Override
    boolean CleanUp() {
        return true;
    }
    
}
