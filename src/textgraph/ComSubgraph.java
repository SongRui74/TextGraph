/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgraph;

import com.ansj.vec.Word2VEC;
import java.io.IOException;
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
    
    Word2VEC w2v = new Word2VEC();

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
    
//    public void ComSubgraph(Graph g1,Graph g2){
//        
//        this.setVertexList1(g1.getVertexlist());
//        this.setVertexList2(g2.getVertexlist());
//        this.setEdgeList1(g1.getEdgeList());
//        this.setEdgeList2(g2.getEdgeList());
//        
////        this.com_ver();
////        this.com_edge();
//        this.com();
//        
//    }   
    public void ComSubgraph2(Graph g1,Graph g2) throws IOException{
        
        w2v.loadJavaModel(".\\test10000_model");
        
        this.setVertexList1(g1.getVertexlist());
        this.setVertexList2(g2.getVertexlist());
        this.setEdgeList1(g1.getEdgeList());
        this.setEdgeList2(g2.getEdgeList());
        
        int a = vertexList1.size();
        int b = vertexList2.size();
        
        //若存在一个图只有一个节点
        if(a == 1 || b == 1){
            if(a == 1 && b != 1){
                Vertex v1 = new Vertex();
                v1 = vertexList1.get(0);
                for(int i = 0;i < b; i++){
                    Vertex v2 = new Vertex();
                    v2 = vertexList2.get(i);
                    if(v1.getLemma().equals(v2.getLemma())){
                        this.setVertexList(vertexList1);
                        this.setEdgeList(edgeList1);
                    }else{
                        List<Vertex> vertexList = new LinkedList<>();
                        List<Edge> edgeList = new LinkedList<>();
                        this.setVertexList(vertexList);
                        this.setEdgeList(edgeList);
                    }
                }
            }else if(a != 1 && b == 1){
                Vertex v2 = new Vertex();
                v2 = vertexList2.get(0);
                for(int i = 0;i < a; i++){
                    Vertex v1 = new Vertex();
                    v1 = vertexList1.get(i);
                    if(v1.getLemma().equals(v2.getLemma())){
                        this.setVertexList(vertexList2);
                        this.setEdgeList(edgeList2);
                    }else{
                        List<Vertex> vertexList = new LinkedList<>();
                        List<Edge> edgeList = new LinkedList<>();
                        this.setVertexList(vertexList);
                        this.setEdgeList(edgeList);
                    }
                }
            }else if (a == 1 && b == 1) {
                Vertex v1 = new Vertex();
                Vertex v2 = new Vertex();
                v1 = vertexList1.get(0);
                v2 = vertexList2.get(0);
                if (v1.getLemma().equals(v2.getLemma())) {
                    this.setVertexList(vertexList2);
                    this.setEdgeList(edgeList2);
                } else {
                    List<Vertex> vertexList = new LinkedList<>();
                    List<Edge> edgeList = new LinkedList<>();
                    this.setVertexList(vertexList);
                    this.setEdgeList(edgeList);
                }
            }
        }
        else
            this.com_semantic();
        
    }
    
    public void com_semantic(){
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
                //    System.out.println(i+","+j+":"+levelfrom+" "+levelto);
                    double sim1 = w2v.similarity(e1.from.getWord(), e1.to.getWord());
                    double sim2 = w2v.similarity(e2.from.getWord(), e2.to.getWord());
                    //如果该边存在于子图
                    if (levelfrom != 0 && levelto != 0) {
                        Edge edge = new Edge();  
                        Vertex v1 = new Vertex();
                        Vertex v2 = new Vertex();
                        Double d1 = 0.0;//语义权
                        Double d2 = 0.0;//语义权
                        //加入节点集
                        v1 = e1.getFrom();                      
                        switch (levelfrom) {
                            case 1://两词完全相同
                                d1 = 0.001;
                                vertexList.add(v1);
                                break;
                            case 2://两词词根完全相同
                                d1 = 0.0008;
                                v1.word = v1.word+","+e2.getFrom().word+"/"+v1.getLemma();
                                vertexList.add(v1);
                                break;
                            case 3://两词的词性相同，且两个词具有一定相似性
                                d1 = 0.0006;
                                v1.word = v1.word+","+e2.getFrom().word+"/"+v1.getPos();
                                vertexList.add(v1);
                                break;
                            case 4://两词的词性相同，且两个词相似性低  
                                d1 = 0.0001;
                                v1.word = v1.word+","+e2.getFrom().word+"/"+v1.getPos();
                                vertexList.add(v1);
                                break;
                            default:
                                break;
                        }
                        edge.setFrom(v1);
                        //加入节点集             
                        v2 = e1.getTo();
                        switch (levelto) {
                            case 1://两词完全相同
                                d2 = 0.001;
                                vertexList.add(v2);
                                break;
                            case 2://两词词根完全相同
                                d2 = 0.0008;
                                v2.word = v2.word+","+e2.getTo().word+"/"+v2.getLemma();
                                vertexList.add(v2);
                                break;
                            case 3://两词的词性相同，且两个词具有一定相似性
                                d2 = 0.0006;
                                v2.word = v2.word+","+e2.getTo().word+"/"+v2.getPos();
                                vertexList.add(v2);
                                break;
                            case 4://两词的词性相同，且两个词相似性低  
                                d2 = 0.0001;
                                v2.word = v2.word+","+e2.getTo().word+"/"+v2.getPos();
                                vertexList.add(v2);
                                break;
                            default:
                                break;
                        }
                        edge.setTo(v2); 
                        //加入边集
                        edge.setWeight(dep);
                    //    double d = 0.5*(d1+d2); //语义权
                        double d = 0.5*(sim1+sim2)+0.5*(d1+d2);
                        edge.setW(d+0.5*(0.5*(e1.getFrom().getWight()+e2.getFrom().getWight())+0.5*(e1.getTo().getWight()+e2.getTo().getWight()))); 
                        e1.setW(e1.getW()+d); //同时改变原图的语义值
                        e2.setW(e2.getW()+d);
                    
                        //匹配等级很弱，惩罚
                        if(levelfrom == 4 && levelto == 4)
                            edge.setW(0.01 * edge.getW());
                        
                        edgeList.add(edge);
                    }
                }
            }
        }
        this.setVertexList(vertexList);
        this.setEdgeList(edgeList);
    }
        
    //判断两个词的相似等级
    public int level(Vertex v1,Vertex v2){
        
        double sim = w2v.similarity(v1.getWord(), v2.getWord());
        double standard = 0.0;
        
        int level = 0;
        //两词完全相同
        if(v1.getWord().equals(v2.getWord()))
            return 1;
        
        //两词词根完全相同
        else if(v1.getLemma().equals(v2.getLemma()))
            return 2;
                
        //两词的词性相同，且两个词具有一定相似性
        else if(v1.getPos().equals(v2.getPos()) && sim > standard) 
            return 3;        
        else if(v1.getPos().startsWith("NN") && v2.getPos().startsWith("NN") && sim > standard)
            return 3;
        else if(v1.getPos().startsWith("VB") && v2.getPos().startsWith("VB") && sim > standard)
            return 3;
        
        //两词的词性相同，两个词没有相似性
        else if(v1.getPos().equals(v2.getPos()) && sim <= standard) 
            return 4;        
        else if(v1.getPos().startsWith("NN") && v2.getPos().startsWith("NN") && sim <= standard)
            return 4;
        else if(v1.getPos().startsWith("VB") && v2.getPos().startsWith("VB") && sim <= standard)
            return 4;
                      
        return level;
    }
    
