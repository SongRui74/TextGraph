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
public class InitVerandEdge {
    
    public List<String> vertexList;   //图的顶点集
    public Map<String, List<Graph.Edge>> ver_edgeList_map;  //图的每个顶点对应的有向边
    
    public List deplist;//依存关系列表
    /**
     * 利用Standfordnlp分析用户评论,得到依存关系
     * @param str 一条用户评论
     */
    public void init_ver(String str){  
        
        List<String> vertexList = new LinkedList<>();
        
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
            break;//只取第一条评论内容，防止存在省略号覆盖list
        }
    }
    
    public void init_dege(){
        Map<String, List<Graph.Edge>> ver_edgeList_map = new HashMap<>();
        
    }
}

