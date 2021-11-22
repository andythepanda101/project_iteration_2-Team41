package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DayTrainStrategyTest {

  private DayTrainStrategy dayTrainStrategy;

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

    dayTrainStrategy = new DayTrainStrategy();
  }

  /**
   * Testing that trains are created in the correct sequence.
   */
  @Test
  public void testCreateTrain() {
    Train firstTrain = dayTrainStrategy.createTrain(
        1, new Line(10000, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(firstTrain instanceof ElectricTrain);
    Train secondTrain = dayTrainStrategy.createTrain(
        1, new Line(10001, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(secondTrain instanceof ElectricTrain);
    Train thirdTrain = dayTrainStrategy.createTrain(
        1, new Line(10002, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(thirdTrain instanceof ElectricTrain);
    Train fourthTrain = dayTrainStrategy.createTrain(
        1, new Line(10003, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(fourthTrain instanceof DieselTrain);
    Train fifthTrain = dayTrainStrategy.createTrain(
        1, new Line(10004, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(fifthTrain instanceof ElectricTrain);
  }

  /**
   * Checks if the counter resets correctly.
   * If it does, fourth train should be ElectricTrain, since the sequence is reset.
   */
  @Test
  public void testResetCount() {
    Train firstTrain = dayTrainStrategy.createTrain(
        1, new Line(10000, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(firstTrain instanceof ElectricTrain);
    Train secondTrain = dayTrainStrategy.createTrain(
        1, new Line(10001, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(secondTrain instanceof ElectricTrain);
    Train thirdTrain = dayTrainStrategy.createTrain(
        1, new Line(10002, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(thirdTrain instanceof ElectricTrain);

    dayTrainStrategy.resetCount();
    Train fourthTrain = dayTrainStrategy.createTrain(
        1, new Line(10003, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(fourthTrain instanceof ElectricTrain);
  }

  /**
   * Checks if the counter is decremented correctly.
   * If it does, fifth train should be DieselTrain, since the sequence is decremented.
   */
  @Test
  public void testDecrementCount() {
    Train firstTrain = dayTrainStrategy.createTrain(
        1, new Line(10000, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(firstTrain instanceof ElectricTrain);
    Train secondTrain = dayTrainStrategy.createTrain(
        1, new Line(10001, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(secondTrain instanceof ElectricTrain);
    Train thirdTrain = dayTrainStrategy.createTrain(
        1, new Line(10002, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(thirdTrain instanceof ElectricTrain);
    Train fourthTrain = dayTrainStrategy.createTrain(
        1, new Line(10003, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(fourthTrain instanceof DieselTrain);

    dayTrainStrategy.decrementCount();
    Train fifthTrain = dayTrainStrategy.createTrain(
        1, new Line(10004, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(fifthTrain instanceof DieselTrain);
  }
}