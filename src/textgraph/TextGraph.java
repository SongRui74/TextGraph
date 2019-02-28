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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        String a = "Hope we can use the English version";
        String b = "It needs an iPad version";
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
        csg.ComSubgraph(g1, g2);
        Graph g = new Graph();
        ve.creatGraph(g,csg.getVertexList(),csg.getEdgeList());
        ve.showgraph(g);
        System.out.println("图结构相似度（仅词汇）："+ve.sim(g, g1, g2));
        
        csg.ComSubgraph2(g1, g2);
        Graph gg = new Graph();
        ve.creatGraph(gg,csg.getVertexList(),csg.getEdgeList());
        ve.showgraph(gg);
        System.out.println("图结构相似度（含语义）："+ve.sim(gg, g1, g2));
        
        //词
        Similarity sim = new Similarity();
        System.out.println("词向量相似度："+sim.Similarity(a, b));
    }
    
}
