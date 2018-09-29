/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heu.edu.sun.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;
import org.heu.edu.sun.models.GraphModel;
import org.heu.edu.sun.models.NodeModel;

/**
 *
 * @author sunba
 */
public class GraphLayoutAlgo {
    //将二维图谱布局到m维上
    //读取combined数据的方法

    public double[] GraphLayoutM(int this_dimension,int start_node,int m,HashMap<Integer, ArrayList<Integer>> graph, int node_amount){
        //从图中所有结点中随机选择一个结点
        HashMap<Integer, Double> dist = new HashMap<Integer,Double>();
        List<Integer> visited = new ArrayList<Integer>();
        Queue<Integer> node_queue = new LinkedList<>();
        //将起点加到队列
        node_queue.add(start_node);
        dist.put(start_node, 0.0);
        visited.add(start_node);
        int num = 0;
        double mi = 0.0;
        //定义偏移量精度
        double offset = 0.01;
        double[] X = new double [node_amount+1];       
          while(!node_queue.isEmpty())
          {
              int top = node_queue.poll();//取出队首元素
              num++;
              //一次生成一行，传入参数this_dimension,节点数量，top, dist.get(top)    
                X[top] = dist.get(top);
                mi = 0;
              double distance = dist.get(top)+1;//得出其周边还未被访问的节点的距离
                for(Integer integer : graph.get(top))
                { 
                  //如果dist中还没有该元素说明还没有被访问
                    if(!visited.contains(integer))
                  {
                      mi += offset;
                      dist.put(integer, distance+mi);
                      visited.add(integer);
                      node_queue.add(integer);
                  }
                }
          }      
        return X;
    }
    
    public NodeModel BfsSearch(HashMap<NodeModel, LinkedList<NodeModel>> graph,HashMap<NodeModel, Integer> dist,NodeModel start){
       Queue<NodeModel> q=new LinkedList<>();
       //定义返回的结点
       NodeModel next = new NodeModel();
       q.add(start);//将s作为起始顶点加入队列
       dist.put(start, 0);
       int max=0;
    int i=0;
    while(!q.isEmpty())
    {
        NodeModel top=q.poll();//取出队首元素
        i++;
        //System.out.println("The "+i+"th element:"+top+" Distance from s is:"+dist.get(top));
        max=dist.get(top)+1;//得出其周边还未被访问的节点的距离
        for (NodeModel nm : graph.get(top)) {
            if(!dist.containsKey(nm))//如果dist中还没有该元素说明还没有被访问
            {
                dist.put(nm, max);
                q.add(nm);
            }
        }
    }
    next.setV(i);
    next.setDis(max);
    return next;
    }
    
}
