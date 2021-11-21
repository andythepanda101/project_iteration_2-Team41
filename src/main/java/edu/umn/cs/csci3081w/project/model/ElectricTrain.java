package edu.umn.cs.csci3081w.project.model;

public class ElectricTrain extends Train {

  public ElectricTrain(int id, Line line, int capacity, double speed) {
    super(id, line, capacity, speed);
  }

  public int getCurrentCO2Emission() { return 0;}
}
