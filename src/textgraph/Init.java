/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgraph;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author HP
 */

public class Init {
    
    public List<String> vertexList;   //图的顶点集
    public List<Edge> edgeList;   //图的边集
    public List deplist;//依存关系列表

    public List<String> getVertexList() {
        return vertexList;
    }

    public void setVertexList(List<String> vertexList) {
        this.vertexList = vertexList;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }
    
    /**
     * 利用Standfordnlp分析用户评论,得到依存关系
     * @param str 一条用户评论
     */
    public void init(String str){  
        
        List<String> vertexList = new LinkedList<>();
        List<Edge> edgeList = new LinkedList<>();
        //点集
        Properties props = new Properties();
        props.setProperty("ner.useSUTime", "false");
        //分词（tokenize）、分句（ssplit）、词性标注（pos）、词形还原（lemma）、命名实体识别（ner）、语法解析（parse）、情感分析（sentiment）、指代消解（coreference resolution）
    //    props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        props.put("annotators", "tokenize, ssplit,pos, lemma, ner, parse");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation document = new Annotation(str);
        pipeline.annotate(document);
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        deplist = new ArrayList(); 
        for(CoreMap sentence: sentences) {
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                String word = token.get(TextAnnotation.class);
                vertexList.add(word);
            }
            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
            deplist = dependencies.edgeListSorted(); //依存关系list
            System.out.println(dependencies.toString());
            break;//只取第一条评论内容，防止存在省略号覆盖list
        }
        this.setVertexList(vertexList);
        
        //边集
        for(int i =0;i<deplist.size();i++){
            SemanticGraphEdge s = (SemanticGraphEdge) deplist.get(i);
            String dep = s.getDependent().word(); //dependent单词
            String gov = s.getGovernor().word();
            String reln = s.getRelation().toString();  //dependent与governor关系
            
            Edge edge = new Edge();
            edge.from = gov;//from
            edge.to = dep;//to
            edge.weight = reln;//weight
            
            edgeList.add(edge);
        }
        this.setEdgeList(edgeList);
    }
            
    public Graph creatGraph(Graph gm, List vertexList, List edgelist){
        gm.setVertexNum(vertexList.size());
        gm.setEdgeList(edgelist);
        gm.setVertexlist(vertexList);
        
        String weight;         //权
        String startV,endV;   //起始，终止顶点
        
        //点集
        gm.Vertex = new String[gm.VertexNum]; //保存顶点信息
        for(int i=0;i<gm.VertexNum;i++){
            gm.Vertex[i]=(String) vertexList.get(i);//保存到顶点数组中
        }
        
        //边集
        gm.EdgeWeight = new String[gm.VertexNum][gm.VertexNum];
        if (edgelist == null) {
            for (int i = 0; i < vertexList.size(); i++) {
                for (int j = 0; j < vertexList.size(); j++) {
                    gm.EdgeWeight[i][j] = null;
                }
            }
        } else {
            for (int i = 0; i < edgelist.size(); i++) {
                Edge s = (Edge) edgelist.get(i);
                startV = s.from;
                endV = s.to;
                weight = s.weight;

                for (int j = 0; j < gm.VertexNum; j++) //在顶点数组中查找起点位置
                {
                    if (gm.Vertex[j].endsWith(startV)) {
                        for (int k = 0; k < gm.VertexNum; k++) //在顶点数组中查找终点位置
                        {
                            if (gm.Vertex[k].endsWith(endV)) {
                                gm.EdgeWeight[j][k] = weight;
                            }
                        }
                    }
                }
            }
        }
        return gm;
    }
    
    public void showgraph(Graph gm){
        System.out.println("第一列为起始点，第一行为终点");
        for(int i=0;i<gm.VertexNum;i++){
            System.out.printf("\t%s",gm.Vertex[i]); //第一行输出顶点信息
        }
        System.out.println();
        for(int i=0;i<gm.VertexNum;i++){
            System.out.printf("%s",gm.Vertex[i]);
            for(int j=0;j<gm.VertexNum;j++){
                if(gm.EdgeWeight[i][j]==null){
                    System.out.printf("\tZ");
                }else{
                    System.out.printf("\t"+gm.EdgeWeight[i][j]);
                }
            }
            System.out.println();
        }
    }
}