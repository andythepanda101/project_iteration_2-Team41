package edu.umn.cs.csci3081w.project.model;

/**
 * An interface for the vehicle subject class. Acts as the subject in the
 * observer design pattern.
 */
public interface VehicleSubjectInterface {

  public void attach(Vehicle observer);

  public void detach(Vehicle observer);

  public void notifyObservers();
}
