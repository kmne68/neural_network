/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.matrix;

/**
 *
 * @author kemery
 */
public class Matrix {
  
  public interface Producer {
    double produce(int index);
  }
  
  
  private double[] a;
  
  public Matrix(int rows, int cols) {
    a = new double[rows * cols];
  }
  
  public Matrix(int rows, int cols, Producer producer) {
    a = new double[rows * cols];
    
    for(int i = 0; i < a.length; i++) {
      a[i] = producer.produce(i);
    }
  }
}
