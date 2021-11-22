package edu.umn.cs.csci3081w.project.webserver;

import edu.umn.cs.csci3081w.project.model.Bus;
import edu.umn.cs.csci3081w.project.model.BusFactory;
import edu.umn.cs.csci3081w.project.model.Counter;
import edu.umn.cs.csci3081w.project.model.DayBusStrategy;
import edu.umn.cs.csci3081w.project.model.DayTrainStrategy;
import edu.umn.cs.csci3081w.project.model.DieselTrain;
import edu.umn.cs.csci3081w.project.model.ElectricTrain;
import edu.umn.cs.csci3081w.project.model.LargeBus;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.NightBusStrategy;
import edu.umn.cs.csci3081w.project.model.NightTrainStrategy;
import edu.umn.cs.csci3081w.project.model.Route;
import edu.umn.cs.csci3081w.project.model.SmallBus;
import edu.umn.cs.csci3081w.project.model.StorageFacility;
import edu.umn.cs.csci3081w.project.model.Train;
import edu.umn.cs.csci3081w.project.model.TrainFactory;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for VTS, responsible for running the simulation.
 */
public class VisualTransitSimulator {

  private static boolean LOGGING = false;
  private int numTimeSteps = 0;
  private int simulationTimeElapsed = 0;
  private Counter counter;
  private List<Line> lines;
  private List<Vehicle> activeVehicles;
  private List<Vehicle> completedTripVehicles;
  private List<Integer> vehicleStartTimings;
  private List<Integer> timeSinceLastVehicle;
  private StorageFacility storageFacility;
  private WebServerSession webServerSession;
  private BusFactory busFact = new BusFactory();
  private LocalDateTime time = LocalDateTime.now();
  private DayBusStrategy dayBus = new DayBusStrategy();
  private NightBusStrategy nightBus = new NightBusStrategy();
  private TrainFactory trainFact = new TrainFactory();
  private DayTrainStrategy dayTrain = new DayTrainStrategy();
  private NightTrainStrategy nightTrain = new NightTrainStrategy();

