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

public class AutoLiftClimber extends Command {
  public AutoLiftClimber() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_arm);
    requires(Robot.m_climber);
    requires(Robot.m_pneumatics);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.m_pneumatics.disableCompressor();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
<<<<<<< HEAD:src/main/java/frc/robot/commands/AutoClimb.java
    Robot.m_telemetry.getPitch();

    Robot.m_arm.getArmPosition();
    Robot.m_arm.setArmPositon(Robot.m_arm.getArmPosition() + Robot.m_telemetry.getPitch());
=======
    float roll = Robot.m_telemetry.getRoll();
    roll *= 30.0;
>>>>>>> 0570ea66dd52790b95f2362b80c8119ea3928334:src/main/java/frc/robot/commands/AutoLiftClimber.java

    Robot.m_arm.setArmPositon(Robot.m_arm.getArmTargetPosition() + roll);
    Robot.m_climber.controlClimberLift(RobotMap.climbUpSpeed);
  }


  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.m_climber.getForwardLimitSwitch();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_pneumatics.eneableCompressor();
    Robot.m_climber.controlClimberLift(0.0);
    Robot.m_arm.setArmPositon(Robot.m_arm.getArmTargetPosition());
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
