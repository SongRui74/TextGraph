/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgraph;

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
public class SQL {
    private final String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; //加载JDBC驱动
    private final String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=mypro"; //连接服务器和数据库mypro
    private final String userName = "sa";
    private final String userPwd = "123456";
    private Connection conn;

    public String tablename;

    public void settablename(String name) {
        tablename = name;
    }

    public String gettablename() {
        return tablename;
    }
    
    
    public Map<String, List> Vmap = new HashMap();
    public Map<String, List> Emap = new HashMap();

    public Map<String, List> getVmap() {
        return Vmap;
    }

    public Map<String, List> getEmap() {
        return Emap;
    }
    
    
    /**
     * 记录评论解析后的依存关系列表
     *
     * @param table_name
     */
    public void RecordMap(String table_name) throws SQLException, ClassNotFoundException, IOException {
        Init ve = new Init();
        ve.wordweight();
        
        Class.forName(driverName);
        conn = DriverManager.getConnection(dbURL, userName, userPwd);  //连接数据库
        Statement stmt;
        ResultSet rs;
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        rs = stmt.executeQuery("SELECT * FROM " + table_name);        
        //循环记录
        while (rs.next()) {
            /*文本转化为列表*/
            String content = rs.getString("Review_Content");
            content = content.toLowerCase();
            ve.init(content);
            /*写入map中*/
            Vmap.put(content, ve.getVertexList());
            Emap.put(content, ve.getEdgeList());
        }
        rs.close();
        stmt.close();
        conn.close();

    }
    
    /**
     * 获取表中数据数量
     *
     * @param table 表名
     * @return 返回表中数据数量
     */
    public int GetDataNum(String table) {
        int num = 0;
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(dbURL, userName, userPwd);

            Statement stmt = conn.createStatement();
            ResultSet rs;
            String sql = null;
            String tempcol = "a";
            sql = "select count(*) as " + tempcol + " from " + table;
            rs = stmt.executeQuery(sql);
            rs.next();
            num = rs.getInt(tempcol);

            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return num;
    }
}
