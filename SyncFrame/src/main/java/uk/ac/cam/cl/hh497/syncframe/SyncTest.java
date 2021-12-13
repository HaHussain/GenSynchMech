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
public class SyncTest extends SyncMech{
    private Object SyncedObject;
    
    static{
        System.out.println("Sync Test registering");
        Synchroniser.RegisterNewSyncMech(SyncTest.class);
    }

    public SyncTest(Object o) {
        super(o);
        System.out.println("Sync Test initialised");
    }

    @Override
    String getName() {
        return "SyncTest";
    }

    @Override
    Object Read() {
        System.out.println("Read data from object o!");
        return SyncedObject;
    }

    @Override
    boolean Write(Object o, Object n) {
        System.out.println("Written data to object o!");
        SyncedObject = n;
        return true;
    }

    @Override
    boolean CleanUp() {
        System.out.println("Cleaned-up sync mechanism on object o!");
        return true;
    }
    
}
