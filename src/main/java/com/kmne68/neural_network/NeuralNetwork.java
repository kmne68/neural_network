/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network;

import com.kmne68.matrix.Matrix;
import com.kmne68.neural_network.loader.BatchData;
import com.kmne68.neural_network.loader.Loader;
import com.kmne68.neural_network.loader.MetaData;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author kemery
 */
public class NeuralNetwork {

  private Engine engine;

  private int epochs = 20; // The number of times we'll go through the dataset while training
  private double learningRate;
  private double initialLearningRate = 0.1;
  private double finalLearningRate = 0;
  private Object lock = new Object();
  private int threads;
  
  
  public NeuralNetwork() {
    engine = new Engine();
  }

  public void setThreads(int threads) {
    this.threads = threads;
  }
  
  
  public void add(Transform transform, double... params) {
    engine.add(transform, params);
  }

  public void setLearningRates(double initialLearningRate, double finalLearningRate) {
    this.initialLearningRate = initialLearningRate;
    this.finalLearningRate = finalLearningRate;
  }

  public void setEpochs(int epochs) {
    this.epochs = epochs;
  }

  public void fit(Loader trainLoader, Loader evalLoader) {
    learningRate = initialLearningRate;

    for (int epoch = 0; epoch < epochs; epoch++) {
      System.out.printf("Epoch %3d \n", epoch);

      runEpoch(trainLoader, true);

      if (evalLoader != null) {
        runEpoch(evalLoader, false);
      }

      learningRate -= (initialLearningRate - finalLearningRate) / epochs;

    }
  }

  @Override
  public String toString() {
    return "NeuralNetwork{" + "engine=" + engine.toString() + '}';
  }

  private void runEpoch(Loader loader, boolean trainingMode) {

    // TO DO enclose the following in a try/catch
    loader.open();

    var queue = createBatchTasks(loader, trainingMode);
    consumeBatchTasks(queue, trainingMode);

    loader.close();
  }

  private LinkedList<Future<BatchResult>> createBatchTasks(Loader loader, boolean trainingMode) {
    
    LinkedList<Future<BatchResult>> batches = new LinkedList<>();    // The return value of tasks in a thread pool

    MetaData metaData = loader.getMetaData();
    int numberOfBatches = metaData.getNumberOfBatches();
    
    var executor = Executors.newFixedThreadPool(threads);

    for (int i = 0; i < numberOfBatches; i++) {
      batches.add(executor.submit(() -> runBatch(loader, trainingMode)));
    }
    
    executor.shutdown();
    
    return batches;
  }
  

  private void consumeBatchTasks(Object queue, boolean trainingMode) {

  }
  

  private BatchResult runBatch(Loader loader, boolean trainingMode) {

    MetaData metaData = loader.open();

    BatchData batchData = loader.readBatch();

    int itemsRead = metaData.getItemsRead();
    int inputSize = metaData.getInputSize();
    int expectedSize = metaData.getExpectedSize();

    Matrix input = new Matrix(inputSize, itemsRead, batchData.getInputBatch());
    Matrix expected = new Matrix(expectedSize, itemsRead, batchData.getExpectedBatch());

    BatchResult batchResult = engine.runForward(input);
    
    if(trainingMode) {
      engine.runBackward(batchResult, expected);

      synchronized(lock) {
        engine.adjust(batchResult, learningRate);
      }
    } else {
      engine.evaluate(batchResult, expected);
    }
            
    return batchResult;
  }

}
