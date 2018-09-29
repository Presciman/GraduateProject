
package org.heu.edu.sun.utils;

import Jama.Matrix;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.heu.edu.sun.algorithm.GraphLayoutAlgo;
import org.heu.edu.sun.algorithm.PCA;
import org.heu.edu.sun.models.GraphModel;
import org.heu.edu.sun.models.NodeModel;

/**
 *
 * @author sunba
 */
public class FileOperator {
//    public static String savepath = "E:\\edge.csv";
//    public static String finalpath  = "E:\\Graphdata.csv";
    //该方法能打开原始数据文件并且将其处理成为邻接矩阵
    public GraphModel OpenFile(File file) throws IOException{
        GraphModel gm = new GraphModel();
        int nodes = 0;
        int edges = 0;
        int line = 1;
        try {
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            //使用readLine方法，一次读一行
            while((s = br.readLine())!=null){
                //匹配#，识别文件信息
                //识别有向图还是无向图
                if(line == 1)
                {
                    System.out.println("信息:Directed Graph");
                }
                //获取结点和边的个数
                if(line == 3)
                {
                    int index1 = s.indexOf("Nodes:");
                    int index2 = s.indexOf(" Edges");
                    nodes = Integer.parseInt(s.substring(index1+7, index2));
                    //String nodess = s.substring(index1+7, index2);
                    //System.out.println(nodes);
                }
                if(line == 3)
                {
                    int index3 = s.indexOf("Edges:");
                    edges = Integer.parseInt(s.substring(index3+7));
                    //String edgess = s.substring(index3+7);
                    //System.out.println(edges);
                }
                //记录下一行
                line++;
            } 
        gm.setFromNode(nodes);
        gm.setNextNode(edges);
            br.close();    
        } catch (FileNotFoundException ex) {
            //输出错误信息
            Logger.getLogger(FileOperator.class.getName()).log(Level.SEVERE, null, ex);
            gm.setFromNode(nodes);
            gm.setNextNode(-1);
        }
        return gm;
    }
    
