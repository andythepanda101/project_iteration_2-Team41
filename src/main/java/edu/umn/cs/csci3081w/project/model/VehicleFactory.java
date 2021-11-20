package edu.umn.cs.csci3081w.project.model;
import edu.umn.cs.csci3081w.project.model.*;

import java.util.List;

public abstract class VehicleFactory {
  public void deployVehicle(List<Vehicle> vList, int id, Line line, double speed) {

    Vehicle newVehicle = this.createVehicle(id, line, speed);
    vList.add(newVehicle);
  }
  
  public abstract Vehicle createVehicle(int id, Line line, double speed);
}
