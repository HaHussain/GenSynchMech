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
public class SyncMechLock extends SyncMech{
    private Object SyncedObject;
    private final Lock ObjectLock;
    static{
        System.out.println("Sync Lock registering");
        Synchroniser.RegisterNewSyncMech(SyncTest.class);
    }
    
    public SyncMechLock(Object o){
        super(o);
        ObjectLock = new ReentrantLock();
    }

    @Override
    String getName() {
        return "Mutual Exclusion Lock";
    }

    @Override
    Object Read() {
        ObjectLock.lock();
        try {
            return SyncedObject;
        } finally {
            ObjectLock.unlock();
        }
    }

    @Override
    boolean Write(Object o, Object n) {
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
