package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public abstract class Bus extends Vehicle {
  public static final String BUS_VEHICLE = "BUS_VEHICLE";
  public static final double SPEED = 0.5;
  public static final int CAPACITY = 60;

  /**
   * Constructor for a bus.
   *
   * @param id       bus identifier
   * @param line     route of in/out bound
   * @param capacity capacity of bus
   * @param speed    speed of bus
   */
  public Bus(int id, Line line, int capacity, double speed) {
    super(id, line, capacity, speed, new PassengerLoader(), new PassengerUnloader());
  }

  /**
   * Report statistics for the bus.
   *
   * @param out stream for printing
   */
  public void report(PrintStream out) {
    out.println("####Bus Info Start####");
    super.report(out);
    out.println("####Bus Info End####");
  }

  /**
   * co2 consumption for a bus.
   *
   * @return The current co2 consumption value
  */
  public int getCurrentCO2Emission() {
    return (1 * getPassengers().size()) + 2;
  }
}
