package edu.umn.cs.csci3081w.project.model;

/**
 * A class that represents an Electric Train in the simulation.
 */
public class ElectricTrain extends Train {

  /**
   * Constructor for a train.
   *
   * @param id       train identifier
   * @param line     route of in/out bound
   * @param speed    speed of the train
   */
  public ElectricTrain(int id, Line line, double speed) {
    super(id, line, CAPACITY, speed);
  }

  public int getCurrentCO2Emission() {
    return 0;
  }
}
