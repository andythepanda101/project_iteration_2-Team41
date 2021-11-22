package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NightBusStrategyTest {

  private NightBusStrategy nightBusStrategy;

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

    nightBusStrategy = new NightBusStrategy();
  }

  /**
   * Testing that buses are created in the correct sequence.
   */
  @Test
  public void testCreateBus() {
    Bus firstBus = nightBusStrategy.createBus(
        1, new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(firstBus instanceof SmallBus);
    Bus secondBus = nightBusStrategy.createBus(
        1, new Line(10001, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(secondBus instanceof SmallBus);
    Bus thirdBus = nightBusStrategy.createBus(
        1, new Line(10002, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(thirdBus instanceof SmallBus);
    Bus fourthBus = nightBusStrategy.createBus(
        1, new Line(10003, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(fourthBus instanceof LargeBus);
    Bus fifthBus = nightBusStrategy.createBus(
        1, new Line(10004, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(fifthBus instanceof SmallBus);
  }

  /**
   * Checks if the counter resets correctly.
   * If it does, fourth bus should be SmallBus, since the sequence is reset.
   */
  @Test
  public void testResetCount() {
    Bus firstBus = nightBusStrategy.createBus(
        1, new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(firstBus instanceof SmallBus);
    Bus secondBus = nightBusStrategy.createBus(
        1, new Line(10001, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(secondBus instanceof SmallBus);
    Bus thirdBus = nightBusStrategy.createBus(
        1, new Line(10002, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(thirdBus instanceof SmallBus);

    nightBusStrategy.resetCount();
    Bus fourthBus = nightBusStrategy.createBus(
        1, new Line(10003, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(fourthBus instanceof SmallBus);
  }

  /**
   * Checks if the counter is decremented correctly.
   * If it does, fifth bus should be LargeBus, since the sequence is decremented.
   */
  @Test
  public void testDecrementCount() {
    Bus firstBus = nightBusStrategy.createBus(
        1, new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(firstBus instanceof SmallBus);
    Bus secondBus = nightBusStrategy.createBus(
        1, new Line(10001, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(secondBus instanceof SmallBus);
    Bus thirdBus = nightBusStrategy.createBus(
        1, new Line(10002, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(thirdBus instanceof SmallBus);
    Bus fourthBus = nightBusStrategy.createBus(
        1, new Line(10003, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(fourthBus instanceof LargeBus);

    nightBusStrategy.decrementCount();
    Bus fifthBus = nightBusStrategy.createBus(
        1, new Line(10004, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(fifthBus instanceof LargeBus);
  }
}
