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

/**
 *
 * @author HP
 */
public class ComSubgraph{
    
    public List<Vertex> vertexList1;   //图1的顶点集
    public List edgeList1;  //图1的每个顶点对应的有向边
    public List<Vertex> vertexList2;   //图2的顶点集
    public List edgeList2;  //图2的每个顶点对应的有向边
    
    public List<Vertex> vertexList;   //子图的顶点集
    public List edgeList;  //子图的每个顶点对应的有向边

    public List getVertexList1() {
        return vertexList1;
    }

    public void setVertexList1(List vertexList1) {
        this.vertexList1 = vertexList1;
    }

    public List getEdgeList1() {
        return edgeList1;
    }

    public void setEdgeList1(List edgeList1) {
        this.edgeList1 = edgeList1;
    }

    public List getVertexList2() {
        return vertexList2;
    }

    public void setVertexList2(List vertexList2) {
        this.vertexList2 = vertexList2;
    }

    public List getEdgeList2() {
        return edgeList2;
    }

    public void setEdgeList2(List edgeList2) {
        this.edgeList2 = edgeList2;
    }

    public List getVertexList() {
        return vertexList;
    }

    public void setVertexList(List vertexList) {
        this.vertexList = vertexList;
    }

    public List getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List edgeList) {
        this.edgeList = edgeList;
    }
    
    public void ComSubgraph(Graph g1,Graph g2){
        
        this.setVertexList1(g1.getVertexlist());
        this.setVertexList2(g2.getVertexlist());
        this.setEdgeList1(g1.getEdgeList());
        this.setEdgeList2(g2.getEdgeList());
        
//        this.com_ver();
//        this.com_edge();
        this.com();
        
    }    
    
    public void com(){
        List<Vertex> vertexList = new LinkedList<>();
        List<Edge> edgeList = new LinkedList<>();
        
        for (int i = 0; i < edgeList1.size(); i++) {
            Edge e1 = (Edge) edgeList1.get(i);
            String dep = e1.getWeight();
            for (int j = 0; j < edgeList2.size(); j++) {
                Edge e2 = (Edge) edgeList2.get(j);
                if (e2.getWeight().equals(dep)) {
                    //边关系相同，判断节点相似等级
                    int levelfrom = this.level(e1.from, e2.from);
                    int levelto = this.level(e1.to, e2.to);
                    //如果该边存在于子图
                    if (levelfrom != 0 && levelto != 0) {
                        Edge edge = new Edge();      
                        Vertex v = new Vertex();
                        //加入节点集
                        v = e1.getFrom();                      
                        if (levelfrom == 1) {                            
                            vertexList.add(v);
                        } else if (levelfrom == 2) {
                            v.word = v.word+","+e2.getFrom().word+"/"+v.getPos();
                            vertexList.add(v);
                        }
                        edge.setFrom(v);
                        //加入节点集             
                        v = e1.getTo();
                        if (levelto == 1) {
                            vertexList.add(v);
                        } else if (levelto == 2) {
                            v.word = v.word+","+e2.getTo().word+"/"+v.getPos();
                            vertexList.add(v);
                        }
                        edge.setTo(v);                        
                        //加入边集
                        edge.setWeight(dep);
                        edgeList.add(edge);
                    }
                }
            }
        }
        this.setVertexList(vertexList);
        this.setEdgeList(edgeList);
    }
    
    public void com_ver() {
        List<Vertex> vertexList = new LinkedList<>();
        for (Vertex v1 : vertexList1) {
            for (Vertex v2 : vertexList2) {
                int level = this.level(v1, v2);
                if (level == 1) {
                    vertexList.add(v1);
                }
            }
        }
        this.setVertexList(vertexList);
    }
    
    public void com_edge() {
        if (vertexList.isEmpty()) {
            System.out.println("There is no common subgraph!");
        } else {
            List<Edge> edgeList = new LinkedList<>();
            Edge newedge = new Edge();
            for (int k = 0;k < vertexList.size(); k++) { //从公共点集开始遍历
                Vertex v = (Vertex) vertexList.get(k);
                String start = v.word;
                for (int i = 0; i < edgeList1.size(); i++) { //找到图1中的起始点
                    Edge e1 = (Edge) edgeList1.get(i);
                    if (e1.from.word.endsWith(start)) {
                        for (int j = 0; j < edgeList2.size(); j++) { //找到图2中的起始点
                            Edge e2 = (Edge) edgeList2.get(j);
                            if (e2.from.word.endsWith(start)) {
                                if (e1.to.word.endsWith(e2.to.word)) {  //两个图的终止点相同
                                    newedge.from.word = start; //将边加入子图中
                                    newedge.to = e1.to;
                                    newedge.weight = e1.weight;
                                    edgeList.add(newedge);
                                }
                            }
                        }
                    }
                }
            }
        }
        this.setEdgeList(edgeList);
    }
    
    public int level(Vertex v1,Vertex v2){
        int level = 0;
        if(v1.getWord().equals(v2.getWord()))
            return 1;
        else if(v1.getPos().startsWith("NN") && v2.getPos().startsWith("NN"))
            return 2;
        else if(v1.getPos().startsWith("VB") && v2.getPos().startsWith("VB"))
            return 2;
        else if(v1.getPos().equals(v2.getPos()))
            return 2;
        return level;
    }
}
