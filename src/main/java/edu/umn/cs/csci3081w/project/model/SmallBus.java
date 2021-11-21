package edu.umn.cs.csci3081w.project.model;

/**
 * A class that represents a Small Bus in the simulation.
 */
public class SmallBus extends Bus {
  public static final int CAPACITY = 20;

  /**
   * Constructor for a bus.
   *
   * @param id       bus identifier
   * @param line     route of in/out bound
   * @param speed    speed of bus
   */
  public SmallBus(int id, Line line, double speed) {
    super(id, line, CAPACITY, speed);
  }


  public int getCurrentCO2Emission() {
    return 1 + (getPassengers().size());
  }
}
