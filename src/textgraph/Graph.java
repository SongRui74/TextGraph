/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgraph;

import java.util.List;

/**
 *
 * @author HP
 */
class Edge{
    String from;
    String to;
    String weight;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
    
}
    
public class Graph {
 int VertexNum;//顶点数量
    int EdgeNum;   //边的数量
    List Vertexlist;
    List EdgeList;
    String[] Vertex; //保存顶点信息
    String[][] EdgeWeight;//保存权

    public int getVertexNum() {
        return VertexNum;
    }

    public void setVertexNum(int VertexNum) {
        this.VertexNum = VertexNum;
    }

    public int getEdgeNum() {
        return EdgeNum;
    }

    public void setEdgeNum(int EdgeNum) {
        this.EdgeNum = EdgeNum;
    }

    public String[] getVertex() {
        return Vertex;
    }

    public void setVertex(String[] Vertex) {
        this.Vertex = Vertex;
    }

    public String[][] getEdgeWeight() {
        return EdgeWeight;
    }

    public void setEdgeWeight(String[][] EdgeWeight) {
        this.EdgeWeight = EdgeWeight;
    }

    public List getVertexlist() {
        return Vertexlist;
    }

    public void setVertexlist(List Vertexlist) {
        this.Vertexlist = Vertexlist;
    }

    public List getEdgeList() {
        return EdgeList;
    }

    public void setEdgeList(List EdgeList) {
        this.EdgeList = EdgeList;
    }
    
}

