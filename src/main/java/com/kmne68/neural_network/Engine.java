/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network;

import java.util.LinkedList;

/**
 *
 * @author kemery
 */
public class Engine {
  
  private LinkedList<Transform> transforms = new LinkedList<>();
  
  
  public void add(Transform transform) {
    transforms.add(transform);
  }
  
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    
    for(var t: transforms) {
      sb.append(t).append("\n");
    }
    
    return sb.toString();
  }
}
