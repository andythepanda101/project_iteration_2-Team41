package edu.umn.cs.csci3081w.project.model;

/**
 * A class that represents a Large Bus in the simulation.
 */
public class LargeBus extends Bus {
  public static final int CAPACITY = 80;

  /**
   * Constructor for a bus.
   *
   * @param id       bus identifier
   * @param line     route of in/out bound
   * @param speed    speed of bus
   */
  public LargeBus(int id, Line line, double speed) {
    super(id, line, CAPACITY, speed);
  }

  public int getCurrentCO2Emission() {
    return 3 + (getPassengers().size());
  }
}
