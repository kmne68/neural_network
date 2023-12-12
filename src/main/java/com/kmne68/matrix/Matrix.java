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
  
  
  private static final String NUMBER_FORMAT = "%12.5f";
  
  private int rows;
  private int cols;
  
  private double[] a;
  
  public interface Producer {
    double produce(int index);
  }
  
  
  public Matrix(int rows, int cols) {
    
    this.rows = rows;
    this.cols = cols;
    
    a = new double[rows * cols];
  }
  
  
  public Matrix(int rows, int cols, Producer producer) {
    this(rows, cols);
    
    for(int i = 0; i < a.length; i++) {
      a[i] = producer.produce(i);
    }
  }
  
    
  public String toString() {
    StringBuilder sb = new StringBuilder();
    
    int index = 0;
            
    for(int row = 0; row < rows; row++) {
      for(int col = 0; col < cols; col++) {
        sb.append(String.format(NUMBER_FORMAT, a[index]));
        
        index++;
                
      }
      sb.append("\n");
    }
    
    return sb.toString();
  }
}
