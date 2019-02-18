/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Add your docs here.
 */
public class HatchGrabber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public DoubleSolenoid hatchSolPusher;
  public DoubleSolenoid hatchSolSlide;

  public HatchGrabber() {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    hatchSolPusher = new DoubleSolenoid(RobotMap.hatchSolPusher[0], RobotMap.hatchSolPusher[1]);
    hatchSolSlide = new DoubleSolenoid(RobotMap.hatchSolSlide[0], RobotMap.hatchSolSlide[1]);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void setHatchSolPusher(boolean extend) {
    if (extend) {
      hatchSolPusher.set(Value.kForward);
    } else {
      hatchSolPusher.set(Value.kReverse);
    }
  }

  public void toggleHatchSolPusher() {
    DoubleSolenoid.Value value = Value.kOff;
    switch (hatchSolPusher.get()) {
    case kForward:
      value = DoubleSolenoid.Value.kReverse;
      break;
    case kReverse:
      value = DoubleSolenoid.Value.kForward;
      break;
    case kOff:
      value = DoubleSolenoid.Value.kForward;
      break;
    }
    hatchSolPusher.set(value);
  }

  public void setHatchSolSlide(boolean extend) {
    if (extend) {
      hatchSolSlide.set(Value.kForward);
    } else {
      hatchSolSlide.set(Value.kReverse);
    }
  }

  public void toggleHatchSolSlide() {
    DoubleSolenoid.Value value = Value.kOff;
    switch (hatchSolSlide.get()) {
    case kForward:
      value = DoubleSolenoid.Value.kReverse;
      break;
    case kReverse:
      value = DoubleSolenoid.Value.kForward;
      break;
    case kOff:
      value = DoubleSolenoid.Value.kForward;
      break;
    }
    hatchSolSlide.set(value);
  }
}