//    public void com(){
//        List<Vertex> vertexList = new LinkedList<>();
//        List<Edge> edgeList = new LinkedList<>();
//        
//        for (int i = 0; i < edgeList1.size(); i++) {
//            Edge e1 = (Edge) edgeList1.get(i);
//            String dep = e1.getWeight();
//            for (int j = 0; j < edgeList2.size(); j++) {
//                Edge e2 = (Edge) edgeList2.get(j);
//                if (e2.getWeight().equals(dep)) {
//                    //边关系相同，判断节点相似等级
//                    int levelfrom = this.level(e1.from, e2.from);
//                    int levelto = this.level(e1.to, e2.to);
//                    //如果该边存在于子图
//                    if (levelfrom != 0 && levelto != 0) {
//                        Edge edge = new Edge();  
//                        Vertex v1 = new Vertex();
//                        Vertex v2 = new Vertex();
//                        //加入节点集
//                        v1 = e1.getFrom();                      
//                        if (levelfrom == 1) {  
//                            vertexList.add(v1);
//                        } else if (levelfrom == 2) {
//                            v1.word = v1.word+","+e2.getFrom().word+"/"+v1.getPos();
//                            vertexList.add(v1);
//                        } else if (levelfrom == 3) {
//                            v1.word = v1.word+","+e2.getFrom().word+"/"+v1.getLemma();
//                            vertexList.add(v1);
//                        }
//                        edge.setFrom(v1);
//                        //加入节点集             
//                        v2 = e1.getTo();
//                        if (levelto == 1) {
//                            vertexList.add(v2);
//                        } else if (levelto == 2) {
//                            v2.word = v2.word+","+e2.getTo().word+"/"+v2.getPos();
//                            vertexList.add(v2);
//                        } else if (levelto == 3) {
//                            v2.word = v2.word+","+e2.getTo().word+"/"+v2.getLemma();
//                            vertexList.add(v2);
//                        }
//                        edge.setTo(v2); 
//                        //加入边集
//                        edge.setWeight(dep);
//                        edge.setW(0.5*(v1.getWight()+v2.getWight()));
////                        if (v1.getWight() == 0.0 || v2.getWight() == 0.0) {
////                            edge.setW(0.0);
////                        } else {
////                            edge.setW(0.5*(v1.getWight()+v2.getWight()));
////                        //    edge.setW(v1.getWight()*v2.getWight()/Math.sqrt(v1.getWight()*v1.getWight()*v2.getWight()*v2.getWight()));
////                        }
//                        edgeList.add(edge);
//                    }
//                }
//            }
//        }
//        this.setVertexList(vertexList);
//        this.setEdgeList(edgeList);
//    }
    
