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

class Vertex{
    String word;
    String pos;
    String lemma;
    String ner;
    Double wight = 0.0;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getNer() {
        return ner;
    }

    public void setNer(String ner) {
        this.ner = ner;
    }

    public Double getWight() {
        return wight;
    }

    public void setWight(Double wight) {
        this.wight = wight;
    }
    
}

class Edge{
    Vertex from;
    Vertex to;
    Double w = 0.0;
    String weight;

    public Vertex getFrom() {
        return from;
    }

    public void setFrom(Vertex from) {
        this.from = from;
    }

    public Vertex getTo() {
        return to;
    }

    public void setTo(Vertex to) {
        this.to = to;
    }

    public Double getW() {
        return w;
    }

    public void setW(Double w) {
        this.w = w;
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
    String[][] EdgeWeight;//保存语义权
    Double[][] EdgeW;//保存权

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

    public Double[][] getEdgeW() {
        return EdgeW;
    }

    public void setEdgeW(Double[][] EdgeW) {
        this.EdgeW = EdgeW;
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

