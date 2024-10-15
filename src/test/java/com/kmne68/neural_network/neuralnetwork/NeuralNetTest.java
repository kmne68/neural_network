/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package com.kmne68.neural_network.neuralnetwork;

import com.kmne68.matrix.Matrix;
import com.kmne68.neural_network.Approximator;
import com.kmne68.neural_network.BatchResult;
import com.kmne68.neural_network.Engine;
import com.kmne68.neural_network.LossFunctions;
import com.kmne68.neural_network.RunningAverages;
import com.kmne68.neural_network.Transform;
import com.kmne68.neural_network.Utils;
import java.util.Random;
import junit.framework.TestCase;

/**
 *
 * @author kemery
 */
public class NeuralNetTest extends TestCase {

  private Random random = new Random();

  public void testApproximator() {

    final int ROWS = 4;
    final int COLUMNS = 5;

    Matrix input = new Matrix(ROWS, COLUMNS, i -> random.nextGaussian()).softmax();

    Matrix expected = new Matrix(ROWS, COLUMNS, i -> 0);

    for (int column = 0; column < COLUMNS; column++) {
      int randomRow = random.nextInt(ROWS);

      expected.set(randomRow, column, 1);
    }

    // evaluate a loss for every column in the input
    Matrix result = Approximator.gradient(input, in -> {
      return LossFunctions.crossEntropy(expected, in);
    });

    input.forEach((index, value) -> {
      double resultValue = result.get(index);
      double expectedValue = expected.get(index);

      if (expectedValue < 0.001) {
        assertTrue(Math.abs(resultValue) < 0.01);
      } else {
        assertTrue(Math.abs(resultValue + 1.0 / value) < 0.01);
      }
    });

    System.out.println("\n***** APPROXIMATOR *****");
    System.out.println();
    System.out.println(input);
    System.out.println("\n***** EXPECTED *****");
    System.out.println(expected);

    System.out.println("***** RESULT *****");
    System.out.println(result);
  }

  public void testBackprop() {

    interface NeuralNet {

      Matrix apply(Matrix m);
    }

    final int inputRows = 4;
    final int columns = 5;
    final int outputRows = 4;

    // A random matrix that acts as an input to the approximator
    Matrix input = new Matrix(inputRows, columns, i -> random.nextGaussian());

    Matrix expected = new Matrix(outputRows, columns, i -> 0);

    Matrix weights = new Matrix(outputRows, inputRows, i -> random.nextGaussian());

    Matrix biases = new Matrix(outputRows, 1, i -> random.nextGaussian());

    for (int column = 0; column < columns; column++) {
      int randomRow = random.nextInt(outputRows);

      expected.set(randomRow, column, 1);
    }

    NeuralNet neuralNet = m -> {

      Matrix out = m.apply((index, value) -> value > 0 ? value : 0);
      out = weights.multiply(out);                           // weights
      out.modify((row, col, value) -> value + biases.get(row));   // biases
      out = out.softmax();                                        // Softmax activation function

      return out;

    };

    Matrix softmaxOutput = neuralNet.apply(input);

    // evaluate a loss for every column in the input
    Matrix approximatedResult = Approximator.gradient(input, in -> {
      Matrix out = neuralNet.apply(in);
      return LossFunctions.crossEntropy(expected, out);
    });

    Matrix calculatedResult = softmaxOutput.apply((index, value) -> value - expected.get(index));
    calculatedResult = weights.transpose().multiply(calculatedResult);
    // back propagate through Relu
    calculatedResult = calculatedResult.apply((index, value) -> input.get(index) > 0 ? value : 0);

    assertTrue(approximatedResult.equals(calculatedResult));

    System.out.println("Value\t\t\t  Softmax Value\t\t\t Expected Value");

    approximatedResult.forEach((index, value) -> {
      double softmaxValue = softmaxOutput.get(index);
      double expectedValue = expected.get(index);
      System.out.println(value + "\t" + softmaxValue + "\t" + expectedValue);
    });

    System.out.println("*******************************\n*******************************");
    System.out.println("EXPECTED: \n" + calculatedResult);
    System.out.println("APPROXIMATED: \n" + approximatedResult);

    System.out.println("\n***** APPROXIMATOR *****");
    System.out.println();
    System.out.println(input);
    System.out.println("\n***** EXPECTED *****");
    System.out.println(expected);

    System.out.println("***** RESULT *****");
    System.out.println(calculatedResult);
  }

