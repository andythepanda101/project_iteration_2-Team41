package edu.umn.cs.csci3081w.project.model;

/**
 * An interface for train strategies.
 */
public interface TrainStrategy {

  /**
   * A method that creates a train based on the strategy.
   * @param id       train identifier
   * @param line     a wrapper class that has routes
   * @param speed    speed of train
   * @return  created train
   */
  Train createTrain(int id, Line line, double speed);

  /**
   * Decrements the number of trains by 1.
   */
  void decrementCount();
}
