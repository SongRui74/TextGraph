/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgraph;

import java.util.List;
import java.util.Map;

/**
 *
 * @author HP
 */
public class Graph {
    
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    
    public List<String> vertexList;   //图的顶点集
    public Map<String, List<Edge>> ver_edgeList_map;  //图的每个顶点对应的有向边
    
    public Graph(List<String> vertexList, Map<String, List<Edge>> ver_edgeList_map) {
        super();
        this.vertexList = vertexList;
        this.ver_edgeList_map = ver_edgeList_map;
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
 
 
    static class Edge{
        private String startVertex;  //此有向边的起始点
        private String endVertex;  //此有向边的终点
        private int weight;  //此有向边的权值
        
        public Edge(String startVertex, String endVertex, int weight) {
            super();
            this.startVertex = startVertex;
            this.endVertex = endVertex;
            this.weight = weight;
        }
        
        public Edge()
        {}
        
        public String getStartVertex() {
            return startVertex;
        }
        public void setStartVertex(String startVertex) {
            this.startVertex = startVertex;
        }
        public String getEndVertex() {
            return endVertex;
        }
        public void setEndVertex(String endVertex) {
            this.endVertex = endVertex;
        }
        public int getWeight() {
            return weight;
        }
        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
}