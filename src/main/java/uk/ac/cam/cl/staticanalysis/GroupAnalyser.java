/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.staticanalysis;

import static java.lang.Math.abs;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author icerus
 */
public class GroupAnalyser {
    final static int groupDist = 2;
    
    protected static void GenerateSyncMechs(Tree t){
        ArrayList<ArrayList<Node>> groups = group(t);
        HashMap<ArrayList<Node>, String> analysis = new HashMap<>();
        
        for(ArrayList<Node> g : groups){
            analysis.put(g, analyse(g));
        }
        
        for(ArrayList<Node> g : groups){
            HashSet<String> variableNames = new HashSet<>();
            ArrayList<Node> instanciationNodes = new ArrayList<>();
            String a = analysis.get(g);            
            for(Node n : g){
                String val = n.getValue();
                
                int ri = val.indexOf("SharedRead");
                int wi = val.indexOf("SharedWrite");
                int rbi = val.indexOf('(', ri);
                int wbi = val.indexOf('(', wi);
                int wci = val.indexOf(',',wbi);
                variableNames.add(val.substring(rbi+1,val.indexOf(')',ri)).strip());
                variableNames.add(val.substring(wbi+1, wci).strip());
                
                
                val = val.replaceAll("SharedRead", "Synchroniser.Read");
                val = val.replaceAll("SharedWrite", "Synchroniser.Write");
                n.setValue(val);
            }
            Node LeaderNode = g.get(0);
            List<Node> siblings = LeaderNode.getParent().getChildren();
            int i = siblings.indexOf(LeaderNode);
            for(String variable : variableNames){
                instanciationNodes.add(new Node(LeaderNode.getParent(),
                    "Synchroniser.Initialise("+variable+", " + a + ")"));
            }
            ArrayList<Node> InsertedInstances = new ArrayList<>(siblings.subList(0, i));
            InsertedInstances.addAll(instanciationNodes);
            InsertedInstances.addAll(siblings.subList(i,siblings.size()));
            LeaderNode.getParent().setChildren(InsertedInstances);
        }
        
        HashSet<String> imports = new HashSet<>(analysis.values());
        ArrayList<Node> ImportNodes = new ArrayList<>();
        ImportNodes.add(new Node(t.RootNode, "import uk.ac.cam.cl.Synchroniser"));
        for(String SyncMech : imports){
            ImportNodes.add(new Node(t.RootNode, "import uk.ac.cam.cl." + SyncMech));
        }
        
    }
    
    protected static boolean CanReach(Node origin, Node end, int DistanceLimit){
        if (DistanceLimit == 0){
            return false;
        }
        
        ArrayList<Node> ImmediateFamily = new ArrayList<>();
        if(origin.equals(end)){
            return true;
        }
        
        Node parent = origin.getParent();
        if(parent.equals(end)){
            return true;
        }else{
            ImmediateFamily.add(parent);
        }
        
        List<Node> siblings = origin.getParent().getChildren();
        if(siblings.contains(end) && (abs(siblings.indexOf(origin) - siblings.indexOf(end))) <= DistanceLimit){
            return true;
        }else{
            ImmediateFamily.addAll(siblings);
        }
        
        List<Node> children = origin.getChildren();
        if (children.contains(end)){
            return true;
        }else{
            ImmediateFamily.addAll(children);
        }
        
        for(Node f : ImmediateFamily){
            if (CanReach(f,end,DistanceLimit - 1)){
                return true;
            }
        }
        
        return false;
    }
    
    protected static String analyse(ArrayList<Node> group){
        int NumWrites = 0, NumReads = 0;
        for(Node n : group){
            if(n.getValue().contains("SharedRead(")){
                NumReads += 1;
            }else{
                NumWrites += 1;
            }
        }
        if(NumWrites == 0){
            return "SyncTest";
        }else if(group.size() == 1){
            return "SyncMechAtomic";
        }else if(NumWrites * 4 < NumReads){
            return "SyncMechSemaphore";
        }else{
            return "SyncMechLock";
        }
        
    }
    
    protected static ArrayList<ArrayList<Node>> group(Tree t){
        ArrayList<ArrayList<Node>> SharedMemGroups = new ArrayList<>();
        ArrayDeque<Node> UngroupedNodes = new ArrayDeque<>();
        UngroupedNodes.addAll(t.DepthFirstSearch("SharedRead("));
        UngroupedNodes.addAll(t.DepthFirstSearch("SharedWrite("));
        
        while(!UngroupedNodes.isEmpty()){
            Node n = UngroupedNodes.pollFirst();
            boolean HasBeenAdded = false;
            for(ArrayList<Node> group : SharedMemGroups){
                for(Node g : group){
                    if(CanReach(n,g,groupDist)){
                        group.add(n);
                        HasBeenAdded = true;
                        break;
                    }
                }
                if(HasBeenAdded){break;}
            }
            if(!HasBeenAdded){
                ArrayList<Node> newGroup = new ArrayList<>();
                SharedMemGroups.add(newGroup);
            }
        }
        return SharedMemGroups;
    }
}
