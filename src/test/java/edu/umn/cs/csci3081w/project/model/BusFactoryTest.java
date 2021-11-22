package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BusFactoryTest {

  private BusFactory busFactory;

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

    busFactory = new BusFactory();
  }

  /**
   * Checks if the factory creates vehicles if strategy is set to Day.
   */
  @Test
  public void checkDayStrategy() {
    busFactory.setBusStrategy(new DayBusStrategy());
    Bus firstBus = busFactory.createVehicle(
        1, new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(firstBus instanceof LargeBus);
    Bus secondBus = busFactory.createVehicle(
        1, new Line(10001, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(secondBus instanceof LargeBus);
    Bus thirdBus = busFactory.createVehicle(
        1, new Line(10002, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(thirdBus instanceof SmallBus);
    Bus fourthBus = busFactory.createVehicle(
        1, new Line(10003, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(fourthBus instanceof LargeBus);
  }

  /**
   * Checks if the factory creates vehicles if strategy is set to Night.
   */
  @Test
  public void checkNightStrategy() {
    busFactory.setBusStrategy(new NightBusStrategy());
    Bus firstBus = busFactory.createVehicle(
        1, new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(firstBus instanceof SmallBus);
    Bus secondBus = busFactory.createVehicle(
        1, new Line(10001, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(secondBus instanceof SmallBus);
    Bus thirdBus = busFactory.createVehicle(
        1, new Line(10002, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(thirdBus instanceof SmallBus);
    Bus fourthBus = busFactory.createVehicle(
        1, new Line(10003, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(fourthBus instanceof LargeBus);
    Bus fifthBus = busFactory.createVehicle(
        1, new Line(10004, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(fifthBus instanceof SmallBus);
  }

  /**
   * Checks if the factory correctly deploys vehicles into a list.
   */
  @Test
  public void checkDeployVehicle() {
    busFactory.setBusStrategy(new DayBusStrategy());
    List<Vehicle> vehicleList = new ArrayList<>();
    busFactory.deployVehicle(vehicleList,
        1, new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    busFactory.deployVehicle(vehicleList,
        1, new Line(10001, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    busFactory.deployVehicle(vehicleList,
        1, new Line(10002, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    busFactory.deployVehicle(vehicleList,
        1, new Line(10003, "testLine", "BUS", testRouteOut, testRouteIn), 1.0);
    assertTrue(vehicleList.get(0) instanceof LargeBus);
    assertTrue(vehicleList.get(1) instanceof LargeBus);
    assertTrue(vehicleList.get(2) instanceof SmallBus);
    assertTrue(vehicleList.get(3) instanceof LargeBus);
  }
}
