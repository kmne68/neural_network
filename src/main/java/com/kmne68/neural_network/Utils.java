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

    return new Matrix(rows, cols, i -> random.nextGaussian());
  }

  public static Matrix generateExpectedMatrix(int rows, int cols) {

    Matrix expected = new Matrix(rows, cols, i -> 0);

    for (int col = 0; col < cols; col++) {
      int randomRow = random.nextInt(rows);

      expected.set(randomRow, col, 1);
    }
    return expected;
  }

  public static Matrix generateTrainableExpectedMatrix(int outputRows, Matrix input) {

    Matrix expected = new Matrix(outputRows, input.getColumns());

    Matrix columnSums = input.sumColumns();

  //  System.out.println("FROM Utils: \n");
    columnSums.forEach((row, col, value) -> {
      int rowIndex = (int) (outputRows * (Math.sin(value) + 1.0) / 2.0);

      expected.set(rowIndex, col, 1);
  //    System.out.println(rowIndex);
    });
    

    return expected;
  }
  
  
  public static TrainingMatrices generateTrainingMatrix(int inputRows, int outputRows, int cols) {
    
    Matrix input = new Matrix(inputRows, cols);
    Matrix output = new Matrix(outputRows, cols);
    
    for(int col = 0; col < cols; col++) {
      int radius = random.nextInt(outputRows);
      
      double[] values = new double[inputRows];
      double initialRadius = 0;
      
      for(int row = 0; row < inputRows; row++) {
        double value = random.nextGaussian();
        values[row] = value;
        initialRadius += value * value;
        
      }
      initialRadius = Math.sqrt(initialRadius);
      
      for(int row = 0; row < inputRows; row++) {
        input.set(row, col, values[row] * radius/initialRadius);
      }
      
      output.set(radius, col, 1);   // how far is this point from the origin
    }
    
    return new TrainingMatrices(input, output);
    
  }
}
