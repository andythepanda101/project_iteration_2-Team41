package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrainFactoryTest {

  private TrainFactory trainFactory;

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

    trainFactory = new TrainFactory();
  }

  /**
   * Checks if the factory creates vehicles if strategy is set to Day.
   */
  @Test
  public void checkDayStrategy() {
    trainFactory.setTrainStrategy(new DayTrainStrategy());
    Train firstTrain = trainFactory.createVehicle(
        1, new Line(10000, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(firstTrain instanceof ElectricTrain);
    Train secondTrain = trainFactory.createVehicle(
        1, new Line(10001, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(secondTrain instanceof ElectricTrain);
    Train thirdTrain = trainFactory.createVehicle(
        1, new Line(10002, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(thirdTrain instanceof ElectricTrain);
    Train fourthTrain = trainFactory.createVehicle(
        1, new Line(10003, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(fourthTrain instanceof DieselTrain);
    Train fifthTrain = trainFactory.createVehicle(
        1, new Line(10004, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(fifthTrain instanceof ElectricTrain);
  }

  /**
   * Checks if the factory creates vehicles if strategy is set to Night.
   */
  @Test
  public void checkNightStrategy() {
    trainFactory.setTrainStrategy(new NightTrainStrategy());
    Train firstTrain = trainFactory.createVehicle(
        1, new Line(10000, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(firstTrain instanceof ElectricTrain);
    Train secondTrain = trainFactory.createVehicle(
        1, new Line(10001, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(secondTrain instanceof DieselTrain);
    Train thirdTrain = trainFactory.createVehicle(
        1, new Line(10002, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(thirdTrain instanceof ElectricTrain);
  }

  /**
   * Checks if the factory correctly deploys vehicles into a list.
   */
  @Test
  public void checkDeployVehicle() {
    trainFactory.setTrainStrategy(new NightTrainStrategy());
    List<Vehicle> vehicleList = new ArrayList<>();
    trainFactory.deployVehicle(vehicleList,
        1, new Line(10000, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    trainFactory.deployVehicle(vehicleList,
        1, new Line(10001, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    trainFactory.deployVehicle(vehicleList,
        1, new Line(10002, "testLine", "TRAIN", testRouteOut, testRouteIn), 1.0);
    assertTrue(vehicleList.get(0) instanceof ElectricTrain);
    assertTrue(vehicleList.get(1) instanceof DieselTrain);
    assertTrue(vehicleList.get(2) instanceof ElectricTrain);
  }
}
