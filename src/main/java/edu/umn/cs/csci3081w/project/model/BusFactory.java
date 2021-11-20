package edu.umn.cs.csci3081w.project.model;

public class BusFactory extends VehicleFactory {
  private BusStrategy busType;

  public void setBusType(BusStrategy description) {
    busType = description;
  }

  public Bus createVehicle(int id, Line line, double speed) {
    Bus newBus = busType.createBus(id, line, speed);
    return newBus;
  }
}
