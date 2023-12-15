/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package com.kmne68.neural_network.matrix;

import com.kmne68.matrix.Matrix;
import junit.framework.TestCase;


/**
 *
 * @author kemery
 */
public class MatrixTest extends TestCase {
  
  
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

}
