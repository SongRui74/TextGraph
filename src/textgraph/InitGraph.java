//
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgraph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
 
import textgraph.Graph.Edge;
/**
 *
 * @author HP
 */
public class InitGraph {
    private List<String> vertexList;   //图的顶点集
    private Map<String, List<Edge>> ver_edgeList_map;  //图的每个顶点对应的有向边

    public List<String> getVertexList() {
        return vertexList;
    }

    public void setVertexList(List<String> vertexList) {
        this.vertexList = vertexList;
    }

    public Map<String, List<Edge>> getVer_edgeList_map() {
        return ver_edgeList_map;
    }

    public void setVer_edgeList_map(Map<String, List<Edge>> ver_edgeList_map) {
        this.ver_edgeList_map = ver_edgeList_map;
    }
    
    //设置点集和边集
    public void init(){
       
        List<String> verList = new LinkedList<>();
        verList.add("v1");
        verList.add("v2");
        verList.add("v3");
        verList.add("v4");
        verList.add("v5");
        verList.add("v6");
        verList.add("v7");
        verList.add("v8");
    
        Map<String, List<Edge>> vertex_edgeList_map = new HashMap<>();
        
        List<Edge> v1List = new LinkedList<>();
        v1List.add(new Edge("v1","v2",6));
        v1List.add(new Edge("v1","v4",1));
        
        List<Edge> v2List = new LinkedList<>();
        v2List.add(new Edge("v2","v3",43));
        v2List.add(new Edge("v2","v4",11));
        v2List.add(new Edge("v2","v5",6));
        
        List<Edge> v3List = new LinkedList<>();
        v3List.add(new Edge("v3","v8",8));
        
        List<Edge> v4List = new LinkedList<>();
        v4List.add(new Edge("v4","v3",15));
        v4List.add(new Edge("v4","v5",12));
        
        List<Edge> v5List = new LinkedList<>();
        v5List.add(new Edge("v5","v3",38));
        v5List.add(new Edge("v5","v8",13));
        v5List.add(new Edge("v5","v7",24));
        
        List<Edge> v6List = new LinkedList<>();
        v6List.add(new Edge("v6","v5",1));
        v6List.add(new Edge("v6","v7",12));
        
        List<Edge> v7List = new LinkedList<>();
        v7List.add(new Edge("v7","v8",20));
        
        vertex_edgeList_map.put("v1", v1List);
        vertex_edgeList_map.put("v2", v2List);
        vertex_edgeList_map.put("v3", v3List);
        vertex_edgeList_map.put("v4", v4List);
        vertex_edgeList_map.put("v5", v5List);
        vertex_edgeList_map.put("v6", v6List);
        vertex_edgeList_map.put("v7", v7List);
        
        this.setVertexList(verList);
        this.setVer_edgeList_map(vertex_edgeList_map);
    }
    
    public Graph creategraph(){
        Graph g = new Graph(vertexList, ver_edgeList_map);
        return g;
    }
    
    public void showgraph(Graph g) {
        vertexList = g.getVertexList();
        ver_edgeList_map = g.getVer_edgeList_map();
        //输出原图
    //    String s = vertexList.size() + " 个顶点, " + ver_edgeList_map.size() + " 条边\n";
        String s = "";
        List<Edge> list = new LinkedList<>();
        for (int i = 0; i < vertexList.size(); i++) {
            s += vertexList.get(i) + ": ";
            if (ver_edgeList_map.containsKey(vertexList.get(i))) {
                list = ver_edgeList_map.get(vertexList.get(i));
                for (Edge e : list) {
                    s += " [" + e.getEndVertex() + "," + e.getWeight() + "] ,";
                }
                s += "\n";
            }
        }	
        System.out.println(s);
    }
    
    //设置点集和边集
    public void init2(){
        List<String> verList = new LinkedList<>();
        verList.add("v1");
        verList.add("v2");
        verList.add("v3");
        verList.add("v4");
        verList.add("v5");
        verList.add("v6");
    
        Map<String, List<Edge>> vertex_edgeList_map = new HashMap<>();
        
        List<Edge> v1List = new LinkedList<>();
        v1List.add(new Edge("v1","v2",6));
        v1List.add(new Edge("v1","v4",1));
        
        List<Edge> v2List = new LinkedList<>();
        v2List.add(new Edge("v2","v3",43));
        v2List.add(new Edge("v2","v4",11));
        v2List.add(new Edge("v2","v5",6));
                        
        List<Edge> v4List = new LinkedList<>();
        v4List.add(new Edge("v4","v3",15));
        v4List.add(new Edge("v4","v5",12));
        
        List<Edge> v5List = new LinkedList<>();
        v5List.add(new Edge("v5","v3",38));
        
        List<Edge> v6List = new LinkedList<>();
        v6List.add(new Edge("v6","v5",1));
        
        
        vertex_edgeList_map.put("v1", v1List);
        vertex_edgeList_map.put("v2", v2List);
        vertex_edgeList_map.put("v4", v4List);
        vertex_edgeList_map.put("v5", v5List);
        vertex_edgeList_map.put("v6", v6List);
        
        this.setVertexList(verList);
        this.setVer_edgeList_map(vertex_edgeList_map);
    }
}