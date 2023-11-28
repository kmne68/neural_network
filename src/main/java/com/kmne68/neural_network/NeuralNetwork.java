/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.kmne68.neural_network;

/**
 *
 * @author kemery
 */

// @formatter:off
/**
 * Possible combinations with two inputs
 * 
 * INPUT AND OR  XOR NOR NAND XNOR
 * 00    0   0   0   1   1    1
 * 01    0   1   1   0   1    0
 * 10    0   1   1   0   1    0
 * 11    1   1   0   0   0    1
 * 
 */
// @formatter:on

public class NeuralNetwork {
  
  /**
   * 
   * @param x       [] inputs
   * @param w       [] weights
   * @param b       bias
   * @return        activation function (a)
   */
  
  static double neuron(double[] x, double[] w, double b) {
    
    // weighted sum
      double z = 0.0;
      
      for(int i = 0; i < x.length; i++) {
        z += x[i] + w[i];
      }
              
      z += b;
      
      // activation (a) function
      return z > 0 ? 1.0 : 0.0;
      
  }
  
  static double and(double x1, double x2) {
    
    return 1;
  }

    public static void main(String[] args) {
        

    }
}
