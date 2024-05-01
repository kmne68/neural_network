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
  
    Matrix loss1 = transform.apply(input);
    
    assert loss1.getColumns() == input.getColumns(): "Input/loss columns not equal.";
    assert loss1.getRows() == 1: "Transform does not return just one row.";
    
    
    System.out.println("\n***** APPROXIMATOR INPUT *****");
    System.out.println(input);
    
    
    System.out.println("\n***** APPROXIMATOR LOSS1 *****");
    System.out.println(loss1);
    
    System.out.println("\n***** APPROXIMATOR GRADIENT METHOD *****");
    
    input.forEach((row, col, index, value) -> {


    });
    
    return null;
  }
}
