/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.kmne68.neural_network.loader;

/**
 *
 * @author kemery
 */
public interface Loader {
  
  MetaData open();
  void close();
  
  MetaData getMetaData();
  BatchData readBatch();
  
}
