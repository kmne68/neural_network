/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package com.kmne68.neural_network.loader.test;

import com.kmne68.neural_network.loader.BatchData;
import com.kmne68.neural_network.loader.Loader;
import com.kmne68.neural_network.loader.MetaData;
import junit.framework.TestCase;

/**
 *
 * @author kemery
 */
public class TestTestLoader extends TestCase {
  
  public TestTestLoader(String testName) {
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

    
  public void testLoader() {
    int batchSize = 32;
    Loader testLoader = new TestLoader(60000, 32);
    
    MetaData metaData = testLoader.open();
    
    for(int i = 0; i < metaData.getNumberOfBatches(); i++) {
      BatchData batchData = testLoader.readBatch();
      
      assertTrue(batchData != null);
      
      int itemsRead = metaData.getItemsRead();
      
      assertTrue(itemsRead == batchSize);
    }
  }
}
