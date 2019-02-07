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
  public DoubleSolenoid hatchSol1;
  public DoubleSolenoid hatchSol2;

  public HatchGrabber() {
     // Put methods for controlling this subsystem
     // here. Call these from Commands.
     hatchSol1 = new DoubleSolenoid(RobotMap.hatchSolChannel1[0], RobotMap.hatchSolChannel1[1]);
     hatchSol2 = new DoubleSolenoid(RobotMap.hatchSolChannel2[0], RobotMap.hatchSolChannel2[1]);
  }
  public void setHatchSol1(boolean extend) {
    if (extend) {
      hatchSol1.set(Value.kForward);
    } else {
      hatchSol1.set(Value.kReverse);
    }
  }
  public void setHatchSol2(boolean extend) {
    if (extend) {
      hatchSol2.set(Value.kForward);
    } else {
      hatchSol2.set(Value.kReverse);
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}



