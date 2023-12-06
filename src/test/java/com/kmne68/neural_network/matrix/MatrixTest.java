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
  
  private static final String NUMBER_FORMAT = "%.";
  
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

  public void testSomeMethod() {
    // TODO review the generated test code and remove the default call to fail.
    System.out.println("This is only a test...");
    
    Matrix m = new Matrix(3, 4, i -> i*2);
//    fail("The test case is a prototype.");
  }
  
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    
    return sb.toString();
  }
  
}
