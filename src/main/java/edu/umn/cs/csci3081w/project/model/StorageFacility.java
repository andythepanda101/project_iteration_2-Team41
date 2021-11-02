package edu.umn.cs.csci3081w.project.model;

public class StorageFacility {
  private int busesNum;
  private int trainsNum;

  public StorageFacility() {
    busesNum = 0;
    trainsNum = 0;
  }

  public StorageFacility(int busesNum, int trainsNum) {
    this.busesNum = busesNum;
    this.trainsNum = trainsNum;
  }

  public int getBusesNum() {
    return busesNum;
  }

  public int getTrainsNum() {
    return trainsNum;
  }

  public void setBusesNum(int busesNum) {
    this.busesNum = busesNum;
  }

  public void setTrainsNum(int trainsNum) {
    this.trainsNum = trainsNum;
  }

  public void decrementBusesNum() {
    busesNum--;
  }

  public void decrementTrainsNum() {
    trainsNum--;
  }

  public void incrementBusesNum() {
    busesNum++;
  }

  public void incrementTrainsNum() {
    trainsNum++;
  }
}
