package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public abstract class Train extends Vehicle {
  public static final String TRAIN_VEHICLE = "TRAIN_VEHICLE";
  public static final double SPEED = 1;
  public static final int CAPACITY = 120;

  /**
   * Constructor for a train.
   *
   * @param id       train identifier
   * @param line     route of in/out bound
   * @param capacity capacity of the train
   * @param speed    speed of the train
   */
  public Train(int id, Line line, int capacity, double speed) {
    super(id, line, capacity, speed, new PassengerLoader(), new PassengerUnloader());
  }

  /**
   * Report statistics for the train.
   *
   * @param out stream for printing
   */
  public void report(PrintStream out) {
    out.println("####Train Info Start####");
    super.report(out);
    out.println("####Train Info End####");
  }

  /**
   * co2 consumption for a train.
   *
   * @return The current co2 consumption value
   */
  public int getCurrentCO2Emission() {
    return (2 * getPassengers().size()) + 5;
  }
}
