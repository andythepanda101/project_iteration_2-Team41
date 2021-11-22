package edu.umn.cs.csci3081w.project.model;

import java.util.List;

/**
 * An abstract class sets up vehicle creation.
 */
public abstract class VehicleFactory {

  /**
   * Deploys vehicle into the list of vehicles.
   * @param vlist list of vehicles
   * @param id  vehicle identifier
   * @param line route of in/out bound
   * @param speed speed of vehicle
   */
  public void deployVehicle(List<Vehicle> vlist, int id, Line line, double speed) {
    Vehicle newVehicle = this.createVehicle(id, line, speed);
    vlist.add(newVehicle);
  }

  /**
   * An abstract method that would create a specified vehicle.
   * @param id  vehicle identifier
   * @param line route of in/out bound
   * @param speed speed of vehicle
   * @return created vehicle
   */
  public abstract Vehicle createVehicle(int id, Line line, double speed);
}