    @SuppressWarnings("empty-statement")
    public HashMap<Integer,ArrayList<Integer>> CSVFileOperator(File file,int m, double threshold) {
        
        GraphLayoutAlgo gla = new GraphLayoutAlgo();
        ArrayList<GraphModel> listgm = new ArrayList<GraphModel>();
        ArrayList<GraphModel> listfinal = new ArrayList<GraphModel>();
        ArrayList<GraphModel> original_list = new ArrayList<GraphModel>();
        //自定义数据结构存储边集合  
       HashMap<Integer,ArrayList<Integer>> hm_graph = new HashMap<Integer,ArrayList<Integer>>();
       
        int node = 0;
        int edges =0;
       int former_node = 0;
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
//            File graphfile1 = new File(savepath);
//            FileOutputStream writerStream1 = new FileOutputStream(graphfile1); 
//            BufferedWriter fw1 = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (graphfile1,true),"UTF-8"));
            while((s = br.readLine())!=null)
            {
                //TestData
//                String[] ss = s.split("	");
                //eu-core
                String[] ss = s.split("	|,| ");
                //逐项封装到数据结构当中
                GraphModel gm = new GraphModel();
                gm.setFromNode(Integer.parseInt(ss[0]));
                gm.setNextNode(Integer.parseInt(ss[1]));
                GraphModel gm_reverse = new GraphModel();
                gm_reverse.setFromNode(Integer.parseInt(ss[1]));
                gm_reverse.setNextNode(Integer.parseInt(ss[0]));
                       listgm.add(gm);
                       listfinal.add(gm);
                       listgm.add(gm_reverse);
                       original_list.add(gm);
                       edges++;
//                  fw1.write(ss[0]+",");
//                  fw1.write(ss[1]+",");
//                   fw1.write("\r\n");
//                System.out.println(ss[0]+" "+ss[1]);
                //取得节点数
                if(Integer.parseInt(ss[1]) > node)
                {
                    node = Integer.parseInt(ss[1]);
                }
                gm.setNodes(node);
            }
//            fw1.close();
            //gla.GraphLayoutM(node, node, listgm);
            //初始化第一个节点
            GraphModel former = new GraphModel();
            former = listgm.get(0);
            int temp_fromnode = former.getFromNode();
            int flag = 0;
            //TODO list里面有很多重复元素
            //TODO 定义一个listgm_norepeat,以避免NextNode不在hashMap的key中情况
            ArrayList<GraphModel> listgm_norepeat = new ArrayList<GraphModel>();
                //去重
                for(GraphModel graphModel : listgm)
                {
                    if(!listgm_norepeat.contains(graphModel))
                    {
                        listgm_norepeat.add(graphModel);
                        edges++; 
                    }
                }
            int size = listgm_norepeat.size();
            //先将listgm处理成为HashMap
           for(int i = 0 ; i < size; i++)
          {
              if(!hm_graph.containsKey(listgm_norepeat.get(i).getFromNode()))
              {
                if(listgm_norepeat.get(i).getFromNode() != temp_fromnode)
                {  
                  ArrayList<Integer> inside = new ArrayList<Integer>();
                  for(int j = flag ; j < i ; j++)
                  {
                      inside.add(listgm_norepeat.get(j).getNextNode());
                  }
//                  for(int n=0;n<inside.size();n++)
//                  {
//                      System.out.print(inside.get(n)+"  ");
//                  }
//                  System.out.println();
                  hm_graph.put(temp_fromnode, inside);
                  temp_fromnode = listgm_norepeat.get(i).getFromNode();
                  flag = i;
                }
              }
              
              else if(hm_graph.containsKey(listgm_norepeat.get(i).getFromNode()))
              {
                  ArrayList<Integer> inside_new = new ArrayList<Integer>();
                  inside_new = hm_graph.get(listgm_norepeat.get(i).getFromNode());
                  inside_new.add(listgm_norepeat.get(i).getNextNode());
                  hm_graph.put(listgm_norepeat.get(i).getFromNode(), inside_new);
              }
          }
           //把最后一个节点对应的边加进去
           ArrayList<Integer> inside_last = new ArrayList<Integer>();
           for(int k = flag; k < listgm.size();k++)
           {
               inside_last.add(listgm.get(k).getNextNode());
           }
           hm_graph.put(temp_fromnode, inside_last);
           System.out.println("最后一个节点是"+temp_fromnode);
//           hm_graph.put(temp_fromnode, inside);
//           Iterator  it = inside.descendingIterator();  
//            while(it.hasNext()){  
//            System.out.println("#############################"+it.next());  
//             }  
//            System.out.println(temp_fromnode);
            int new_size = 0;
            double Matrix_high[][] = new double[node+1][m];
            //因为选取的下一节点与之前有重复就没有意义了，因此需要定义一个ArrayList记住选过的节点
            ArrayList<Integer> visited = new ArrayList<Integer>();
            //第一个节点从所有节点当中随机选出来
            int start_node_pos = (int)(Math.random()*node);
            int start_node = listgm.get(start_node_pos).getFromNode();
            for(int dimension = 0; dimension<m;dimension++)
            {
                visited.add(start_node);
                //获取到返回的一维数组
                double Matrix_onedim[] = gla.GraphLayoutM(dimension,start_node, m, hm_graph,node);
                //拷贝一份数组用于标记上次的节点
                double Matrix_copy[] = Arrays.copyOf(Matrix_onedim, Matrix_onedim.length);
                //遍历visited
                //将所有用过的节点处距离置为-1
                for(int v_i=0;v_i<Matrix_copy.length;v_i++)
                {
                    //如果节点在visited里面
                    if(visited.contains(v_i))
                    {
                        //那么对应的位置就置为-1作为标记，绕开找最大值的时候再找到之前选过的节点
                        Matrix_copy[v_i] = -1;  
                    }
                }

                for(int t=0;t<Matrix_onedim.length;t++)
                {
                    //每一维数组拼凑成n*m数组
                    Matrix_high[t][dimension] = Matrix_onedim[t];
                    //选取下一节点,选取和此节点图论距离最远的那个
                    if(Matrix_copy[start_node] < Matrix_copy[t])
                    {
                            start_node = t;
                    }
                }
            }
            //降低精度
            for(int i_out=0;i_out<Matrix_high.length;i_out++)
            {
                for(int i_ins=0;i_ins<Matrix_high[0].length;i_ins++)
                {
                    double temp = Matrix_high[i_out][i_ins];
                    //保留两位小数
                    String str_temp = String.format("%.2f", temp);
                    Matrix_high[i_out][i_ins] = Double.parseDouble(str_temp);
                }
            }
        //TODO 声明PCA类
        PCA pca = new PCA();
        //TODO 先将矩阵进行中心化
        //averageArray 中心化后的矩阵
        double averageArray[][] = pca.changeAverageToZero(Matrix_high);
        
        //TODO 计算协方差矩阵
        //result_cov 协方差矩阵
        double result_cov[][] = pca.getVarianceMatrix(averageArray);
        
        //TODO 求特征值矩阵
        //result_eigenValue 向量的特征值二维数组矩阵
        double result_eigenValue[][] = pca.getEigenvalueMatrix(result_cov);
        
        //TODO 标准化矩阵（特征向量矩阵）
        //result_standard 标准化后的二维数组矩阵
        double result_standard[][] = pca.getEigenVectorMatrix(result_eigenValue);
        
        //TODO 寻找主成分
        //principalMatrix主成分矩阵
        Matrix principalMatrix = pca.getPrincipalComponent(Matrix_high, result_eigenValue, result_standard, threshold);
        
        //TODO 得到优化后的矩阵
        //result_matrix 结果矩阵
        Matrix result_matrix = pca.getResult(Matrix_high, principalMatrix);
        double result_array[][] = result_matrix.getArray();
            System.out.println(result_array.length+" * "+result_array[0].length);
//        for(int i_out=0;i_out<result_array.length;i_out++)
//            {
//                System.out.print("[");
//                for(int i_ins=0;i_ins<result_array[0].length;i_ins++)
//                {
//                    System.out.print(result_array[i_out][i_ins]+",");
//                }
//                System.out.print("]");
//                System.out.println();
//            }
        System.out.println("###################################################################");
        System.out.println();
       
        //将边集处理成json
        StringBuffer str = new StringBuffer();
        boolean first1 = true;
        str.append("[");
        System.out.println("***************************************");
        System.out.println(listfinal.size());
         for(int json_j=0;json_j<listfinal.size();json_j++)
        {
            if(!first1)
            {
                str.append(",");
            } 
            str.append("{");
            str.append("source:'"+listfinal.get(json_j).getFromNode()+"',");
            str.append("target:'"+listfinal.get(json_j).getNextNode()+"'");
            str.append("}");
            str.append("\r\n");
            first1 = false;
        }
        str.append("];");
        str.toString();
        
        //将坐标集处理成json
        StringBuffer strb = new StringBuffer();
        boolean first = true;
        strb.append("[");
        for(int json_i=0;json_i<result_array.length;json_i++)
        {
            if(!first)
            {
                strb.append(",");
            } 
            String y_str = result_array[1].toString();
            strb.append("{");
            strb.append("x:'"+(int)(result_array[json_i][0]*1000)+"',");
            strb.append("y:'"+(int)(result_array[json_i][1]*1000)+"'");
            strb.append("}");
            strb.append("\r\n");
            first = false;
        }
        strb.append("];");
        System.out.println(strb.toString());

        //对矩阵每一行求平方和，作为权值
        int[] weight = new int[result_array.length];
            for(int i_out=0;i_out<result_array.length;i_out++)
            {
                for(int i_ins=0;i_ins<result_array[0].length;i_ins++)
                {
                    weight[i_out] += result_array[i_out][i_ins] * result_array[i_out][i_ins];
                }
            }
            
            //可视化展示
            //拼接html代码作为展示结果
            //拼接字符串
            String htmlpage = "";
            htmlpage +="<!DOCTYPE html>\n" +
"<html>\n" +
"	<head>\n" +
"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">  \n" +
"		<title>布局效果</title>\n" +
"		<script src=\"http://code.jquery.com/jquery-latest.js\"></script>\n" +
"		<script src=\"d3.js\"></script>\n" +
"		<script src=\"d3.min.js\"></script>\n" +
"		\n" +
"	</head>\n" +
"	<body>\n" +
"		<!--<svg width=\"50\" height=\"50\">\n" +
"    		<circle cx=\"25\" cy=\"25\" r=\"25\" fill=\"purple\" />\n" +
"		</svg>-->\n" +
"	</body>\n" +
"	<script>";
            htmlpage += "\n";
            htmlpage += " var json_edge =";
            htmlpage += str;
            htmlpage += "\n";
            htmlpage += "var json_coor =";
            htmlpage += strb;
            htmlpage += "\n";
            htmlpage += "var edgeobj = eval(json_edge);\n" +
"			var coorobj = eval(json_coor);";
            htmlpage += "\n";
            htmlpage += "var getRandomColor = function(){\n" +
"				return '#'+Math.floor(Math.random()*16777215).toString(16); \n" +
"			}";
            htmlpage += "\n";
            htmlpage += "var svgContainer = d3.select(\"body\").append(\"svg\")\n" +
"				.attr(\"width\",10000)\n" +
"				.attr(\"height\",10000);\n" +
" 			for(var i=0;i<edgeobj.length;i++)\n" +
" 			{\n" +
" 				var nodeSource = edgeobj[i].source;\n" +
" 				var nodeTarget = edgeobj[i].target;\n" +
" 				var nodeS_x = coorobj[nodeSource].x;\n" +
" 				var nodeS_y = coorobj[nodeSource].y;\n" +
" 				var nodeT_x = coorobj[nodeTarget].x;\n" ;

            //根据不同的节点数量适配前端展示的样式
            if(node<=5000)
            {
                htmlpage += "var nodeT_y = coorobj[nodeTarget].y;\n"+
" 				var nodeS = svgContainer.append(\"circle\")\n" +
" 					.attr(\"cx\",nodeS_x)\n" +
" 					.attr(\"cy\",nodeS_y)\n"+
                        ".attr(\"r\",10)\n" +
" 					.attr(\"fill\",getRandomColor);\n" +
" 				var nodeT = svgContainer.append(\"circle\")\n" +
" 					.attr(\"cx\",nodeT_x)\n" +
" 					.attr(\"cy\",nodeT_y)\n" +
" 					.attr(\"r\",10)\n" +
" 					.attr(\"fill\",getRandomColor);\n" +
" 				var line = svgContainer.append(\"line\")\n" +
"			    .attr(\"x1\",nodeS_x)\n" +
"			    .attr(\"y1\",nodeS_y)\n" +
"			    .attr(\"x2\",nodeT_x)\n" +
"			    .attr(\"y2\",nodeT_y)\n" +
"			    .attr(\"stroke\",getRandomColor)\n" +
"			    .attr(\"stroke-width\",3);\n" +
" 			};\n" +
" 			\n" +
"		\n" ;

            }
            if(node>5000)
            {
                htmlpage += "var nodeT_y = coorobj[nodeTarget].y;\n"+
" 				var nodeS = svgContainer.append(\"circle\")\n" +
" 					.attr(\"cx\",nodeS_x)\n" +
" 					.attr(\"cy\",nodeS_y)\n"+
                        ".attr(\"r\",5)\n" +
" 					.attr(\"fill\",getRandomColor);\n" +
" 				var nodeT = svgContainer.append(\"circle\")\n" +
" 					.attr(\"cx\",nodeT_x)\n" +
" 					.attr(\"cy\",nodeT_y)\n" +
" 					.attr(\"r\",5)\n" +
" 					.attr(\"fill\",getRandomColor);\n" +
" 				var line = svgContainer.append(\"line\")\n" +
"			    .attr(\"x1\",nodeS_x)\n" +
"			    .attr(\"y1\",nodeS_y)\n" +
"			    .attr(\"x2\",nodeT_x)\n" +
"			    .attr(\"y2\",nodeT_y)\n" +
"			    .attr(\"stroke\",getRandomColor)\n" +
"			    .attr(\"stroke-width\",1);\n" +
" 			};\n" +
" 			\n" +
"		\n" ;

            }
 		htmlpage += "		</script>\n" +
"</html>";			

            //将字符串htmlpage写入文件
            //定义文件名
            java.util.Random random=new java.util.Random();
            String htmlfileurl = "H:\\MyDocs\\孙百西2018毕设\\毕设代码\\GraphLayout";
//            htmlfileurl += random.toString();
              htmlfileurl += Integer.toString(random.nextInt(20));
            htmlfileurl += ".html";
            File htmlfile = new File(htmlfileurl);
            if(!htmlfile.exists())
            {
               htmlfile.createNewFile();
            }
            PrintStream ps = new PrintStream(new FileOutputStream(htmlfile));
            ps.println(htmlpage);
            //运行
            Runtime ce = Runtime.getRuntime();
                    String cmd = "cmd /c start ";
                    cmd += htmlfileurl;
                    ce.exec(cmd);
//                    ce.exec("cmd /c start  "+htmlfileurl);
//                    ce.exec("cmd /c start H:\\MyDocs\\孙百西2018毕设\\代码20180427\\Data18.html");
//                    
//                    String [] cmd={"cmd","/c","start",htmlfileurl}; 
//                    Process proc =Runtime.getRuntime().exec(cmd);
                    
//                    ce.exec("cmd /c start "+htmlfile.getAbsolutePath());
        //改成用HashMap存储
//        HashMap<Integer,Integer> weight_map = new HashMap<Integer,Integer>();
//            for(int i_out=0;i_out<result_array.length;i_out++)
//            {
//                int weight_mid = 0;
//                for(int i_ins=0;i_ins<result_array[0].length;i_ins++)
//                {
//                    weight_mid += result_array[i_out][i_ins] * result_array[i_out][i_ins];
//                }
//                weight_map.put(i_out,weight_mid);
//            }
        

        
        } catch (Exception ex) {
            Logger.getLogger(FileOperator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("打开文件失败："+ex);
            return hm_graph;
        }
        System.out.println("节点数为:"+node);
        System.out.println("边数为:"+edges);
        return hm_graph;
    }
}
