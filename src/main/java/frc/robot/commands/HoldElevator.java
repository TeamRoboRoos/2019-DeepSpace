/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.awt.Color;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.customobjects.LightStrip.Animations;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Elevator.ElevatorState;

public class HoldElevator extends Command {
  ElevatorState state, lastState=ElevatorState.PANIC;

  public HoldElevator() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.m_elevator.moveElevator();
    
    state = Robot.m_elevator.getState();
    if (state != lastState) {
      lastState = state;
      switch (state) {
        case DOWN:
          Robot.m_arduino.bottom.setColor(Color.GREEN);
          Robot.m_arduino.bottom.setAnimation(Animations.SOLIDCOLOR);
          break;
        case GOING_DOWN:
          Robot.m_arduino.bottom.setColor(Color.GREEN);
          Robot.m_arduino.bottom.setAnimation(Animations.BLINK);
          break;
        case UP:
          Robot.m_arduino.bottom.setColor(Color.BLUE);
          Robot.m_arduino.bottom.setAnimation(Animations.SOLIDCOLOR);
          break;
        case GOING_UP:
          Robot.m_arduino.bottom.setColor(Color.BLUE);
          Robot.m_arduino.bottom.setAnimation(Animations.BLINK);
          break;
        default:
          Robot.m_arduino.bottom.setColor(Color.RED);
          Robot.m_arduino.bottom.setAnimation(Animations.FADE);
          break;
      }
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
    Robot.m_elevator.setState(Elevator.ElevatorState.PANIC);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
