/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.staticanalysis;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 *
 * @author icerus
 */
public class Tree {
    Node RootNode;
    ArrayList<Node> RootChildren;
    
    
    Tree(String Text){
        RootChildren = Parse(Text);
    }
    
    private ArrayList<Node> Parse(String Text){
        ArrayList<Node> Children = new ArrayList<>();        
        int SCIndex = 0, CBIndex = 0;
        while((Text.length() > 2) || (SCIndex != -1 && CBIndex != -1)){
            if((((SCIndex = Text.indexOf(';')) < (CBIndex = Text.indexOf('{'))) && SCIndex != -1) || CBIndex == -1){
                if(SCIndex > Text.indexOf('(') && SCIndex < Text.indexOf(')')){
                    SCIndex = Text.indexOf(')');
                }
                Children.add(new Node(RootNode, Text.substring(0, SCIndex + 1)));
                Text = Text.substring(SCIndex+1);
            }else{
                Node lead = new Node(RootNode, Text.substring(0, CBIndex + 1));
                Children.add(lead);
                String follow =  Text.substring(CBIndex + 1, Text.indexOf('}') + 1);
                if(!(follow.equals(null) || lead.equals(null) || follow.length() == 0)){
                    lead.AddChildren(Parse(follow));;
                }
                Text = Text.substring(Text.indexOf('}')+1);
            }
        }
        return Children;
    }
    
    ArrayList<Node> DepthFirstSearch(String s){
        ArrayList<Node> Results = new ArrayList<>();
        for(Node r : RootChildren){
            if(r.getValue().contains(s)){
                Results.add(r);
                Results.addAll(r.DepthFirstSearch(s));
            }
        }
        return Results;
    }
    
    ArrayList<Node> BreadthFirstSearch(String s){
        ArrayList<Node> Results = new ArrayList<>();
        ArrayDeque<Node> NodeQueue = new ArrayDeque<>(RootChildren);
        while(!NodeQueue.isEmpty()){
            Node n = NodeQueue.pollFirst();
            if(n.getValue().contains(s)){
                Results.add(n);
            }
            NodeQueue.addAll(n.getChildren());
        }
        return Results;
    }
    
    @Override
    public String toString(){
        String Text = "";
        for(Node c : RootChildren){
            Text += c.toString();
        }
    return Text;
    }
}
