package edu.umn.cs.csci3081w.project.model;

/**
 * An interface for bus strategies.
 */
public interface BusStrategy {

  /**
   * A method that creates a bus based on the strategy.
   * @param id       bus identifier
   * @param line     a wrapper class that has routes
   * @param speed    speed of bus
   * @return  created bus
   */
  Bus createBus(int id, Line line, double speed);

  /**
   * Decrements the number of buses by 1.
   */
  void decrementCount();
}
