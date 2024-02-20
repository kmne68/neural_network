/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package com.kmne68.neural_network.neuralnetwork;

import com.kmne68.matrix.Matrix;
import com.kmne68.neural_network.Engine;
import com.kmne68.neural_network.Transform;
import java.util.Random;
import junit.framework.TestCase;

/**
 *
 * @author kemery
 */
public class NeuralNetTest extends TestCase {

  private Random random = new Random();

  public NeuralNetTest(String testName) {
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

  public void testAddBias() {

    // Threee neurons
    Matrix input = new Matrix(3, 3, i -> (i + 1));
    Matrix weights = new Matrix(3, 3, i -> (i + 1));  // 3 rows (i.e. neurons) need 3 weights
    Matrix biases = new Matrix(3, 1, i -> (i + 1));

    Matrix result = weights.multiply(input).modify((row, col, value) -> value + biases.get(row));

    double[] expectedValues = {+31.00000, +37.00000, +43.00000,
      +68.00000, +83.00000, +98.00000,
      +105.00000, +129.00000, +153.00000};

    Matrix expected = new Matrix(3, 3, i -> expectedValues[i]);

    assertTrue(expected.equals(result));

    System.out.println("inputs: \n" + input);
    System.out.println("weights: \n" + weights);
    System.out.println("biases: \n" + biases);
    System.out.println("result: \n" + result);
  }
  
  
  public void testEngine() {
    Engine engine = new Engine();
    
    engine.add(Transform.DENSE, 8, 5);
    engine.add(Transform.RELU);
    engine.add(Transform.DENSE, 5);
    engine.add(Transform.RELU);
    engine.add(Transform.DENSE, 4);
    engine.add(Transform.SOFTMAX);
    
    System.out.println("Engine:\n" + engine);
  }
  

  public void testReLu() {

    final int numberOfNeurons = 5;
    final int numberOfInputs = 6;
    final int inputSize = 4;

    // Threee neurons
    // Use of Gaussian helps avoid overflow issues in the network
    Matrix input = new Matrix(inputSize, numberOfInputs, i -> random.nextDouble());
    Matrix weights = new Matrix(numberOfNeurons, inputSize, i -> random.nextGaussian());  // 3 rows (i.e. neurons) need 3 weights
    Matrix biases = new Matrix(numberOfNeurons, 1, i -> random.nextGaussian());

    Matrix result1 = weights.multiply(input).modify((row, col, value) -> value + biases.get(row));
    Matrix result2 = weights.multiply(input).modify((row, col, value) -> value + biases.get(row)).modify(value -> value > 0 ? value : 0);

    result2.forEach((index, value) -> {

      double originalValue = result1.get(index);

      if (originalValue > 0) {
        assertTrue(Math.abs(originalValue - value) < 0.000001);
      } else {
        assertTrue(Math.abs(value) < 0.000001);
      }
    });

    System.out.println("inputs: \n" + input);
    System.out.println("weights: \n" + weights);
    System.out.println("biases: \n" + biases);
    System.out.println("result: \n" + result1);
  }
  
  /*
  public void testBasicEngine() {
    
    Engine engine = new Engine();
    
    engine.add(Transform.DENSE);
    engine.add(Transform.RELU);
    engine.add(Transform.DENSE);
    engine.add(Transform.SOFTMAX);
    
    System.out.println("ENGINE: \n" + engine);
  }
  */
  
  public void testThreeLayer() {
    
    int inputSize = 5;
    int layerOneSize = 6;
    int layerTwoSize = 4;
    
    Matrix input = new Matrix(inputSize, 1, i -> random.nextGaussian());
    Matrix layerOneWeights = new Matrix(layerOneSize, input.getRows(), i -> random.nextGaussian());
    Matrix layerOneBiases = new Matrix(layerOneSize, 1, i -> random.nextGaussian());
    
    /** The number of rows in layerOneWeights is the number of neurons in layer one.
     * Each produces an output.
     * The second layer is multiplying the output.
     * Every neuron in the second layer needs a number of weights equal to the number of outputs from previous layer.
     * which is equal to the number of neurons in the previous layer.
     **/
    Matrix layerTwoWeights = new Matrix(layerTwoSize, layerOneWeights.getRows(), i -> random.nextGaussian());
    Matrix layerTwoBiases = new Matrix(layerTwoSize, 1, i -> random.nextGaussian());
    
    // The first layer after the input layer
    var output = input;
    System.out.println("OUTPUT:\n" + output);
    
    // Apply Weights
    output = layerOneWeights.multiply(output);
    System.out.println("OUTPUT:\n" + output);
    
    // Apply Biases
    output = output.modify((row, col, value) -> value + layerOneBiases.get(row));
    System.out.println("OUTPUT AFTER BIASES\n" + output);
    
    // Apply ReLu
    output = output.modify(value -> value > 0 ? value: 0);
    System.out.println("OUTPUT AFTER RELU\n" + output);
    
    // Layer 2
    // Apply Weights
    output = layerTwoWeights.multiply(output);
    System.out.println("OUTPUT:\n" + output);
    
    // Apply Biases
    output = output.modify((row, col, value) -> value + layerTwoBiases.get(row));
    System.out.println("OUTPUT AFTER BIASES\n" + output);
    
    // Apply Softmax to the output (final) layer
    output = output.softmax();
    System.out.println("SOFTMAX OUTPUT:\n" + output);
  }

}
