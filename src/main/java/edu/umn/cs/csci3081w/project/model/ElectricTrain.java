package edu.umn.cs.csci3081w.project.model;

public class ElectricTrain extends Train {

  public ElectricTrain(int id, Line line, double speed) {
    super(id, line, CAPACITY, speed);
  }

  public int getCurrentCO2Emission() { return 0;}
}
