/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.syncframe;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author icerus
 * @param <T>
 */
public class SyncMechLock<T> extends SyncMech{
    private T SyncedObject;
    private final ReentrantLock ObjectLock;
    static{
        System.out.println("Sync Lock registering");        
        Synchroniser.RegisterNewSyncMech(getName());
    }
    
    public SyncMechLock(T o){
        super(o);
        SyncedObject = o;
        ObjectLock = new ReentrantLock();
    }

    static String getName() {
        return "Mutual Exclusion Lock";
    }
    
    public void LockAcquire(){
        ObjectLock.lock();
    }
    
    public void LockRelease(){
        ObjectLock.unlock();
    }
    
    public T LockedRead(){
        if(ObjectLock.isHeldByCurrentThread()){
            return SyncedObject;
        }
        return null;
    }
    
    public boolean LockedWrite(T o, T n){
        if(ObjectLock.isHeldByCurrentThread()){
            SyncedObject = n;
            return true;
        }
        return false;
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
    boolean Write(Object o, Object n) {
        ObjectLock.lock();
        try {
            SyncedObject = (T) n;
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
