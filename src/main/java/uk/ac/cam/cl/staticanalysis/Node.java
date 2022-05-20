/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.staticanalysis;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author icerus
 */
public class Node {
    private Node parent = null;
    private String value = null;
    private List<Node> children = new ArrayList<>();
    
    Node(ArrayList s){
        children = s;
    }
    
    Node(Node p, String v){
        parent = p;
        value = v;
    }
    
    void setParent(Node p){
        parent = p;
    }
    
    void setValue(String v){
        value = v;
    }
    
    void setChildren(ArrayList<Node> c){
        children = c;
    }
    
    Node getParent(){
        return parent;
    }
    
    String getValue(){
        return value;
    }
    
    List<Node> getChildren(){
        return children;
    }
    
    void AddChild(Node c){
        c.setParent(this);
        children.add(c);
    }
    
    void AddChild(String s){
        Node c = new Node(this, s);
        children.add(c);
    }
    
    void AddChildren(ArrayList<Node> c){
        for(Node t : c){
            t.setParent(this);
        }
        children.addAll(c);
    }
    
    ArrayList<Node> DepthFirstSearch(String s){
        ArrayList<Node> Results = new ArrayList<>();
        for(Node r : children){
            if(r.getValue().contains(s)){
                Results.add(r);
                Results.addAll(r.DepthFirstSearch(s));
            }
        }
        return Results;
    }
    
    @Override
    public String toString(){
        String Text = value;
        if(!children.isEmpty()){
            for(Node c : children){
                Text += c;
            }
        }
        return Text;
    }
}
