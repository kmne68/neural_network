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
  
  
  public static double function3(double y1, double y2) {
    
    return y1 * y2 + 4.7 * y1;
  }
  
  
  public static double function4(double x) {
    
    return function1(x) * function2(x) + 4.7 * function1(x);
  }
  
  
  public static double functionProduct(double x) {
    
    return function2(function1(x));
  }
  
  
  public static double differentiate(DoubleFunction<Double> function, double x) {
    
    double output1 = function.apply(x);
    double output2 = function.apply(x + INCREASE);
    
    return (output2 - output1) / INCREASE;
    
  }
  
  
  public static void main(String[] args) {
    
    double x = 3.64;
    double y = function1(x);
    double y1 = function1(x);
    double y2 = function2(x);
    double z = function2(y);
    double z2 = function3(y1, y2);
    
    System.out.println("x: " + x + "\ny: " + y + "\nz: " + z + "\nfunction2(function1): " + functionProduct(x));
    
    double dy_dx = differentiate(Calculus::function1, x);
    double dz_dy = differentiate(Calculus::function2, y);
    double dz_dx = differentiate(Calculus::functionProduct, x);
    
    double dy1_dx = differentiate(Calculus::function1, x);
    double dy2_dx = differentiate(Calculus::function2, x);
    double dz2_dy1 = differentiate(w -> function3(w, y2), y1);
    double dz2_dy2 = differentiate(w -> function3(y1, w), y2);

    double dz2_dxCalculated = (dz2_dy1 * dy1_dx) + (dz2_dy2 * dy2_dx);
    double dz2_dxApproximated = differentiate(Calculus::function4, x);
    
    System.out.println("dy_dx: " + dy_dx);
    System.out.println("dz_dy: " + dz_dy);
    System.out.println("dz_dx: " + dz_dx);
    
    System.out.println("dz2_dxCalculated: " + dz2_dxCalculated);
    System.out.println("dz2_dxApproximated: " + dz2_dxApproximated);
    
    
    /* 
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
    */
  }
        
  
}
