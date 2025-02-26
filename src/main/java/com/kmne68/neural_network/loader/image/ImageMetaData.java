/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network.loader.image;

import com.kmne68.neural_network.loader.AbstractMetaData;

/**
 *
 * @author kemery
 */
public class ImageMetaData extends AbstractMetaData {
  
  private int width;
  private int height;

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  @Override
  public void setItemsRead(int itemsRead) {
    super.setItemsRead(itemsRead); 

    super.setTotalItemsRead(super.getTotalItemsRead() + itemsRead);
  }
  
}
