package edu.umn.cs.csci3081w.project.model;

public class LargeBus extends Bus {
  public static final int CAPACITY = 80;

  public LargeBus(int id, Line line, double speed) {
    super(id, line, CAPACITY, speed);
  }

  public int getCurrentCO2Emission() { return 3 + (getPassengers().size()); }
}
