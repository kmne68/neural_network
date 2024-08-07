/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network;

import com.kmne68.matrix.Matrix;
import java.util.Random;
        
        
/**
 *
 * @author kemery
 */
public class Utils {
  
  private static Random random = new Random();
  
  public static Matrix generateInputMatrix(int rows, int cols) {
    
    return new Matrix (rows, cols, i -> random.nextGaussian());
  }
  
  
  public static Matrix generateExpectedMatrix(int rows, int cols) {
    
    Matrix expected = new Matrix(rows, cols, i -> 0);
    
    for(int col = 0; col < cols; col++) {
      int randomRow = random.nextInt(rows);
    
      expected.set(randomRow, col, 1);
    }
    return expected;
  }
}
