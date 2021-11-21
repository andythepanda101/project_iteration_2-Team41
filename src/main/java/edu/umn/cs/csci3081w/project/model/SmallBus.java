package edu.umn.cs.csci3081w.project.model;

public class SmallBus extends Bus {
  public static final int CAPACITY = 20;

  public SmallBus(int id, Line line, double speed) {
    super(id, line, CAPACITY, speed);
  }


  public int getCurrentCO2Emission() { return 1 + (getPassengers().size()); }
}
