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
public class SyncMechAtomic extends SyncMech{
    private Object SyncedObject;
    private final AtomicReference AtomicObject;
    static{
        System.out.println("Sync Atomic registering");
        Synchroniser.RegisterNewSyncMech(SyncTest.class);
    }
    
    public SyncMechAtomic(Object o){
        AtomicObject = new AtomicReference(o);
    }

    @Override
    String getName() {
        return "Atomic and CAS protected Object";
    }

    @Override
    Object Read() {
        return AtomicObject.get();
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
