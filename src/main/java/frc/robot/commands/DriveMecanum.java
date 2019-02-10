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
import frc.robot.RobotMap;

public class DriveMecanum extends Command {
  public DriveMecanum() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_driveBase);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double powerX = 0.75 * Robot.m_oi.getDriveAxis(RobotMap.driveStickX);
    double powerY = -0.75 * Robot.m_oi.getDriveAxis(RobotMap.driveStickY);
    double powerZ = -0.5 * Robot.m_oi.getDriveAxis(RobotMap.driveStickZ);

    Robot.m_driveBase.drive(powerX, powerY, powerZ, RobotMap.squaredDriverInputs, RobotMap.fieldOrientedDrive);
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
}
