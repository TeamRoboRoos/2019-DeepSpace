/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.RobotMap;
import frc.robot.commands.DriveMecanum;

/**
 * Add your docs here.
 */
public class DriveBase extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private CANSparkMax frontLeft, frontRight, rearLeft, rearRight;
  private MecanumDrive drive;

  public DriveBase() {
    frontLeft = new CANSparkMax(RobotMap.frontLeftMotor, MotorType.kBrushless);
    frontRight = new CANSparkMax(RobotMap.frontRightMotor, MotorType.kBrushless);
    rearLeft = new CANSparkMax(RobotMap.rearLeftMotor, MotorType.kBrushless);
    rearRight = new CANSparkMax(RobotMap.rearRightMotor, MotorType.kBrushless);

    drive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new DriveMecanum());
  }

  public void drive(double x, double y, double z) {
    drive.driveCartesian(x, y, z);
  }  
}