  public void testEngine() {

    int inputRows = 5;
    int cols = 6;
    int outputRows = 4;

    Engine engine = new Engine();

    // Transforms for use in runForward
    engine.add(Transform.DENSE, 8, 5);
    engine.add(Transform.RELU);
    engine.add(Transform.DENSE, 5);
    engine.add(Transform.RELU);
    engine.add(Transform.DENSE, 4);

    engine.add(Transform.SOFTMAX);
    engine.setStoreInputError(true);

    Matrix input = Utils.generateInputMatrix(inputRows, cols);
    Matrix expected = Utils.generateExpectedMatrix(outputRows, cols);

    Matrix approximatedError = Approximator.gradient(input, in -> {
      BatchResult batchResult = engine.runForward(in);
      return LossFunctions.crossEntropy(expected, batchResult.getOutput());
    });

    BatchResult batchResult = engine.runForward(input);

    System.out.println("=============== FORWARD =================\n");
    System.out.println("Engine:\n" + engine);

    System.out.println("********** RUN BACKWARD ***********");
    engine.runBackward(batchResult, expected);
    Matrix calculatedError = batchResult.getInputError();

    System.out.println("CALUCULATED ERROR:\n" + calculatedError);
    System.out.println("APPROXIMATED ERROR:\n" + approximatedError);

    calculatedError.setTolerance(0.001);

    assertTrue(calculatedError.equals(approximatedError));

  }

  public void testSoftmaxCrossEntropyGradient() {

    final int ROWS = 4;
    final int COLUMNS = 5;

    // A random matrix that acts as an input to the approximator
    Matrix input = new Matrix(ROWS, COLUMNS, i -> random.nextGaussian());

    Matrix expected = new Matrix(ROWS, COLUMNS, i -> 0);

    for (int column = 0; column < COLUMNS; column++) {
      int randomRow = random.nextInt(ROWS);

      expected.set(randomRow, column, 1);
    }

    Matrix softmaxOutput = input.softmax();

    // evaluate a loss for every column in the input
    Matrix result = Approximator.gradient(input, in -> {
      return LossFunctions.crossEntropy(expected, in.softmax());
    });

    System.out.println("VALUE\t\t\t  SOFTMAX VALUE\t\t\t EXPECTED VALUE");

    result.forEach((index, value) -> {
      double softmaxValue = softmaxOutput.get(index);
      double expectedValue = expected.get(index);

      assertTrue(Math.abs(value - (softmaxValue - expectedValue)) < 0.01);

      System.out.println(value + "\t" + softmaxValue + "\t" + expectedValue);
    });

    System.out.println("\n***** APPROXIMATOR *****");
    System.out.println();
    System.out.println(input);
    System.out.println("\n***** EXPECTED *****");
    System.out.println(expected);

    System.out.println("***** RESULT *****");
    System.out.println(result);
  }

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

