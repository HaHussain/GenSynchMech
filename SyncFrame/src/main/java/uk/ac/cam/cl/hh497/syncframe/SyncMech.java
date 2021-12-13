package uk.ac.cam.cl.hh497.syncframe;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author icerus
 */
public abstract class SyncMech {
    private final Object SyncedObject;
    
    SyncMech(Object o){
        SyncedObject = o;
    }
    
    abstract String getName();
    
    abstract Object Read();
    
    abstract boolean Write(Object o, Object n);
    
    abstract boolean CleanUp();
    
    
}
