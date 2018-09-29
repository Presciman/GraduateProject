/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heu.edu.sun.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.heu.edu.sun.algorithm.GraphLayoutAlgo;
import org.heu.edu.sun.algorithm.MatrixGenerator;
import org.heu.edu.sun.models.GraphModel;
import org.heu.edu.sun.utils.FileOperator;
/**
 *
 * @author sunba
 */
public class main {
    //定义数据存储位置
//    public static String fileUrl = "H:\\MyDocs\\孙百西2018毕设\\毕设数据V1.0\\email-Eu-core.csv";
    public static String fileUrl = "H:\\MyDocs\\孙百西2018毕设\\毕设数据V1.0\\facebook_combined.csv";
//      public static String fileUrl = "H:\\MyDocs\\孙百西2018毕设\\毕设数据V1.0\\Test_Data.csv";
//      public static String fileUrl = "H:\\MyDocs\\孙百西2018毕设\\毕设数据V1.0\\Test_Data_old.csv";
    /**
     * @param args the command linargumentse
     */
    public static void main(String[] args) throws IOException {
     //打开csv图谱文件
     File file = new File(fileUrl);
     int status=0;
     int m_dimension = 50;
     double threshold = 0.65;
     //ArrayList<GraphModel> list = new ArrayList<GraphModel>();
     String status_str = new String();
     FileOperator fo = new FileOperator();
     GraphLayoutAlgo gla = new GraphLayoutAlgo();
        try {
            fo.CSVFileOperator(file, m_dimension, threshold);
     /*   // TODO 调用Utils里FileOperator中的方法打开文件
        File file = new File(fileUrl);
        GraphModel graphModel = new GraphModel();
        FileOperator fo = new FileOperator();
        try {
            
            graphModel = fo.OpenFile(file);
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("发生了错误 : main.java - 31");
        }
        //调用algorithms里MatrixGenerator中的GenerateMatrix方法
        MatrixGenerator mg = new MatrixGenerator();
        mg.GenerateMatrix(graphModel);
        System.out.print(graphModel.getNodes());*/
     
        //编辑Graphdata.txt文件
//        File graphfile = new File(finalpath);
//        FileOutputStream writerStream = new FileOutputStream(graphfile); 
//        //文件不存在时候，主动创建文件。
//        if(!graphfile.exists()){
//            graphfile.createNewFile();
//        }
//        if(graphfile.exists())
//        {
//            BufferedWriter fw = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (graphfile,true),"UTF-8"));
//            for(int f_i=0;f_i<result_array.length;f_i++)
//            {
////               int pos = graph_m.getFromNode();
////               int weight_int = weight[pos];
//               fw.write(result_array[f_i][0]*10+",");
//               fw.write(result_array[f_i][1]*10+",");
//               fw.write("\r\n"); 
//            }
//            fw.close();
//        }
        } catch (Exception ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }

        
    }
        
        
}
    

