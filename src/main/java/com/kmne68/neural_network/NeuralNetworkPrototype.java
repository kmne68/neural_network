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

public class NeuralNetworkPrototype {
  
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
        z += x[i] * w[i];
      }
              
      z += b;
      
      // activation (a) function
      return z > 0 ? 1.0 : 0.0;
      
  }
  
  
  static double and(double x1, double x2) {
    
    return neuron(new double[] {x1, x2}, new double[] {1, 1}, -1);
  }
  
  
  static double or(double x1, double x2) {
    
    return neuron(new double[] {x1, x2}, new double[] {1, 1}, 0);
  }
    
    
  static double xor(double x1, double x2) {
    
    return and(or(x1, x2), nand(x1, x2));
  }
  
    
  static double nor(double x1, double x2) {
    
    return neuron(new double[] {x1, x2}, new double[] {-1, -1}, 1);
  }
  
  
  static double nand(double x1, double x2) {
    
    return neuron(new double[] {x1, x2}, new double[] {-1, -1}, 2);
  }


  static double xnor(double x1, double x2) {
    
    return or(and(x1, x2), nor(x1, x2));
  }
  

    public static void main(String[] args) {
        
      for(int i = 0; i < 4; i++) {
        double x1 = i / 2;
        double x2 = i % 2;
        
      //  double output = and(x1, x2);
      //  double output = or(x1, x2);
      //  double output = nand(x1, x2);
      //  double output = nor(x1, x2);
      //  double output = xor(x1, x2);
        double output = xnor(x1, x2);
      
        System.out.printf("%d%d\t%d\n", (int)x1, (int)x2, (int)output);
      }
    }
}
