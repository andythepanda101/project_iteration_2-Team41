package edu.umn.cs.csci3081w.project.model;

public interface TrainStrategy {
  Train createTrain(int id, Line line, double speed);

  void decrementCount();
}
