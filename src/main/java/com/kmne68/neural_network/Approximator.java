package com.kmne68.neural_network;


import com.kmne68.matrix.Matrix;
import java.util.function.Function;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kemery
 */
public class Approximator {
  
  
  public static Matrix gradient(Matrix input, Function<Matrix, Matrix> transform) {
    
    final double INC = 0.00000001;
  
    Matrix loss1 = transform.apply(input);
    
    assert loss1.getColumns() == input.getColumns(): "Input/loss columns not equal.";
    assert loss1.getRows() == 1: "Transform does not return just one row.";
    
    Matrix result = new Matrix(input.getRows(), input.getColumns(), i -> 0);
    
    System.out.println("\n***** APPROXIMATOR INPUT *****");
    System.out.println(input);
    
    
    System.out.println("\n***** APPROXIMATOR LOSS1 *****");
    System.out.println(loss1);
    
    System.out.println("\n***** APPROXIMATOR GRADIENT METHOD *****");
    
    input.forEach((row, col, index, value) -> {
      Matrix incremented = input.addIncrement(row, col, INC);
      
      Matrix loss2 = transform.apply(incremented);
      
      double rate = (loss2.get(col) - loss1.get(col)) / INC;
      
      result.set(row, col, rate);
    });
    
    return result;
  }
  
  
    public static Matrix weightGradient(Matrix weights, Function<Matrix, Matrix> transform) {
    
    final double INC = 0.00000001;
  
    Matrix loss1 = transform.apply(weights);
      
    Matrix result = new Matrix(weights.getRows(), weights.getColumns(), i -> 0);
    
    System.out.println("\n***** APPROXIMATOR INPUT *****");
    System.out.println(weights);
    
    
    System.out.println("\n***** APPROXIMATOR LOSS1 *****");
    System.out.println(loss1);
    
    System.out.println("\n***** APPROXIMATOR GRADIENT METHOD *****");
    
    weights.forEach((row, col, index, value) -> {
      Matrix incremented = weights.addIncrement(row, col, INC);
      
      Matrix loss2 = transform.apply(incremented);
      
      double rate = (loss2.get(0) - loss1.get(0)) / INC;
      
      result.set(row, col, rate);
    });
    
    return result;
    }
    
}
