package edu.umn.cs.csci3081w.project.model;

public class StorageFacility {
  private int smallBusesNum;
  private int largeBusesNum;
  private int trainsNum;

  public StorageFacility() {
    smallBusesNum = 0;
    largeBusesNum = 0;
    trainsNum = 0;
  }

  public StorageFacility(int smallBusesNum, int largeBusesNum, int trainsNum) {
    this.smallBusesNum = smallBusesNum;
    this.largeBusesNum = largeBusesNum;
    this.trainsNum = trainsNum;
  }

  public int getSmallBusesNum() {
    return smallBusesNum;
  }

  public int getLargeBusesNum() { return largeBusesNum; }

  public int getTrainsNum() {
    return trainsNum;
  }

  public void setSmallBusesNum(int busesNum) {
    this.smallBusesNum = busesNum;
  }

  public void setLargeBusesNum(int busesNum) { this.largeBusesNum = busesNum; }

  public void setTrainsNum(int trainsNum) {
    this.trainsNum = trainsNum;
  }

  public void decrementSmallBusesNum() {
    smallBusesNum--;
  }

  public void decrementLargeBusesNum() { largeBusesNum--; }

  public void decrementTrainsNum() {
    trainsNum--;
  }

  public void incrementSmallBusesNum() {
    smallBusesNum++;
  }

  public void incrementLargeBusesNum() { largeBusesNum++; }

  public void incrementTrainsNum() {
    trainsNum++;
  }
}
