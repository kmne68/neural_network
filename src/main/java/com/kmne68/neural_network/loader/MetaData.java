/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network.loader;

/**
 *
 * @author kemery
 */
public interface MetaData {
  
  public int getNumberOfItems();

  public void setNumberOfItems(int numberOfItems);

  public int getInputSize();

  public void setInputSize(int inputSize);;

  public int getExpectedSize();

  public void setExpectedSize(int expectedSize);

  public int getNumberOfBatches();

  public void setNumberOfBatches(int numberOfBatches);

  public int getTotalItemsRead();

  public void setTotalItemsRead(int totalItemsRead);

  public int getItemsRead();

  public void setItemsRead(int itemsRead);
  
}
