/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import textgraph.Graph.Edge;

/**
 *
 * @author HP
 */
public class ComSubgraph{
    public List<String> vertexList1;   //图1的顶点集
    public Map<String, List<Graph.Edge>> ver_edgeList_map1;  //图1的每个顶点对应的有向边
    public List<String> vertexList2;   //图2的顶点集
    public Map<String, List<Graph.Edge>> ver_edgeList_map2;  //图2的每个顶点对应的有向边
    
    public List<String> vertexList;   //子图的顶点集
    public Map<String, List<Graph.Edge>> ver_edgeList_map;  //子图的每个顶点对应的有向边

    public List<String> getVertexList1() {
        return vertexList1;
    }

    public void setVertexList1(List<String> vertexList1) {
        this.vertexList1 = vertexList1;
    }

    public Map<String, List<Edge>> getVer_edgeList_map1() {
        return ver_edgeList_map1;
    }

    public void setVer_edgeList_map1(Map<String, List<Edge>> ver_edgeList_map1) {
        this.ver_edgeList_map1 = ver_edgeList_map1;
    }

    public List<String> getVertexList2() {
        return vertexList2;
    }

    public void setVertexList2(List<String> vertexList2) {
        this.vertexList2 = vertexList2;
    }

    public Map<String, List<Edge>> getVer_edgeList_map2() {
        return ver_edgeList_map2;
    }

    public void setVer_edgeList_map2(Map<String, List<Edge>> ver_edgeList_map2) {
        this.ver_edgeList_map2 = ver_edgeList_map2;
    }

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
    
    public void ComSubgraph(Graph g1,Graph g2){
        this.setVertexList1(g1.getVertexList());
        this.setVertexList2(g2.getVertexList());
        this.setVer_edgeList_map1(g1.getVer_edgeList_map());
        this.setVer_edgeList_map2(g2.getVer_edgeList_map());
        
        this.com_ver();
        this.com_edge();
        
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
    
    
    public void com_ver() {
        List<String> vertexList = new LinkedList<>();
        for (String v1 : vertexList1) {
            for (String v2 : vertexList2) {
                if (v1.equals(v2)) {
                    vertexList.add(v1);
                }
            }
        }
        this.setVertexList(vertexList);
    }
    
    public void com_edge() {
        if(vertexList.isEmpty()){
            System.out.println("There is no common subgraph!");
        }else {
            Map<String, List<Edge>> ver_edgeList_map = new HashMap<>();
            for (String v : vertexList) {
                List<Edge> e1 = ver_edgeList_map1.get(v);
                List<Edge> e2 = ver_edgeList_map2.get(v);
                if (e1 != null && e2 != null) {
                    //存放图1中当前节点所指向的节点集合
                    List v_end = new ArrayList<>();
                    for (Edge e : e1) {
                        v_end.add(e.getEndVertex());
                    }
                    //判断是否在图二中有相同的边
                    List elist = new ArrayList<>();
                    for (Edge e : e2) {
                        if (v_end.contains(e.getEndVertex())) {
                            elist.add(e);
                        }
                    }

                    ver_edgeList_map.put(v, elist);
                }
            }
            this.setVer_edgeList_map(ver_edgeList_map);
        }
    }
    
}
