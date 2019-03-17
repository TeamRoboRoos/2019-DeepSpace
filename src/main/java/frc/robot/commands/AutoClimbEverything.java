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

public class AutoClimbEverything extends Command {
  private int state;
  private long time;
  private boolean l2;
  private int posStart;

  public AutoClimbEverything(boolean level2) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_climber);
    requires(Robot.m_arm);
    requires(Robot.m_pneumatics);
    requires(Robot.m_driveBase);

    l2 = level2;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    state = Robot.m_climber.getAutoClimbState();
    Robot.m_pneumatics.disableCompressor();
    time = System.currentTimeMillis();
    if(Robot.m_climber.getAutoClimbState() == 0) {
      posStart = Robot.m_climber.getDistance();
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    SmartDashboard.putNumber("ClimbState", state);
    // double stickPow = Math.abs(Robot.m_oi.getDriveAxis(RobotMap.driveStickY));
    // if(stickPow <= 0.1) stickPow = 0;

    switch (state) {
    case 0: //Setup start
      incState(); // incState() records stat time
      break;

    case 1: //Start climbing, counter tipping with arm
      float roll = Robot.m_telemetry.getRoll();
      roll *= 30.0;

      roll = Math.min(0, roll);

      Robot.m_arm.setArmPositon(Robot.m_arm.getArmTargetPosition() + roll);
      Robot.m_climber.controlClimberLift(RobotMap.climbUpSpeed);
      if (Robot.m_climber.getForwardLimitSwitch()) {
        incState();
      }
      if(!l2 && Robot.m_climber.getDistance() - posStart <= -10700) { //L2 climb end condition
        Robot.m_climber.controlClimberLift(0);
        incState();
      }
      break;

    case 2: //Set arm down all the way
      // Robot.m_arm.setArmPositon(-11100);
      Robot.m_arm.ArmMoveNoFF(-1.0);
      if (checkDelay(500) || Robot.m_arm.getArmPosition() <= -10000) { //Wait for either arm or time
        incState();
      }
      break;

    case 3: //Hold arm down and drive forward
      // Robot.m_arm.setArmPositon(-11100);
      Robot.m_arm.ArmMoveNoFF(-1.0);
      Robot.m_climber.runClimbDrive(1.00);
      Robot.m_driveBase.drive(0, -0.10, 0);
      // Robot.m_climber.runClimbDrive(stickPow*100);
      // Robot.m_driveBase.drive(0, stickPow/10, 0);
      if (checkDelay(2000) || Robot.m_oi.getOpButton(RobotMap.autoClimbArmUp)) { //Wait 5s OR wait for button press
        incState();
      }
      break;

    case 4: //Bring arm partially up, drive forwards
      Robot.m_arm.setArmPositon(-2500);
      Robot.m_climber.runClimbDrive(1.00);
      Robot.m_driveBase.drive(0, -0.10, 0);
      // Robot.m_climber.runClimbDrive(stickPow*10);
      // Robot.m_driveBase.drive(0, stickPow/10, 0);
      if (checkDelay(1500)) { //Wait 1.5s
        incState();
      }
      break;

    case 5: //Move arm more up, hold little drive power, lift legs
      Robot.m_arm.setArmPositon(-2500);
      Robot.m_climber.runClimbDrive(0.00);
      Robot.m_driveBase.drive(0, -0.10, 0);
      // Robot.m_climber.runClimbDrive(stickPow*10);
      // Robot.m_driveBase.drive(0, stickPow/10, 0);
      Robot.m_climber.controlClimberLift(RobotMap.climbDownSpeed);
      if (checkDelay(3500)) { //Wait 3.5s
        incState();
      }
      break;

    case 6: //Stop driving and hold arm previous position
      Robot.m_arm.setArmPositon(-2500);
      Robot.m_climber.controlClimberLift(0);
      Robot.m_climber.runClimbDrive(0.00);
      Robot.m_driveBase.drive(0, 0, 0);
      // Robot.m_climber.runClimbDrive(stickPow*10);
      // Robot.m_driveBase.drive(0, stickPow/10, 0);
      incState();
      break;

    default:
      break;
    }
  }

  private void incState() {
    state++;
    Robot.m_climber.setAutoClimbState(state);
    time = System.currentTimeMillis();
  }

  private boolean checkDelay(int millis)  {
    return (time + millis <= System.currentTimeMillis());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (state >= 7) {
      System.out.println("FINISHED");
      // return true;
      return false;
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    // double stickPow = Math.abs(Robot.m_oi.getDriveAxis(RobotMap.driveStickY));
    // if(stickPow <= 0.1) stickPow = 0;

    Robot.m_pneumatics.eneableCompressor();
    Robot.m_arm.setArmPositon(Robot.m_arm.getArmTargetPosition());
    Robot.m_climber.controlClimberLift(0);
    Robot.m_climber.runClimbDrive(0.00);
    Robot.m_driveBase.drive(0, 0, 0);
    // Robot.m_climber.runClimbDrive(stickPow*10);
    // Robot.m_driveBase.drive(0, stickPow/10, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
