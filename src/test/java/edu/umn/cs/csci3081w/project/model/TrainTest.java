package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrainTest {

  private Train testTrain;

  private Route testRouteIn;
  private Route testRouteOut;


  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    testRouteIn = new Route(0, "testRouteIn",
        stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.025);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);

    testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    testTrain = new DieselTrain(1,
        new Line(10000, "testLine", "TRAIN", testRouteOut, testRouteIn),
        1.0);
  }

  /**
   * Tests constructor.
   */
  @Test
  public void testConstructor() {
    assertEquals(1, testTrain.getId());
    assertEquals("testRouteOut1", testTrain.getName());
    assertEquals(120, testTrain.getCapacity());
    assertEquals(1, testTrain.getSpeed());
    assertEquals(testRouteOut, testTrain.getLine().getOutboundRoute());
    assertEquals(testRouteIn, testTrain.getLine().getInboundRoute());
  }

  /**
   * Tests if testIsTripComplete function works properly.
   */
  @Test
  public void testIsTripComplete() {
    assertEquals(false, testTrain.isTripComplete());
    testTrain.move();
    testTrain.move();
    testTrain.move();
    testTrain.move();
    assertEquals(true, testTrain.isTripComplete());

  }


  /**
   * Tests if loadPassenger function works properly.
   */
  @Test
  public void testLoadPassenger() {
    for (int i = 0; i < testTrain.getCapacity(); i++) {
      Passenger newPassenger = new Passenger(3, String.format("testPassenger%d", i));
      assertEquals(1, testTrain.loadPassenger(newPassenger));
    }
    Passenger extraGuy = new Passenger(2, "I can't get on the train");
    assertEquals(0, testTrain.loadPassenger(extraGuy));
  }


  /**
   * Tests if move function works properly.
   */
  @Test
  public void testMove() {

    assertEquals("test stop 2", testTrain.getNextStop().getName());
    assertEquals(1, testTrain.getNextStop().getId());
    testTrain.move();

    assertEquals("test stop 1", testTrain.getNextStop().getName());
    assertEquals(0, testTrain.getNextStop().getId());

    testTrain.move();
    assertEquals("test stop 1", testTrain.getNextStop().getName());
    assertEquals(0, testTrain.getNextStop().getId());

    testTrain.move();
    assertEquals("test stop 2", testTrain.getNextStop().getName());
    assertEquals(1, testTrain.getNextStop().getId());

    testTrain.move();
    assertEquals(null, testTrain.getNextStop());

  }

  /**
   * Tests if update function works properly.
   */
  @Test
  public void testUpdate() {

    assertEquals("test stop 2", testTrain.getNextStop().getName());
    assertEquals(1, testTrain.getNextStop().getId());
    testTrain.update();

    assertEquals("test stop 1", testTrain.getNextStop().getName());
    assertEquals(0, testTrain.getNextStop().getId());

    testTrain.update();
    assertEquals("test stop 1", testTrain.getNextStop().getName());
    assertEquals(0, testTrain.getNextStop().getId());

    testTrain.update();
    assertEquals("test stop 2", testTrain.getNextStop().getName());
    assertEquals(1, testTrain.getNextStop().getId());

    testTrain.update();
    assertEquals(null, testTrain.getNextStop());

  }


  /**
   * Tests if updateDistance function works properly.
   */
  @Test
  public void testReport() {

    testTrain.move();
    try {

      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      testTrain.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "####Train Info Start####" + System.lineSeparator()
              + "ID: 1" + System.lineSeparator()
              + "Name: testRouteOut1" + System.lineSeparator()
              + "Speed: 1.0" + System.lineSeparator()
              + "Capacity: 120" + System.lineSeparator()
              + "Position: 44.97358,-93.235071" + System.lineSeparator()
              + "Distance to next stop: 0.843774422231134" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num of passengers: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Train Info End####" + System.lineSeparator();
      assertEquals(data, strToCompare);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Test the co2 calculation for a train.
   */
  @Test
  public void testCo2() {
    assertEquals(6, testTrain.getCurrentCO2Emission());
    Passenger testPassenger1 = new Passenger(3, "testPassenger1");
    testTrain.loadPassenger(testPassenger1);
    assertEquals(8, testTrain.getCurrentCO2Emission());
  }


  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testTrain = null;
  }

}
