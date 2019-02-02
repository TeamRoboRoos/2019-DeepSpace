/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;
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
  private AHRS navx;

  public DriveBase() {
    frontLeft = new CANSparkMax(RobotMap.frontLeft, MotorType.kBrushless);
    frontRight = new CANSparkMax(RobotMap.frontRight, MotorType.kBrushless);
    rearLeft = new CANSparkMax(RobotMap.rearLeft, MotorType.kBrushless);
    rearRight = new CANSparkMax(RobotMap.rearRight, MotorType.kBrushless);

    drive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);
    navx = new AHRS(SPI.Port.kMXP);
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

  public double getAngle() {
   return navx.getAngle();
  }
  
}
 