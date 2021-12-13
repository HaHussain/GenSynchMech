/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.hh497.syncframe;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author icerus
 */
public class Synchroniser {
    private static final ArrayList SyncMechList = new ArrayList<Class>();
    private static final HashMap SyncedObjects = new HashMap<Object, SyncMech>();
    private static final boolean debug = true;
    
    static void RegisterNewSyncMech(Class SyncClass){
        SyncMechList.add(SyncClass);
        if(debug){
            System.out.println(SyncClass.getSimpleName() + " registered as " + SyncClass.getName());
            SyncClass.getConstructor(Class[Object]).newInstance(Object[o]);
        }
    }
    
    static boolean Initialise(Object o, String s){
        for(int i = 0; i < SyncMechList.size(); i++){
            if (SyncMechList.get(i).getName().equals(s)){
                SyncMech a = new SyncMechList.get(i);
                SyncedObjects.put(o, a);
                return true;
            }
        return false;
        }
    }
    
    static boolean Write(Object o, Object n){
        if(SyncedObjects.get(o).Write(o, n)){
            return true;
        }else{
            return false; 
        }
    }
    
    static Object Read(Object o){
        return SyncedObjects.get(o).Read(o);
    }
    
    static boolean DeSync(Object o){
        if(SyncedObjects.get(o).Cleanup()){
            SyncedObjects.remove(o);
            return true;
        }else{
            return false;
        }
    }
}
