package edu.umn.cs.csci3081w.project.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to update the CO2 emissions values of vehicles. Acts as the
 * subject in the observer design pattern.
 */
public class VehicleSubject implements VehicleSubjectInterface {
  public List<Vehicle> observers = new ArrayList<Vehicle>();

  /**
   * Attaches a vehicle as an observer.
   *
   * @param newObserver observer to be added to this Subject
   */
  public void attach(Vehicle newObserver) {
    observers.add(newObserver);
  }

  /**
   * Detaches the given observer from this Subject class.
   *
   * @param observer This vehicle's recent CO2 values can be updated from this subject class
   */
  public void detach(Vehicle observer) {
    for (int i = 0; i < observers.size(); i++) {
      if (observers.get(i).equals(observer)) {
        observers.remove(i);
        i--;
      }
    }
  }

  /**
   * Updates the list of recent CO2 emission values of every observer.
   * The oldest emission value is removed and a new one is added to the observer's list.
   */
  public void notifyObservers() {
    for (int i = 0; i < observers.size(); i++) {
      observers.get(i).updateEmissionsList();
    }
  }
}
