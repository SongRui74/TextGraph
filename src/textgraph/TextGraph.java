/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgraph;

import java.io.IOException;

/**
 *
 * @author HP
 */
public class TextGraph {

    private String table_name ;

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }
    
    /**
     * 对给定表的评论数据聚类
     */
    public void KMeans() throws IOException{
        Kmeans km = new Kmeans();
        km.setTable_name(table_name);
        km.ImportData();
        km.Readtxt();
        km.ChooseCenter();
        km.Iteration();
        km.ResultOut();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here

//        TextGraph k = new TextGraph();
//        k.setTable_name("graph_BUG");
//        
//        //记录相似度矩阵
//        SimMatrix s = new SimMatrix();
//        s.setTable_name(k.getTable_name());
//        s.ImportData();
//        s.calMatrix();
//        s.Writetxt();
//    //    s.Writedatatxt();
//        System.out.println("图结构平均相似度："+s.aversim());
//        
//        s.calVSMMatrix();
//        s.Writetxt();
//    //    s.Writedatatxt();
//        System.out.println("VSM平均相似度："+s.aversim());
//        //聚类
//        k.KMeans();
        
        String a = "please fix it";
        String b = "fix it";
//        String a = "Hope we can use the English version";
//        String b = "It needs an iPad version";
//        String a = "Hope we can use the English version";
//        String b = " I wish all apps worked this well";
        Init ve = new Init();
        ve.wordweight();
        ve.init(a);
        Graph g1 = new Graph();
        ve.creatGraph(g1,ve.getVertexList(),ve.getEdgeList());
        ve.showgraph(g1);
        
        ve.init(b);
        Graph g2 = new Graph();
        ve.creatGraph(g2,ve.getVertexList(),ve.getEdgeList());
        ve.showgraph(g2);
        
        ComSubgraph csg = new ComSubgraph();
        csg.ComSubgraph2(g1, g2);
        Graph g = new Graph();
        ve.creatGraph(g,csg.getVertexList(),csg.getEdgeList());
        ve.showgraph(g);
        System.out.println("图结构相似度："+ve.sim(g, g1, g2));
       
        //VSM
        VSM sim = new VSM ();
        System.out.println("VSM相似度："+sim.VSM(a, b));
    }
    
}
