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
    
    System.out.println("\n***** APPROXIMATOR GRADIENT METHOD *****");
    
    input.forEach((row, col, index, value) -> {
      System.out.printf("%12.5f", value);
      
      if(col == input.getColumns() - 1) {
        System.out.println("");
      }
    });
    
    return null;
  }
}
