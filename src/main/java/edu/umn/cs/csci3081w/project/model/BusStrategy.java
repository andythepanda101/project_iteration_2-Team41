package edu.umn.cs.csci3081w.project.model;

public interface BusStrategy {
  Bus createBus(int id, Line line, double speed);

  void decrementCount();
}
