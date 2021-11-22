package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Bus;
import edu.umn.cs.csci3081w.project.model.Counter;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.Route;
import edu.umn.cs.csci3081w.project.model.StorageFacility;
import edu.umn.cs.csci3081w.project.model.Train;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import java.util.ArrayList;
import java.util.List;

public class VisualTransitSimulator {

  private static boolean LOGGING = false;
  private int numTimeSteps = 0;
  private int simulationTimeElapsed = 0;
  private Counter counter;
  private List<Line> lines;
  private List<Route> routes;
  private List<Vehicle> activeVehicles;
  private List<Vehicle> completedTripVehicles;
  private List<Integer> vehicleStartTimings;
  private List<Integer> timeSinceLastVehicle;
  private StorageFacility storageFacility;
  private WebServerSession webServerSession;
  // Feature 5 additions (rm) comment
  //private List<String> lineIdsWithIssues;
  // counter for remaining time steps frozen
  //private List<Integer> lineIdsWithIssuesTimer;

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
    this.routes = configManager.getRoutes();
    this.activeVehicles = new ArrayList<Vehicle>();
    this.completedTripVehicles = new ArrayList<Vehicle>();
    this.vehicleStartTimings = new ArrayList<Integer>();
    this.timeSinceLastVehicle = new ArrayList<Integer>();
    this.storageFacility = configManager.getStorageFacility();
    //this.lineIdsWithIssues = new ArrayList<String>();
    //this.lineIdsWithIssuesTimer = new ArrayList<Integer>();
    if (this.storageFacility == null) {
      this.storageFacility = new StorageFacility(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    if (VisualTransitSimulator.LOGGING) {
      System.out.println("////Simulation Routes////");
      for (int i = 0; i < routes.size(); i++) {
        routes.get(i).report(System.out);
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
        Route outbound = routes.get(2 * i);
        Route inbound = routes.get(2 * i + 1);
        Line line = findLineBasedOnRoute(outbound);
        System.out.println("in gen vehicle loop, line " + line.getId() + "has steps " + line.getIssuesRemainingSteps());
        // feature 5, added wrapper if(line.getIssuesRemainingSteps() <= 0)
        if (line.getIssuesRemainingSteps() <= 0) {
          if (line.getType().equals(Line.BUS_LINE)) {
            if (storageFacility.getBusesNum() > 0) {
              activeVehicles
                  .add(new Bus(counter.getBusIdCounterAndIncrement(), line.shallowCopy(),
                      Bus.CAPACITY, Bus.SPEED));
              this.storageFacility.decrementBusesNum();
            }
            timeSinceLastVehicle.set(i, vehicleStartTimings.get(i));
            timeSinceLastVehicle.set(i, timeSinceLastVehicle.get(i) - 1);
          } else if (line.getType().equals(Line.TRAIN_LINE)) {
            if (storageFacility.getTrainsNum() > 0) {
              activeVehicles
                  .add(new Train(counter.getTrainIdCounterAndIncrement(), line.shallowCopy(),
                      Train.CAPACITY, Train.SPEED));
              this.storageFacility.decrementTrainsNum();
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
    for (int i = activeVehicles.size() - 1; i >= 0; i--) { // loop through each vehicle $
      Vehicle currVehicle = activeVehicles.get(i);
      // begin feature 5
      boolean currVehicleHasIssue = false;
      for (int index = 0; index < lines.size(); index++) { // loop through each line
        Line currLineLoop = lines.get(index);
        // if currVehicle is on the same Line that we're currently
        // on in the loop, AND that Line has issue
        if (currVehicle.getLine().getId() == currLineLoop.getId() && currLineLoop.getIssuesRemainingSteps() > 0) {
          // if vehicle is this line do nothing
          // System.out.println(currVehicle.getId() + " not moving cuz line " + currLineLoop.getId() + " has steps: " + currLineLoop.getIssuesRemainingSteps());
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
        if (completedTripVehicle instanceof Bus) {
          this.storageFacility.incrementBusesNum();
        } else if (completedTripVehicle instanceof Train) {
          this.storageFacility.incrementTrainsNum();
        }
      } else {
        if (VisualTransitSimulator.LOGGING) {
          currVehicle.report(System.out);
        }
      }
    }
    // update routes
    for (int i = 0; i < routes.size(); i++) {
      Route currRoute = routes.get(i);
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

  /**
   * Method to find the line of a route.
   *
   * @param route route to search
   * @return Line containing route
   */
  public Line findLineBasedOnRoute(Route route) {
    for (Line line : lines) {
      if (line.getOutboundRoute().getId() == route.getId()
          || line.getInboundRoute().getId() == route.getId()) {
        return line;
      }
    }
    throw new RuntimeException("Could not find the line of the route");
  }

  public List<Route> getRoutes() {
    return routes;
  }

  public List<Vehicle> getActiveVehicles() {
    return activeVehicles;
  }

  public List<Line> getLines() { return lines; }

}
