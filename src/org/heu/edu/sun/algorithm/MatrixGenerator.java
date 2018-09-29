/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heu.edu.sun.algorithm;

import java.util.ArrayList;
import org.heu.edu.sun.models.GraphModel;

/**
 *
 * @author sunba
 */
public class MatrixGenerator {
    public int[][] GenerateAdjacencyMatrix(int node_amount,int from_node,int to_node)
    {
         
         GraphModel gm = new GraphModel();
         System.out.println(from_node+" "+to_node+" "+node_amount);
         int[][] Matrix = new int[node_amount+1][node_amount+1];
                 Matrix[from_node][to_node] = 1;
                 Matrix[to_node][from_node] = 1;
        System.out.print("邻接矩阵已经生成了");
        return Matrix;
        
    }
    //传入参数this_dimension,节点数量，top, dist.get(top)
    public int[] GenerateHighDimensionMatrix(int this_dimension, int node_amount, int this_node, int dist)
    {
          int[] Matrix_oned = new int[node_amount+1];
                  Matrix_oned[this_node] = dist;
          return Matrix_oned;
    }
}
