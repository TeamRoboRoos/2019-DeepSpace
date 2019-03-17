/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DriveMecanumAllign extends Command {
  private boolean debugOut = false;

  public DriveMecanumAllign() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_driveBase);
    this.setRunWhenDisabled(true);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double powerX = RobotMap.driveTranslatePower * Robot.m_oi.getDriveAxis(RobotMap.driveStickX);
    double powerY = -RobotMap.driveTranslatePower * Robot.m_oi.getDriveAxis(RobotMap.driveStickY);
    double powerZ = -RobotMap.driveRotatePower * Robot.m_oi.getDriveAxis(RobotMap.driveStickZ);

    boolean left, mid, right;
    left = Robot.m_arduino.getLeft();
    mid = Robot.m_arduino.getMid();
    right = Robot.m_arduino.getRight();
    double fast = 0.50;
    double slow = 0.25;
    if(!left && !mid && !right) {       //O O O  ||Not detected     ||Retain driver control
      //Nothing
      debug("O O O");
    } else if(!left && mid && !right) { //O X O ||Centered
      powerX = 0;
      debug("O X O");
    } else if(left && mid && !right) {  //X X O ||Go right slow
      powerX = slow;
      debug("X X O");
    } else if(left && !mid && !right) { //X O O ||Go right
      powerX = fast;
      debug("X O O");
    } else if(!left && mid && right) {  //O X X ||Go left slow
      powerX = -slow;
      debug("O X X");
    } else if(!left && !mid && right) { //O O X ||Go left
      powerX = -fast;
      debug("O O X");
    } else if(left && !mid && right) {  //X O X ||Error            ||Retain driver control
      //Nothing
      debug("X O X");
    } else if(left && mid && right) {   //X X X ||Error            ||Retain driver control
      //Nothing
      debug("X X X");
    }

    Robot.m_driveBase.drive(powerX, powerY, powerZ, RobotMap.squaredDriverInputs, false);
    debug(Double.toString(powerX));
  }

  // Make this return true when this Command no longer needs to run execute
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

  private void debug(String data) {
    if(debugOut) {
      System.out.println(data);
    }
  }
}
