/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package com.kmne68.neural_network.matrix;

import com.kmne68.matrix.Matrix;
import java.util.Random;
import junit.framework.TestCase;


/**
 *
 * @author kemery
 */
public class MatrixTest extends TestCase {
  
  private Random random = new Random();
  
  public MatrixTest(String testName) {
    super(testName);
  }
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }
  
  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  
  public void testToString() {
    
    Matrix m = new Matrix(3, 4, i -> i*2);
    String text = m.toString();
    double[] expected = new double[12];
    
    for(int i = 0; i < expected.length; i++) {
      expected[i] = i * 2;
    }
    
    int index = 0;
    var rows = text.split("\n");
    
    assertTrue(rows.length == 3);
    
    
    for(var row: rows) {
      
      var values = row.split("\\s+");      
      for(var textValue: values) {
        
        if(textValue.length() == 0) {
          continue;
        }
        
        var doubleValue = Double.valueOf(textValue);
        
        assertTrue(Math.abs(doubleValue - expected[index]) < 0.00001);
        
        index++;
      }
    }
  }
  
  
  public void testAddMatrices() {
    Matrix m1 = new Matrix(2, 2, i -> i);
    Matrix m2 = new Matrix(2, 2, i -> i * 1.5);
    Matrix expected = new Matrix(2, 2, i -> i * 2.5);
    
    Matrix result = m1.apply((index, value) -> value + m2.get(index));
    
    assertTrue(expected.equals(result));
    
  }
  
  
  public void testMultiply() {
    Matrix m1 = new Matrix(2, 3, i -> i);
    Matrix m2 = new Matrix(3, 2, i -> i);
    
    double[] expectedValues = { 10, 13, 28, 40 };
    Matrix expected = new Matrix(2, 2, i -> expectedValues[i]);
    
    System.out.println(m1);
    System.out.println(m2);
    Matrix result = m1.multiply(m2);
    
    assertTrue(expected.equals(result));
  }
  
  
  public void testMultiplySpeed() {
    
    int rows = 500;
    int cols = 500;
    int mid = 50;
    Matrix m1 = new Matrix(rows, mid, i -> i);
    Matrix m2 = new Matrix(mid, cols, i -> i);
    
    var start = System.currentTimeMillis();
    m1.multiply(m2);
    var end = System.currentTimeMillis();
    
    System.out.printf("Matrix multiplication time taken: %dms\n", end - start);
  }
  
  
  public void testMultiplyDouble() {
    Matrix m = new Matrix(3, 4, i -> 0.5 * (i - 6));
    
    double x = 0.5;
    Matrix expected = new Matrix(3, 4, i -> x * 0.5 * (i - 6));
    
    Matrix result = m.apply((index, value) -> x * value);
    
    assertTrue(result.equals(expected));
    assertTrue(Math.abs(result.get(1) + 1.25) < 0.0001);
  }
  
  
  
  public void testEquals() {
    Matrix m1 = new Matrix(3, 4, i -> 0.5 * (i - 6));
    Matrix m2 = new Matrix(3, 4, i -> 0.5 * (i - 6));
    Matrix m3 = new Matrix(3, 4, i -> 0.5 * (i - 6.2));
    
    assertTrue(m1.equals(m2));
    
    assertFalse(m1.equals(m3));
  }

  
  public void testSumColumns() {
    Matrix m = new Matrix(4, 5, i -> i);
    Matrix result = m.sumColumns();
    
    System.out.println("Test matrix:");
    System.out.println(m);
    System.out.println("Sum Columns test result: ");
    System.out.println(result);
    
    double[] expectedValues = { 30.00000, 34.00000, 38.00000, 42.00000, 46.00000 };
    Matrix expected = new Matrix(1, 5, i -> expectedValues[i]);
    
    assertTrue(expected.equals(result));
    
  }
  
  
  public void testSoftMax() {
    Matrix m = new Matrix(5, 8, i -> random.nextGaussian());
    
    Matrix result = m.softmax();
    
    System.out.println("Softmax result: \n" + result);
    
    double[] colSums = new double[8];
    
    result.forEach((row, col, value) -> {
      assertTrue(value >= 0 && value <= 1.0);
      
      colSums[col] += value;    
    });
    
    for(var sum: colSums) {
      assertTrue(Math.abs(sum - 1.0) < 0.00001);
    }
  }
    
}