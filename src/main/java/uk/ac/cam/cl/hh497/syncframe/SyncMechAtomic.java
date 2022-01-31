/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.hh497.syncframe;

import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author icerus
 */
public class SyncMechAtomic<T> extends SyncMech{
    private T SyncedObject;
    private final AtomicReference AtomicObject;
    static{
        System.out.println("Sync Atomic registering");
        Synchroniser.RegisterNewSyncMech(getName());
    }
    
    public SyncMechAtomic(Object o){
        super(o);
        AtomicObject = new AtomicReference(o);
    }

    static String getName() {
        return "Atomic and CAS protected Object";
    }

    @Override
    T Read() {
        return AtomicObject.get();
    }

    @Override
    boolean Write(T o, T n) {
        return AtomicObject.compareAndSet(o, n);
    }

    @Override
    boolean CleanUp() {
        return true;
    }
    
}
