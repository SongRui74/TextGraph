/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgraph;

import edu.stanford.nlp.trees.Tree;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author HP
 */
public class SimMatrix {
    private final String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; //加载JDBC驱动
    private final String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=mypro"; //连接服务器和数据库mypro
    private final String userName = "sa"; 
    private final String userPwd = "123456"; 
    private Connection conn;  
    private String table_name;

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }
    
    SQL s = new SQL();
    Init ve = new Init();
    
    private final Map<String,List> Vmap = new HashMap<>(); //存储评论对应的词
    private final Map<String,List> Emap = new HashMap<>(); //存储评论对应的边
    private final Map<Integer,String> indexmap = new HashMap<>(); //存储评论对应的序号
    
    public double[][] Matrix;//相似度存放数组
    /**
     * 从某表中导入数据
     */
    public void ImportData() throws IOException{
        try {           
            ve.wordweight();
            
            Class.forName(driverName);
            conn = DriverManager.getConnection(dbURL, userName, userPwd);  //连接数据库
            Statement stmt;
            ResultSet rs;
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            
            int num = s.GetDataNum(this.getTable_name());
            
            rs=stmt.executeQuery("SELECT * FROM "+table_name);
            rs.first();//读取数据库第一行记录
            for(int i = 0;i < num ;i++){   
                String content = rs.getString("Review_Content");
                //解析语法树和依存关系存入相应的map中
                content = content.toLowerCase();
                ve.init(content);
                
                indexmap.put(i, content);
                Vmap.put(content, ve.getVertexList());
                Emap.put(content, ve.getEdgeList());
                
                rs.next();
            }
            rs.close();
            stmt.close();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Kmeans.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //计算相似度
    public double sim(int i,int j) throws IOException{
        double sim = 0;
        String t1 = indexmap.get(i); //获取两条评论内容
        String t2 = indexmap.get(j);
        List vlist1 = Vmap.get(t1);  //找到对应的树和依存关系
        List vlist2 = Vmap.get(t2);
        
        List elist1 = Emap.get(t1);
        List elist2 = Emap.get(t2);
                
        Graph g1 = new Graph();
        ve.creatGraph(g1,vlist1,elist1);
        
        Graph g2 = new Graph();
        ve.creatGraph(g2,vlist2,elist2);
        
        ComSubgraph csg = new ComSubgraph();
        csg.ComSubgraph2(g1, g2);
        Graph g = new Graph();
        ve.creatGraph(g,csg.getVertexList(),csg.getEdgeList());
        
        sim = ve.sim(g, g1, g2);
    //    System.out.println("图结构相似度："+ve.sim(g, g1, g2));
        
        return sim;
    }
    
    public void calMatrix() throws IOException{
        int num = indexmap.size();
        Matrix = new double[num][num];

        //初始化
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                Matrix[i] = new double[num];
                Matrix[i][j] = 0;
                Matrix[i][i] = 1;
            }
        }
        //计算一半的矩阵
        for (int i = 0; i < num; i++) {
            for (int j = i+1; j < num; j++) {
                Matrix[i][j] = this.sim(i, j);
            }
        }
        //矩阵对阵
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                Matrix[j][i] = Matrix[i][j];
            }
        }        
    }
    
    
    //计算VSM相似度
    public double VSMsim(int i,int j) throws IOException{
        double sim = 0;
        String t1 = indexmap.get(i); //获取两条评论内容
        String t2 = indexmap.get(j);
        VSM vsm = new VSM();
        sim = vsm.VSM(t1, t2);
        return sim;
    }
    
    public void calVSMMatrix() throws IOException{
        int num = indexmap.size();
        Matrix = new double[num][num];

        //初始化
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                Matrix[i] = new double[num];
                Matrix[i][j] = 0;
                Matrix[i][i] = 1;
            }
        }
        //计算一半的矩阵
        for (int i = 0; i < num; i++) {
            for (int j = i+1; j < num; j++) {
                Matrix[i][j] = this.VSMsim(i, j);
            }
        }
        //矩阵对阵
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                Matrix[j][i] = Matrix[i][j];
            }
        }        
    }
    
    public void Writetxt() throws IOException{
        File file = new File(".\\"+table_name+".txt");  //存放数组数据的文件
        FileWriter out = new FileWriter(file);  //文件写入流
        int n = indexmap.size();
        //将数组中的数据写入到文件中。每行各数据之间TAB间隔
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                out.write(Matrix[i][j] + "\t");
            }
            out.write("\r\n");
        }
        out.close();
    }
    
    public void Writedatatxt() throws IOException{
        File file = new File(".\\"+table_name+"data.txt");  //存放数组数据的文件
        FileWriter out = new FileWriter(file);  //文件写入流
        int n = indexmap.size();
        //将数组中的数据写入到文件中。每行各数据之间TAB间隔
        for (int i = 0; i < n; i++) {
            String str = indexmap.get(i);
            out.write(str+"\r\n");
        }
        out.close();
    }
    
    public double aversim() throws IOException{
        File file = new File(".\\"+table_name+".txt");
        SQL s = new SQL();
        int n = s.GetDataNum(table_name);
        double[][] M;//相似度矩阵
        M = new double[n][n];
          //读取出的数组
        BufferedReader in = new BufferedReader(new FileReader(file));  //
        String line;  //一行数据
        int row = 0;
        //逐行读取，并将每个数组放入到数组中
        while ((line = in.readLine()) != null) {
            String[] temp = line.split("\t");
            for (int j = 0; j < temp.length; j++) {
                M[row][j] = Double.parseDouble(temp[j]);
            }
            row++;
        }
        in.close();
        double sum = 0;
        double count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                sum += M[i][j];
                count++;
            }
        }
        return sum/count;
    }
    
    public void Readtxt() throws IOException{
        File file = new File(".\\"+table_name+".txt");
    //    int n = indexmap.size();
        int n=100;
        double[][] arr2 = new double[n][n];  //读取出的数组
        BufferedReader in = new BufferedReader(new FileReader(file));  //
        String line;  //一行数据
        int row = 0;
        //逐行读取，并将每个数组放入到数组中
        while ((line = in.readLine()) != null) {
            String[] temp = line.split("\t");
            for (int j = 0; j < temp.length; j++) {
                arr2[row][j] = Double.parseDouble(temp[j]);
            }
            row++;
        }
        in.close();
        
    }
}
