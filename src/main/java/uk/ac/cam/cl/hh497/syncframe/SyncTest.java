/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.hh497.syncframe;

/**
 *
 * @author icerus
 * @param <T>
 */
public class SyncTest<T> extends SyncMech{
    private T SyncedObject;
    
    static{
        System.out.println("Sync Test registering");
        /*try{
            Synchroniser.RegisterNewSyncMech(getName(),SyncTest);
        }catch(NoSuchMethodException | SecurityException ex){
            System.err.println(ex);
        }*/
        Synchroniser.RegisterNewSyncMech(getName());
    }

    public SyncTest(T t) {
        super(t);
        SyncedObject = t;
        System.out.println("Sync Test initialised");
    }

    static String getName() {
        return "SyncTest";
    }

    @Override
    T Read() {
        System.out.println("Read data from object o!");
        return SyncedObject;
    }

    boolean Write(T o, T n){
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
