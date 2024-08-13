/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network;

import com.kmne68.matrix.Matrix;

/**
 *
 * @author kemery
 */
public class LossFunctions {
  
  public static Matrix crossEntropy(Matrix expected, Matrix actual) {
    
    return actual.apply((index, value)->{
      
      return -expected.get(index) * Math.log(value);

    }).sumColumns();
        
  }
  
}
