package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.*;

import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

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
    if (this.storageFacility == null) {
      this.storageFacility = new StorageFacility(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
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
        Route outbound = lines.get(i).getOutboundRoute();
        Route inbound = lines.get(i).getInboundRoute();
        Line line = lines.get(i);
        if (line.getType().equals(Line.BUS_LINE)) {
          // Setting Bus Strategy
          if (!(busFact.getStrategy() instanceof DayBusStrategy) && (time.getHour() >= 8) && (time.getHour() < 16)) {
            busFact.setBusStrategy(dayBus);
            nightBus.resetCount();
          } else if (!(busFact.getStrategy() instanceof NightBusStrategy) && ((time.getHour() < 8) || (time.getHour() >= 16))) {
            busFact.setBusStrategy(nightBus);
            dayBus.resetCount();
          }
          Bus newBus = busFact.createVehicle(counter.getBusIdCounterAndIncrement(), line.shallowCopy(), Bus.SPEED);
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
          if (!(trainFact.getStrategy() instanceof DayBusStrategy) && (time.getHour() >= 8) && (time.getHour() < 16) ) {
            trainFact.setTrainStrategy(dayTrain);
            nightTrain.resetCount();
          } else if (!(trainFact.getStrategy() instanceof NightBusStrategy) && ((time.getHour() < 8) || (time.getHour() >= 16)) ) {
            trainFact.setTrainStrategy(nightTrain);
            dayTrain.resetCount();
          }
          Train newTrain = trainFact.createVehicle(counter.getTrainIdCounterAndIncrement(), line.shallowCopy(), Train.SPEED);
          if (newTrain instanceof ElectricTrain && this.storageFacility.getElectricTrainsNum() > 0) {
            activeVehicles.add(newTrain);
            this.storageFacility.decrementElectricTrainsNum();
          } else if (newTrain instanceof DieselTrain && this.storageFacility.getDieselTrainsNum() > 0) {
            activeVehicles.add(newTrain);
            this.storageFacility.decrementDieselTrainsNum();
          } else {
            counter.trainIdCounter--;
            trainFact.getStrategy().decrementCount();
          }
          timeSinceLastVehicle.set(i, vehicleStartTimings.get(i));
          timeSinceLastVehicle.set(i, timeSinceLastVehicle.get(i) - 1);
        }
      } else {
        timeSinceLastVehicle.set(i, timeSinceLastVehicle.get(i) - 1);
      }
    }
    // update vehicles
    for (int i = activeVehicles.size() - 1; i >= 0; i--) {
      Vehicle currVehicle = activeVehicles.get(i);
      currVehicle.update();
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
  }

  public List<Vehicle> getActiveVehicles() {
    return activeVehicles;
  }

  public List<Line> getLines() {
    return lines;
  }
}
