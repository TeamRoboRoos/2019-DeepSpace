/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class BallGrabber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public WPI_VictorSPX grabMotor;
  public DoubleSolenoid solenoid;

  public BallGrabber() {
    grabMotor = new WPI_VictorSPX(RobotMap.grabButton);
    solenoid = new DoubleSolenoid(RobotMap.grabberSolennoidChannel[0], RobotMap.grabberSolennoidChannel[1]); 
  }

  public void setSolenoid(boolean extend) {
    if (extend) {
      solenoid.set(Value.kForward);
    } else {
      solenoid.set(Value.kReverse);
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void grab(double speed) {
    grabMotor.set(ControlMode.PercentOutput, speed);
  }
}
