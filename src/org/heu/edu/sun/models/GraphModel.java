/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heu.edu.sun.models;

/**
 *
 * @author sunba
 */
public class GraphModel {
    int nodes;
   public int getNodes()
   {
       return nodes;
   }
   public void setNodes(int nodes)
   {
       this.nodes = nodes;
   }
   
   int fromNode;
   public int getFromNode()
   {
       return fromNode;
   }
   public void setFromNode(int fromNode)
   {
       this.fromNode = fromNode;
   }
   
   int nextNode;
   public int getNextNode()
   {
       return nextNode;
   }
   public void setNextNode(int nextNode)
   {
       this.nextNode = nextNode;
   }
}
