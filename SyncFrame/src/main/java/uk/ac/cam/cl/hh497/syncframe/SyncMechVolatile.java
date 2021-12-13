/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.hh497.syncframe;


/**
 *
 * @author icerus
 */
public class SyncMechVolatile extends SyncMech{
    private volatile Object SyncedObject;
    static{
        System.out.println("Sync Volatile registering");
        Synchroniser.RegisterNewSyncMech(SyncTest.class);
    }
    
    public SyncMechVolatile(Object o){
        super(o);
    }

    @Override
    String getName() {
        return "Volatile Memory Barrier";
    }

    @Override
    Object Read() {
        return SyncedObject;
    }

    @Override
    boolean Write(Object o, Object n) {
        SyncedObject = n;
        return true;
    }

    @Override
    boolean CleanUp() {
        return true;
    }
    
}
