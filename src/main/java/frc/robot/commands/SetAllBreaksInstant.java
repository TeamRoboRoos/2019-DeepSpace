/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class SetAllBreaksInstant extends InstantCommand {
  /**
   * Add your docs here.
   */
  public boolean enableBreaks = true, toggle;

  public SetAllBreaksInstant(boolean enableBreaks) {
    super();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.setRunWhenDisabled(true);
    this.enableBreaks = enableBreaks;
    this.toggle = false;
  }

  public SetAllBreaksInstant() {
    super();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.setRunWhenDisabled(true);
    this.toggle = true;
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    if(toggle) {
      boolean status = !Robot.m_telemetry.getBreaksStatus();
      doBreaks(status);
    } else {
      doBreaks(enableBreaks);
    }
  }

  private void doBreaks(boolean status) {
    Robot.m_telemetry.setBreaksStatus(status);

    Robot.m_driveBase.setBreaks(status);
    Robot.m_climber.setBreaks(status);
    Robot.m_elevator.setBreaks(status);
    Robot.m_arm.setBreaks(status);
  }
}