//    public void com_ver() {
//        List<Vertex> vertexList = new LinkedList<>();
//        for (Vertex v1 : vertexList1) {
//            for (Vertex v2 : vertexList2) {
//                int level = this.level(v1, v2);
//                if (level == 1) {
//                    vertexList.add(v1);
//                }
//            }
//        }
//        this.setVertexList(vertexList);
//    }
//    
//    public void com_edge() {
//        if (vertexList.isEmpty()) {
//            System.out.println("There is no common subgraph!");
//        } else {
//            List<Edge> edgeList = new LinkedList<>();
//            Edge newedge = new Edge();
//            for (int k = 0;k < vertexList.size(); k++) { //从公共点集开始遍历
//                Vertex v = (Vertex) vertexList.get(k);
//                String start = v.word;
//                for (int i = 0; i < edgeList1.size(); i++) { //找到图1中的起始点
//                    Edge e1 = (Edge) edgeList1.get(i);
//                    if (e1.from.word.endsWith(start)) {
//                        for (int j = 0; j < edgeList2.size(); j++) { //找到图2中的起始点
//                            Edge e2 = (Edge) edgeList2.get(j);
//                            if (e2.from.word.endsWith(start)) {
//                                if (e1.to.word.endsWith(e2.to.word)) {  //两个图的终止点相同
//                                    newedge.from.word = start; //将边加入子图中
//                                    newedge.to = e1.to;
//                                    newedge.weight = e1.weight;
//                                    edgeList.add(newedge);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        this.setEdgeList(edgeList);
//    }
    
    
}
