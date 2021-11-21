package edu.umn.cs.csci3081w.project.model;

public class StorageFacility {
  private int smallBusesNum;
  private int largeBusesNum;
  private int electricTrainsNum;
  private int dieselTrainsNum;

  public StorageFacility() {
    smallBusesNum = 0;
    largeBusesNum = 0;
    electricTrainsNum = 0;
    dieselTrainsNum = 0;
  }

  public StorageFacility(int smallBusesNum, int largeBusesNum, int electricTrainsNum, int dieselTrainsNum) {
    this.smallBusesNum = smallBusesNum;
    this.largeBusesNum = largeBusesNum;
    this.electricTrainsNum = electricTrainsNum;
    this.dieselTrainsNum = dieselTrainsNum;
  }

  public int getSmallBusesNum() {
    return smallBusesNum;
  }

  public int getLargeBusesNum() { return largeBusesNum; }

  public int getElectricTrainsNum() {
    return electricTrainsNum;
  }

  public int getDieselTrainsNum() { return dieselTrainsNum; }

  public void setSmallBusesNum(int busesNum) {
    this.smallBusesNum = busesNum;
  }

  public void setLargeBusesNum(int busesNum) { this.largeBusesNum = busesNum; }

  public void setElectricTrainsNum(int trainsNum) {
    this.electricTrainsNum = trainsNum;
  }

  public void setDieselTrainsNum(int trainsNum) { this.dieselTrainsNum = trainsNum; }

  public void decrementSmallBusesNum() {
    smallBusesNum--;
  }

  public void decrementLargeBusesNum() { largeBusesNum--; }

  public void decrementElectricTrainsNum() {
    electricTrainsNum--;
  }

  public void decrementDieselTrainsNum() { dieselTrainsNum--; }

  public void incrementSmallBusesNum() {
    smallBusesNum++;
  }

  public void incrementLargeBusesNum() { largeBusesNum++; }

  public void incrementElectricTrainsNum() {
    electricTrainsNum++;
  }

  public void incrementDieselTrainsNum() { dieselTrainsNum++; }
}
