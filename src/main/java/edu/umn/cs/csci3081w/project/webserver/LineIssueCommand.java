package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Line;
import java.util.List;
import java.util.ArrayList;

public class LineIssueCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  public LineIssueCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Updates the state of the simulation but accounting
   * for possible Line issues that may have arisen
   *
   * @param session current simulation session
   * @param command the update simulation command content
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    int lineWithIssueId = command.get("id").getAsInt();
    List<Line> simulatorLines = simulator.getLines();
    Line currentSimLine;
    for (int index = 0; index < simulatorLines.size(); index++) {
      currentSimLine = simulatorLines.get(index);
      if (currentSimLine.getId() == lineWithIssueId) {
        currentSimLine.setIssuesRemainingSteps(currentSimLine.getIssuesRemainingSteps() + 10);
      }
    }
  }
}
