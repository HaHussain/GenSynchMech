/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.syncframe;


/**
 *
 * @author icerus
 */
public class SyncMechVolatile<T> extends SyncMech{
    private volatile T SyncedObject;
    static{
        System.out.println("Sync Volatile registering");
        Synchroniser.RegisterNewSyncMech(getName());
    }
    
    public SyncMechVolatile(T o){
        super(o);
        SyncedObject = o;
    }

    static String getName() {
        return "Volatile Memory Barrier";
    }

    @Override
    T Read() {
        return SyncedObject;
    }

    @Override
    boolean Write(Object o, Object n) {
        SyncedObject = (T) n;
        return true;
    }

    @Override
    boolean CleanUp() {
        return true;
    }
    
}