  /**
   * Constructor for Simulation.
   *
   * @param configFile file containing the simulation configuration
   * @param webServerSession session associated with the simulation
   */
  public VisualTransitSimulator(String configFile, WebServerSession webServerSession) {
    this.webServerSession = webServerSession;
    this.counter = new Counter();
    ConfigManager configManager = new ConfigManager();
    configManager.readConfig(counter, configFile);
    this.lines = configManager.getLines();
    this.activeVehicles = new ArrayList<Vehicle>();
    this.completedTripVehicles = new ArrayList<Vehicle>();
    this.vehicleStartTimings = new ArrayList<Integer>();
    this.timeSinceLastVehicle = new ArrayList<Integer>();
    this.storageFacility = configManager.getStorageFacility();
    //this.lineIdsWithIssues = new ArrayList<String>();
    //this.lineIdsWithIssuesTimer = new ArrayList<Integer>();
    if (this.storageFacility == null) {
      this.storageFacility = new StorageFacility(
          Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    if (VisualTransitSimulator.LOGGING) {
      System.out.println("////Simulation Routes////");
      for (int i = 0; i < lines.size(); i++) {
        lines.get(i).report(System.out);
      }
    }
  }

  /**
   * Starts the simulation.
   *
   * @param vehicleStartTimings start timings of bus
   * @param numTimeSteps        number of time steps
   */
  public void start(List<Integer> vehicleStartTimings, int numTimeSteps) {
    this.vehicleStartTimings = vehicleStartTimings;
    this.numTimeSteps = numTimeSteps;
    for (int i = 0; i < vehicleStartTimings.size(); i++) {
      this.timeSinceLastVehicle.add(i, 0);
    }
    simulationTimeElapsed = 0;
  }

  /**
   * Updates the simulation at each step.
   */
  public void update() {
    simulationTimeElapsed++;
    if (simulationTimeElapsed > numTimeSteps) {
      return;
    }
    System.out.println("~~~~The simulation time is now at time step "
        + simulationTimeElapsed + "~~~~");
    // generate vehicles
    for (int i = 0; i < timeSinceLastVehicle.size(); i++) {
      if (timeSinceLastVehicle.get(i) <= 0) {
        Line line = lines.get(i);
        if (line.getIssuesRemainingSteps() <= 0) {
          if (line.getType().equals(Line.BUS_LINE)) {
            // Setting Bus Strategy
            if (!(busFact.getStrategy() instanceof DayBusStrategy)
                && (time.getHour() >= 8) && (time.getHour() < 16)) {
              busFact.setBusStrategy(dayBus);
              nightBus.resetCount();
            } else if (!(busFact.getStrategy() instanceof NightBusStrategy)
                && ((time.getHour() < 8) || (time.getHour() >= 16))) {
              busFact.setBusStrategy(nightBus);
              dayBus.resetCount();
            }
            Bus newBus = busFact.createVehicle(
                counter.getBusIdCounterAndIncrement(), line.shallowCopy(), Bus.SPEED);
            if (newBus instanceof SmallBus && this.storageFacility.getSmallBusesNum() > 0) {
              activeVehicles.add(newBus);
              this.storageFacility.decrementSmallBusesNum();
            } else if (newBus instanceof LargeBus && this.storageFacility.getLargeBusesNum() > 0) {
              activeVehicles.add(newBus);
              this.storageFacility.decrementLargeBusesNum();
            } else {
              counter.busIdCounter--;
              busFact.getStrategy().decrementCount();
            }
            timeSinceLastVehicle.set(i, vehicleStartTimings.get(i));
            timeSinceLastVehicle.set(i, timeSinceLastVehicle.get(i) - 1);
          } else if (line.getType().equals(Line.TRAIN_LINE)) {
            // Setting Train Strategy
            if (!(trainFact.getStrategy() instanceof DayBusStrategy)
                && (time.getHour() >= 8) && (time.getHour() < 16)) {
              trainFact.setTrainStrategy(dayTrain);
              nightTrain.resetCount();
            } else if (!(trainFact.getStrategy() instanceof NightBusStrategy)
                && ((time.getHour() < 8) || (time.getHour() >= 16))) {
              trainFact.setTrainStrategy(nightTrain);
              dayTrain.resetCount();
            }
            Train newTrain =
                trainFact.createVehicle(
                    counter.getTrainIdCounterAndIncrement(), line.shallowCopy(), Train.SPEED);
            if (newTrain instanceof ElectricTrain
                && this.storageFacility.getElectricTrainsNum() > 0) {
              activeVehicles.add(newTrain);
              this.storageFacility.decrementElectricTrainsNum();
            } else if (newTrain instanceof DieselTrain
                && this.storageFacility.getDieselTrainsNum() > 0) {
              activeVehicles.add(newTrain);
              this.storageFacility.decrementDieselTrainsNum();
            } else {
              counter.trainIdCounter--;
              trainFact.getStrategy().decrementCount();
            }
            timeSinceLastVehicle.set(i, vehicleStartTimings.get(i));
            timeSinceLastVehicle.set(i, timeSinceLastVehicle.get(i) - 1);
          }
        }
      } else {
        timeSinceLastVehicle.set(i, timeSinceLastVehicle.get(i) - 1);
      }
    }
    // update vehicles
    for (int i = activeVehicles.size() - 1; i >= 0; i--) {
      Vehicle currVehicle = activeVehicles.get(i);
      currVehicle.getLast5CO2().add(0, currVehicle.getCurrentCO2Emission());
      currVehicle.getLast5CO2().remove(currVehicle.getLast5CO2().size());
      // begin feature 5
      boolean currVehicleHasIssue = false;
      for (int index = 0; index < lines.size(); index++) { // loop through each line
        Line currLineLoop = lines.get(index);
        // if currVehicle is on the same Line that we're currently
        // on in the loop, AND that Line has issue
        if (currVehicle.getLine().getId() == currLineLoop.getId() && currLineLoop.getIssuesRemainingSteps() > 0) {
          // if vehicle is this line do nothing
          currVehicleHasIssue = true;
          break;
        }
      }
      if (!currVehicleHasIssue) {
        currVehicle.update();
      }
      // end feature 5
      if (currVehicle.isTripComplete()) {
        Vehicle completedTripVehicle = activeVehicles.remove(i);
        completedTripVehicles.add(completedTripVehicle);
        if (completedTripVehicle instanceof SmallBus) {
          this.storageFacility.incrementSmallBusesNum();
        } else if (completedTripVehicle instanceof LargeBus) {
          this.storageFacility.incrementLargeBusesNum();
        } else if (completedTripVehicle instanceof ElectricTrain) {
          this.storageFacility.incrementElectricTrainsNum();
        } else if (completedTripVehicle instanceof DieselTrain) {
          this.storageFacility.incrementDieselTrainsNum();
        }
      } else {
        if (VisualTransitSimulator.LOGGING) {
          currVehicle.report(System.out);
        }
      }
    }
    // update routes
    for (int i = 0; i < lines.size(); i++) {
      Route currRoute = lines.get(i).getOutboundRoute();
      currRoute.update();
      currRoute = lines.get(i).getInboundRoute();
      currRoute.update();
      if (VisualTransitSimulator.LOGGING) {
        currRoute.report(System.out);
      }
    }
    // decrement issue counter at end of this time step (feature 5)
    for (int index = 0; index < lines.size(); index++) { // loop through each line
      Line currLineLoop = lines.get(index);
      if (currLineLoop.getIssuesRemainingSteps() > 0) {
        currLineLoop.setIssuesRemainingSteps(currLineLoop.getIssuesRemainingSteps() - 1);
      }
    }
  }

  public List<Vehicle> getActiveVehicles() {
    return activeVehicles;
  }

  public List<Line> getLines() { return lines; }

}
