/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.syncframe;

import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author icerus
 * @param <T>
 */
public class SyncMechAtomic<T> extends SyncMech{
    private T SyncedObject;
    private final AtomicReference AtomicObject;
    static{
        System.out.println("Sync Atomic registering");
        Synchroniser.RegisterNewSyncMech(getName());
    }
    
    public SyncMechAtomic(T o){
        super(o);
        SyncedObject = o;
        AtomicObject = new AtomicReference(o);
    }

    static String getName() {
        return "Atomic and CAS protected Object";
    }

    @Override
    T Read() {
        return (T) AtomicObject.get();
    }

    @Override
    boolean Write(Object o, Object n) {
        return AtomicObject.compareAndSet(o, n);
    }

    @Override
    boolean CleanUp() {
        return true;
    }
    
}
