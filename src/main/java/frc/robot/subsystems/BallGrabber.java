/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class BallGrabber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public Spark grabMotor1, grabMotor2;
  public Solenoid solenoid;

  public BallGrabber() {
    grabMotor1 = new Spark(RobotMap.grabberMotor1);
    grabMotor2 = new Spark(RobotMap.grabberMotor2);
    solenoid = new Solenoid(RobotMap.grabberSolennoidChannel[0]); 
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void setSolenoid(boolean extend) {
    if (extend) {
      solenoid.set(true);
    } else {
      solenoid.set(false);
    }
  }

  public void grab(double speed) {
    grabMotor1.set(-speed);
    grabMotor2.set(-speed);
  }
}
