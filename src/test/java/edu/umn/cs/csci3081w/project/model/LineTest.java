package edu.umn.cs.csci3081w.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LineTest {

  /**
   * Setup operations before each test runs.
   */
  private Route testRouteOut;
  private Route testRouteIn;
  private Route simpleTestRouteIn;
  private Route simpleTestRouteOut;
  private Line testLine;

  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {

    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;

    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    Stop stop3 = new Stop(2, "test stop 3", new Position(-93.226632, 44.975392));
    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop1);
    stopsOut.add(stop2);
    stopsOut.add(stop3);
    List<Double> distancesOut = new ArrayList<Double>();
    distancesOut.add(0.9712663713083954);
    distancesOut.add(0.961379387775189);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(.15);
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.0);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);
    testRouteOut = new Route(10, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    List<Stop> stopsIn = new ArrayList<>();
    stopsIn.add(stop3);
    stopsIn.add(stop2);
    stopsIn.add(stop1);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.961379387775189);
    distancesIn.add(0.9712663713083954);
    List<Double> probabilitiesIn = new ArrayList<>();
    probabilitiesIn.add(.4);
    probabilitiesIn.add(0.3);
    probabilitiesIn.add(.0);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);
    testRouteIn = new Route(11, "testRouteIn",
        stopsIn, distancesIn, generatorIn);

    List<Stop> simpleStopsIn = new ArrayList<Stop>();
    Stop simpleStop1 = new Stop(0, "test stop simpin", new Position(-93.243774, 44.972392));
    simpleStopsIn.add(simpleStop1);
    List<Double> simpleDistancesIn = new ArrayList<>();
    simpleDistancesIn.add(0.961379387775189);
    List<Double> simpleProbabilitiesIn = new ArrayList<Double>();
    simpleProbabilitiesIn.add(.5);
    PassengerGenerator simpleGeneratorIn = new RandomPassengerGenerator(simpleStopsIn,
        simpleProbabilitiesIn);

    simpleTestRouteIn = new Route(0, "simpleTestRouteIn",
        simpleStopsIn, simpleDistancesIn, simpleGeneratorIn);

    List<Stop> simpleStopsOut = new ArrayList<Stop>();
    Stop simpleStop1Out = new Stop(1, "test stop simpout", new Position(-93.243774, 44.972392));
    simpleStopsOut.add(simpleStop1Out);
    List<Double> simpleDistancesOut = new ArrayList<>();
    simpleDistancesOut.add(0.961379387775189);
    List<Double> simpleProbabilitiesOut = new ArrayList<Double>();
    simpleProbabilitiesOut.add(.5);
    PassengerGenerator simpleGeneratorOut = new RandomPassengerGenerator(simpleStopsOut,
        simpleProbabilitiesOut);

    simpleTestRouteOut = new Route(1, "simpleTestRouteOut",
        simpleStopsOut, simpleDistancesOut, simpleGeneratorOut);

    testLine = new Line(2468, "testLine", "testType", testRouteOut, testRouteIn);
  }

  /**
   * Testing state after using constructor.
   */
  @Test
  public void testConstructor() {
    assertEquals(2468, testLine.getId());
    assertEquals("testLine",testLine.getName());
    assertEquals("testType",testLine.getType());
    assertEquals(testRouteOut,testLine.getOutboundRoute());
    assertEquals(testRouteIn,testLine.getInboundRoute());
  }

  /**
   * Testing report feature of a Line that prints details
   */
  @Test
  public void testReport() {
    try {

      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      testLine.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "====Line Info Start====" + System.lineSeparator()
              + "ID: 2468" + System.lineSeparator()
              + "Name: testLine" + System.lineSeparator()
              + "Type: testType" + System.lineSeparator()
              + "####Route Info Start####" + System.lineSeparator()
              + "ID: 10" + System.lineSeparator()
              + "Name: testRouteOut" + System.lineSeparator()
              + "Num stops: 3" + System.lineSeparator()
              + "****Stops Info Start****" + System.lineSeparator()
              + "++++Next Stop Info Start++++" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 0" + System.lineSeparator()
              + "Name: test stop 1" + System.lineSeparator()
              + "Position: 44.972392,-93.243774" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "++++Next Stop Info End++++" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 1" + System.lineSeparator()
              + "Name: test stop 2" + System.lineSeparator()
              + "Position: 44.97358,-93.235071"  + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 2" + System.lineSeparator()
              + "Name: test stop 3" + System.lineSeparator()
              + "Position: 44.975392,-93.226632" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "****Stops Info End****" + System.lineSeparator()
              + "####Route Info End####" + System.lineSeparator()
              + "####Route Info Start####" + System.lineSeparator()
              + "ID: 11" + System.lineSeparator()
              + "Name: testRouteIn" + System.lineSeparator()
              + "Num stops: 3" + System.lineSeparator()
              + "****Stops Info Start****" + System.lineSeparator()
              + "++++Next Stop Info Start++++" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 2"  + System.lineSeparator()
              + "Name: test stop 3" + System.lineSeparator()
              + "Position: 44.975392,-93.226632" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "++++Next Stop Info End++++" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 1" + System.lineSeparator()
              + "Name: test stop 2" + System.lineSeparator()
              +  "Position: 44.97358,-93.235071" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              +  "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "####Stop Info Start####"  + System.lineSeparator()
              + "ID: 0" + System.lineSeparator()
              + "Name: test stop 1" + System.lineSeparator()
              + "Position: 44.972392,-93.243774" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "****Stops Info End****" + System.lineSeparator()
              + "####Route Info End####" + System.lineSeparator()
              + "====Line Info End====" + System.lineSeparator();
      assertEquals(data, strToCompare);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Tests the copy method of a Line to create a copy of itself
   * Uses .report() as this is tested previously to be correct
   * and I know of no better way to test this
   */
  @Test
  public void testShallowCopy() {
    try {

      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      testLine.shallowCopy().report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "====Line Info Start====" + System.lineSeparator()
              + "ID: 2468" + System.lineSeparator()
              + "Name: testLine" + System.lineSeparator()
              + "Type: testType" + System.lineSeparator()
              + "####Route Info Start####" + System.lineSeparator()
              + "ID: 10" + System.lineSeparator()
              + "Name: testRouteOut" + System.lineSeparator()
              + "Num stops: 3" + System.lineSeparator()
              + "****Stops Info Start****" + System.lineSeparator()
              + "++++Next Stop Info Start++++" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 0" + System.lineSeparator()
              + "Name: test stop 1" + System.lineSeparator()
              + "Position: 44.972392,-93.243774" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "++++Next Stop Info End++++" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 1" + System.lineSeparator()
              + "Name: test stop 2" + System.lineSeparator()
              + "Position: 44.97358,-93.235071"  + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 2" + System.lineSeparator()
              + "Name: test stop 3" + System.lineSeparator()
              + "Position: 44.975392,-93.226632" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "****Stops Info End****" + System.lineSeparator()
              + "####Route Info End####" + System.lineSeparator()
              + "####Route Info Start####" + System.lineSeparator()
              + "ID: 11" + System.lineSeparator()
              + "Name: testRouteIn" + System.lineSeparator()
              + "Num stops: 3" + System.lineSeparator()
              + "****Stops Info Start****" + System.lineSeparator()
              + "++++Next Stop Info Start++++" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 2"  + System.lineSeparator()
              + "Name: test stop 3" + System.lineSeparator()
              + "Position: 44.975392,-93.226632" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "++++Next Stop Info End++++" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 1" + System.lineSeparator()
              + "Name: test stop 2" + System.lineSeparator()
              +  "Position: 44.97358,-93.235071" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              +  "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "####Stop Info Start####"  + System.lineSeparator()
              + "ID: 0" + System.lineSeparator()
              + "Name: test stop 1" + System.lineSeparator()
              + "Position: 44.972392,-93.243774" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "****Stops Info End****" + System.lineSeparator()
              + "####Route Info End####" + System.lineSeparator()
              + "====Line Info End====" + System.lineSeparator();
      assertEquals(data, strToCompare);
    } catch (IOException ioe) {
      fail();
    }

  }

}
