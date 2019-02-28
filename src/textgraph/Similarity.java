/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author HP
 */
//词向量相似度
public class Similarity {
    
    public Double Similarity(String a, String b) throws IOException{
        Double sim = 0.0;
        this.wordweight();
        
        List l = this.Duplicateremoval(a, b);
        List l1 = this.word2vec(a, l);
        List l2 = this.word2vec(b, l);
        sim = this.wordsim(l1, l2);
        
        return sim;
    }
    
    //VSM(词)
    //读入权值表    
    Map<String,Double> wordmap = new HashMap();
    public void wordweight () throws FileNotFoundException, IOException{
        File readfile = new File(".\\wordweight.txt");
        if (!readfile.exists() || readfile.isDirectory()) {
            throw new FileNotFoundException();
        }
        BufferedReader br = new BufferedReader(new FileReader(readfile));
        String temp = null;
        temp = br.readLine();//按行读入  
       // Map<String,Double> map = new HashMap();
        while (temp != null) {
            String[] t = temp.split(" ");
            String word = t[0];
            double weight = Double.parseDouble(t[1]);
            wordmap.put(word, weight);
            temp = br.readLine();
        }
    }
    
    //两个句子去重
    public List Duplicateremoval(String s1,String s2){
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        List<String> l =new ArrayList();
        List l1 = new ArrayList();
        List l2 = new ArrayList();
        String[] temp1 = s1.split(" |,");
        for(int i = 0;i<temp1.length;i++){
            l1.add(temp1[i]);
        }
        String[] temp2 = s2.split(" |,");
        for(int i = 0;i<temp2.length;i++){
            l1.add(temp2[i]);
        }
        
        //合并为一个
        l.addAll(l1);
        l.addAll(l2); 
        //去重
        Set set = new HashSet();
        List<String> newList = new ArrayList<>();
        for (String element : l) {
            //set能添加进去就代表不是重复的元素
            if (set.add(element)) {
                newList.add(element);
            }
        }
        l.clear();
        l.addAll(newList);
                
        l1.clear();
        l2.clear();
        newList.clear();
        return l;
    }
    
    //一个句子转化为向量
    public List word2vec(String str,List l){
        List<Double> list = new ArrayList();
        str = str.toLowerCase();
        String[] temp = str.split(" |,");
        
        for(int i = 0;i<l.size();i++){
            double value = 0.0;
            String word = (String) l.get(i);
            for(int j = 0; j< temp.length;j++){
                if(word.equals(temp[j]) && wordmap.containsKey(word)){
                    value = wordmap.get(word);
                }
            }   
            list.add(value);
        }
//        for(int i=0;i<list.size();i++){
//            System.out.println(list.get(i));
//        }
        return list;
    }
    
    //向量计算结果
    public double wordsim(List l1,List l2){
        double sim = 0;
        double a = 0;//分子
        double b1 = 0;//分母
        double b2 = 0;
        
        for(int i =0;i<l1.size();i++){
            double w1 = (double) l1.get(i);
            double w2 = (double) l2.get(i);
            a += w1*w2;
            b1 += w1*w1;
            b2 += w2*w2;
//            System.out.println(i+"个");
//            System.out.println(w1*w2);
//            System.out.println(w1*w1);
//            System.out.println(w2*w2);
        }
        if(Math.sqrt(b1*b2) == 0){
            sim = 0;
        }else{
            sim = a/Math.sqrt(b1*b2);
        }
        return sim;
    }

}
