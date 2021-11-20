package edu.umn.cs.csci3081w.project.model;

public class DieselTrain extends Train {

  public DieselTrain(int id, Line line, int capacity, double speed) {
    super(id, line, capacity, speed);
  }

  public int getCurrentCO2Emission() { return 6 + (getPassengers().size() * 2); }
}
