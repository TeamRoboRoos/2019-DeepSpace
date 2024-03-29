/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class SensorRead extends Command {
  public SensorRead() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_telemetry);
    this.setRunWhenDisabled(true);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    SmartDashboard.putNumber("Pressure", Robot.m_telemetry.getPressure());
    SmartDashboard.putNumber("Encoder", Robot.m_arm.getArmPosition());
    SmartDashboard.putNumber("Pitch", Robot.m_telemetry.getRoll());
    SmartDashboard.putBoolean("Breaks", Robot.m_telemetry.getBreaksStatus());
    //System.out.println(Robot.m_climber.getDistance());
    // Robot.m_climber.runClimbDrive(1.00);
    String str = Robot.m_arduino.readString();
    if(str.length() > 0) System.out.println(str);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
