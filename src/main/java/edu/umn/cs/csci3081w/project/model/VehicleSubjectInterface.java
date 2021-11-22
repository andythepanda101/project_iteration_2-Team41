package edu.umn.cs.csci3081w.project.model;

public interface VehicleSubjectInterface {

  public void attach(Vehicle observer);

  public void detach(Vehicle observer);

  public void notifyObservers();
}
