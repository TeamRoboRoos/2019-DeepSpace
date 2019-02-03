/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RunMotors extends Command {
  private int state = 0;
  private double driveSpeed = 0.25;
  private long time;
  private int waitRun = 14000, waitStop = 1000;

  public RunMotors() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_driveBase);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    time = System.currentTimeMillis();
    System.out.println("init");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println("state: " + state);
    switch(state) {
      case 0:
          Robot.m_driveBase.drive(0, driveSpeed, 0);
          if(time + waitRun <= System.currentTimeMillis()) {
            time = System.currentTimeMillis();
            state++;
          }
        break;
      case 1:
        Robot.m_driveBase.drive(0, 0, 0);
        if(time + waitStop <= System.currentTimeMillis()) {
          time = System.currentTimeMillis();
          state++;
        }
        break;
      case 2:
        Robot.m_driveBase.drive(0, -driveSpeed, 0);
        if(time + waitRun <= System.currentTimeMillis()) {
          time = System.currentTimeMillis();
          state++;
        }
        break;
      case 3:
        Robot.m_driveBase.drive(0, 0, 0);
        if(time + waitStop <= System.currentTimeMillis()) {
          time = System.currentTimeMillis();
          state++;
        }
        break;
      case 4:
        state = 0;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_driveBase.drive(0, 0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
