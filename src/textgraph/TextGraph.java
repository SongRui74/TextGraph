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
        InitGraph g1 = new InitGraph();
        g1.init();
        Graph gg1 = g1.creategraph();
        g1.showgraph(gg1);
        
        g1.init2();
        Graph gg2 = g1.creategraph();
        g1.showgraph(gg2);
        
        ComSubgraph com = new ComSubgraph();
        com.ComSubgraph(gg1, gg2);
        
    }
    
}