    System.out.println("================================\n");
    System.out.println("inputs: \n" + input);
    System.out.println("weights: \n" + weights);
    System.out.println("biases: \n" + biases);
    System.out.println("result: \n" + result);
  }

  public void testAverageColumn() {
    int rows = 3;
    int cols = 4;

    Matrix m = new Matrix(rows, cols, i -> i);

    double averageIndex = (cols - 1) / 2.0;

    Matrix expected = new Matrix(rows, 1);
    expected.modify((row, col, value) -> (row * cols + averageIndex));
    Matrix result = m.averageColumn();

    System.out.println("TEST AVERAGE COLUMN:\n");
    System.out.println(m);
    System.out.println("TEST AVERAGE EXPECTED:\n");
    System.out.println(expected);
    System.out.println("TEST RESULT AVERAGE COLUMN:\n");
    System.out.println(result);

    assertTrue(expected.equals(result));
  }

  public void testCrossEntropy() {
    double[] expectedValues = {1, 0, 0, 0, 0, 1, 0, 1, 0};

    Matrix expected = new Matrix(3, 3, i -> expectedValues[i]);

    System.out.println("================================\n");
    System.out.println("TEST CROSS ENTROPY: \n" + expected);

    Matrix actual = new Matrix(3, 3, i -> 0.05 * i * i).softmax();

    System.out.println("ACTUAL: \n" + actual);

    Matrix result = LossFunctions.crossEntropy(expected, actual);

    System.out.println("LOSS FUNCTION RESULT: \n" + result);

    actual.forEach((row, col, index, value) -> {
      double expectedValue = expected.get(index);
      double loss = result.get(col);

      if (expectedValue > 0.9) {
        assertTrue(Math.abs(Math.log(value) + loss) < 0.001);
      }
    });
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

    System.out.println("================================\n");
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

    /**
     * The number of rows in layerOneWeights is the number of neurons in layer
     * one. Each produces an output. The second layer is multiplying the output.
     * Every neuron in the second layer needs a number of weights equal to the
     * number of outputs from previous layer. which is equal to the number of
     * neurons in the previous layer.
     *
     */
    Matrix layerTwoWeights = new Matrix(layerTwoSize, layerOneWeights.getRows(), i -> random.nextGaussian());
    Matrix layerTwoBiases = new Matrix(layerTwoSize, 1, i -> random.nextGaussian());

    System.out.println("================================\n");
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
    output = output.modify(value -> value > 0 ? value : 0);
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

  public void testTrainengine() {

    int inputRows = 500;
    int cols = 32;
    int outputRows = 3;

    //  Matrix input = Utils.generateInputMatrix(inputRows, cols);                  // moved into loop below
    //  Matrix expected = Utils.generateTrainableExpectedMatrix(outputRows, input); // moved into loop below
    Engine engine = new Engine();
    engine.add(Transform.DENSE, 6, inputRows);
    engine.add(Transform.RELU);
    engine.add(Transform.DENSE, outputRows);
    engine.add(Transform.SOFTMAX);
    
    RunningAverages runningAverages = new RunningAverages(2, 10, (callNumber, averages) -> {});
    System.exit(0);

    for (int i = 0; i < 2000; i++) {      // changed i < 20 to i < 2000 lesson 138
      var trainingMatrices = Utils.generateTrainingMatrix(inputRows, outputRows, cols);
      var input = trainingMatrices.getInput();
      var expected = trainingMatrices.getOutput();
      
    //  Matrix input = Utils.generateInputMatrix(inputRows, cols);
    //   Matrix expected = Utils.generateTrainableExpectedMatrix(outputRows, input);
      BatchResult batchResult = engine.runForward(input);
      //  engine.evaluate(batchResult, expected);     // commented out in lesson 135
      //  double loss1 = batchResult.getLoss();       // commented out in lesson 135
      engine.runBackward(batchResult, expected);
      engine.adjust(batchResult, 0.01);
      //  batchResult = engine.runForward(input);     // commented out in lesson 135
      engine.evaluate(batchResult, expected);

      double loss2 = batchResult.getLoss();
      double percentCorrect = batchResult.getPercentCorrect();

      System.out.println("++++++++++++++++++++++");
      //  System.out.println("loss1 + \t + loss2");
      //  System.out.println(loss1 + "\t" + loss2);
      System.out.printf("Loss: %.3f, %% correct: %.2f\n", loss2, percentCorrect);
      // System.out.println("percentCorrect: " + percentCorrect);
    }
  }

  public void testWeightGradient() {
    int inputRows = 4;
    int outputRows = 5;
    Matrix weights = new Matrix(outputRows, inputRows, i -> random.nextGaussian());
    Matrix input = Utils.generateInputMatrix(inputRows, 1);
    Matrix expected = Utils.generateExpectedMatrix(outputRows, 1);

    Matrix output = weights.multiply(input).softmax();
    Matrix loss = LossFunctions.crossEntropy(expected, output);
    Matrix calculatedError = output.apply((index, value) -> value - expected.get(index));
    Matrix calculatedWeightGradients = calculatedError.multiply(input.transpose());

    Matrix approximatedWeightGradients = Approximator.weightGradient(
            weights,
            w -> {
              Matrix out = w.multiply(input).softmax();
              return LossFunctions.crossEntropy(expected, out);
            });

    calculatedWeightGradients.setTolerance(0.01);
    assertTrue(calculatedWeightGradients.equals(approximatedWeightGradients));

    System.out.println("********** testWeightGradient ***********");
    System.out.println("Input: \n" + input);
    System.out.println("Weights: \n" + weights);
    System.out.println("Output: \n" + output);
    System.out.println("Expected: \n" + expected);
    System.out.println("Loss: \n" + loss);
    System.out.println("Calcualted Error: \n" + calculatedError);
    System.out.println("calculatedWeightGradients: \n" + calculatedWeightGradients);
    System.out.println("approximatedWeightGradients: \n" + approximatedWeightGradients);
  }

}
