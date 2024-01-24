/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.matrix;

import java.util.Arrays;

/**
 *
 * @author kemery
 */
public class Matrix {
  
  
  private static final String NUMBER_FORMAT = "%+12.5f";
  private static final double TOLERANCE = 0.000001;
  
  private int rows;
  private int cols;
  
  private double[] a;
  
  public interface Producer {
    double produce(int index);
  }
  
  /**
   * General purpose method to produce a new Matrix
   */  
  public interface IndexValueProducer {
    double produce(int index, double value);
  }
  
  
  public interface ValueProducer {
    double produce(double value);
  }
  
  
  public interface RowColumnProducer {
    double produce(int row, int col, double value);
  }
  
  
  public interface IndexValueConsumer {
    void consume(int index, double value);
  }
  
  
  public Matrix apply(IndexValueProducer producer) {
    
    Matrix result = new Matrix(rows, cols);
    
    for(int i = 0; i < a.length; i++) {
      result.a[i] = producer.produce(i, a[i]);
    }
    
    return result;
  }
  
  // For testing ReLu with forEach(IndexValueConsumer)
  public Matrix modify(ValueProducer producer) {
    
    for(int i = 0; i < a.length; i++) {
      
      a[i] = producer.produce(a[i]);
      
    }
    
    return this;
  }
    
  
  // For testing ReLu with modify(ValueProducer)
  public void forEach(IndexValueConsumer consumer) {
    
    for(int i = 0; i < a.length; i++) {
      
      consumer.consume(i, a[i]);
    }
  }
  
  
  public Matrix modify(RowColumnProducer producer) {
    
    int index = 0;
    
    for(int row = 0; row < rows; ++row) {
      for(int col = 0; col < cols; ++col) {
        a[index] = producer.produce(row, col, a[index]);
        
        ++index;
      }
    }
    return this;
  }
  
  
  /*
      0 1 2
      3 4 5
      6 7 8
  
      The 7 is at index position 7, it is at row = 2, col = 1 and cols = 3 
      the formumla for seven's position is row * cols + col
  */
  public Matrix multiply(Matrix m) {
    Matrix result = new Matrix(rows, m.cols);
    
    assert cols == m.rows: "Cannot multiply; wrong number of rows vs. colums.";
    
    /**
     * OPTIMIZATION
     * 
     * Potential orders of multiplication
     * row, col, n ==> initial order
     * row, n, col ==> final order
     * col, n, row
     * col, row, n
     * n, row, col
     * n, col, row
     */
    
    
    for(int row = 0; row < result.rows; row++) {
      for(int n = 0; n < cols; n++) {
        for(int col = 0; col < result.cols; col++) {
        
          result.a[row * result.cols + col] += a[row * cols + n] * m.a[col + n * m.cols];
        }
      }
    }
    
    return result;
  }
  
  
  public double get(int index) {
    return a[index];
  }
  

  @Override
  public int hashCode() {
    int hash = 7;
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Matrix other = (Matrix) obj;
    if (this.rows != other.rows) {
      return false;
    }
    if (this.cols != other.cols) {
      return false;
    }
    
    for(int i = 0; i < a.length; i++) {
      if(Math.abs(a[i] - other.a[i]) > TOLERANCE) {
        return false;
      }
    }
    
    return true;
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
