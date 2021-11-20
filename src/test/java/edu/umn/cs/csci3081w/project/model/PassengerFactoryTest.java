package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassengerFactoryTest {

  PassengerFactory testPassengerFactory;
  Passenger testPassenger;

  /**
   * Setup creates a test PassengerFactory to test its methods.
   */
  @BeforeEach
  public void setup() {
    testPassengerFactory = new PassengerFactory();
    testPassenger = new Passenger(13, "Goldy");
    testPassengerFactory.DETERMINISTIC = true;
    testPassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    testPassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
  }

  /**
   * Deterministic testing of the nameGeneration() method of
   * PassengerFactory to see if it works as expected.
   * PassengerFactory needs to have DETERMINISTIC = true to properly
   * run these test cases.
   */
  @Test
  public void testNameGeneration() {
    assertEquals("Goldy", testPassengerFactory.nameGeneration());
    assertEquals("President", testPassengerFactory.nameGeneration());
    assertEquals("Coach", testPassengerFactory.nameGeneration());
    assertEquals("Goldy", testPassengerFactory.nameGeneration());
    assertEquals("President", testPassengerFactory.nameGeneration());
    assertEquals("Coach", testPassengerFactory.nameGeneration());
  }

  /**
   * Assuming that the currStop and lastStop will not be the same.
   * Deterministic testing of the generation of passengers to see if it
   * works as expected, PassengerFactory needs to have DETERMINISTIC = true to properly
   * run these test cases.
   */
  @Test
  public void testGenerate() {
    // generate first test passenger
    Passenger generatedPassenger1 = testPassengerFactory.generate(11, 9);
    generatedPassenger1.pasUpdate();
    generatedPassenger1.pasUpdate();

    try {
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      generatedPassenger1.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "####Passenger Info Start####" + System.lineSeparator()
              + "Name: Goldy" + System.lineSeparator()
              + "Destination: 13" + System.lineSeparator()
              + "Wait at stop: 2" + System.lineSeparator()
              + "Time on vehicle: 0" + System.lineSeparator()
              + "####Passenger Info End####" + System.lineSeparator();
      assertEquals(data, strToCompare);
    } catch (IOException ioe) {
      fail();
    }

    // generate second test passenger
    Passenger generatedPassenger2 = testPassengerFactory.generate(14, 18);
    generatedPassenger2.pasUpdate();
    generatedPassenger2.pasUpdate();
    generatedPassenger2.pasUpdate();

    try {
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      generatedPassenger2.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "####Passenger Info Start####" + System.lineSeparator()
              + "Name: President" + System.lineSeparator()
              + "Destination: 17" + System.lineSeparator()
              + "Wait at stop: 3" + System.lineSeparator()
              + "Time on vehicle: 0" + System.lineSeparator()
              + "####Passenger Info End####" + System.lineSeparator();
      assertEquals(data, strToCompare);
    } catch (IOException ioe) {
      fail();
    }

  }


}
