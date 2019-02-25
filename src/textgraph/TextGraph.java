/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgraph;

/**
 *
 * @author HP
 */
public class TextGraph {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Init ve = new Init();
        ve.init("app doesn't work");
        Graph g1 = new Graph();
        ve.creatGraph(g1,ve.getVertexList(),ve.getEdgeList());
        ve.showgraph(g1);
        
        ve.init("app doesn't open");
        Graph g2 = new Graph();
        ve.creatGraph(g2,ve.getVertexList(),ve.getEdgeList());
        ve.showgraph(g2);
        
        ComSubgraph csg = new ComSubgraph();
        csg.ComSubgraph(g1, g2);
        Graph g = new Graph();
        ve.creatGraph(g,csg.getVertexList(),csg.getEdgeList());
        ve.showgraph(g);
    }
    
}
