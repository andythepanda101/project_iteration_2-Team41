package edu.umn.cs.csci3081w.project.model;

import java.util.List;

public class VehicleSubject implements VehicleSubjectInterface {
  public List<Vehicle> observers;

  public void attach(Vehicle newObserver) {
    observers.add(newObserver);
  }

  public void detach(Vehicle observer) {
    for (int i = 0; i < observers.size(); i++) {
      if (observers.get(i) == observer) {
        observers.remove(i);
        i--;
      }
    }
  }

  public void notifyObservers() {
    for (int i = 0; i < observers.size(); i++) {
      observers.get(i).updateEmissionsList();
    }
  }
}
