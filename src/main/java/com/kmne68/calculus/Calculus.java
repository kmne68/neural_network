/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.calculus;

import java.util.function.DoubleFunction;

/**
 *
 * @author kemery
 */
public class Calculus {
  
  private static final double INCREASE = 0.00001;
  
  
  public static double function1(double x) {
    return 3.7 * x + 5.3;
  }
  
  
  public static double function2(double x) {
    return x * x;
  }
  
  
  public static double differentiate(DoubleFunction<Double> function, double x) {
    
    double output1 = function.apply(x);
    double output2 = function.apply(x + INCREASE);
    
    return (output2 - output1) / INCREASE;
    
  }
  
  
  public static void main(String[] args) {
    
    System.out.println("FUNCTION 1\n");
    for(double x = -2; x <  2; x += 0.1) {
      double gradient1 = differentiate(Calculus::function1, x);
      System.out.printf("%.2f\t%.2f\n", x, gradient1);
      
    }
    
    
    System.out.println("FUNCTION 2\n");
    for(double x = -2; x <  2; x += 0.1) {  
      double gradient2 = differentiate(Calculus::function2, x);
      System.out.printf("%.2f\t%.2f\n", x, gradient2);
    }
  }
        
  
}
