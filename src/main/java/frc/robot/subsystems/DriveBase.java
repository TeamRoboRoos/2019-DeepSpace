/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.DriveMecanum;

/**
 * Add your docs here.
 */
public class DriveBase extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private CANSparkMax frontLeft, frontRight, rearLeft, rearRight;
  private WPI_TalonSRX frontLeftTalon, frontRightTalon, rearLeftTalon, rearRightTalon;
  private MecanumDrive drive;

  private boolean reversed = false;
  private boolean precision = false;
  private double gyroAngleZero = 0;

  public DriveBase() {
    if (RobotMap.sparkDriveBase) {
      frontLeft = new CANSparkMax(RobotMap.frontLeftMotor, MotorType.kBrushless);
      frontRight = new CANSparkMax(RobotMap.frontRightMotor, MotorType.kBrushless);
      rearLeft = new CANSparkMax(RobotMap.rearLeftMotor, MotorType.kBrushless);
      rearRight = new CANSparkMax(RobotMap.rearRightMotor, MotorType.kBrushless);

      drive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);
    } else {
      // frontLeftTalon = new WPI_TalonSRX(RobotMap.frontLeftMotor);
      // frontRightTalon = new WPI_TalonSRX(RobotMap.frontRightMotor);
      // rearLeftTalon = new WPI_TalonSRX(RobotMap.rearLeftMotor);
      // rearRightTalon = new WPI_TalonSRX(RobotMap.rearRightMotor);

      drive = new MecanumDrive(frontLeftTalon, rearLeftTalon, frontRightTalon, rearRightTalon);
    }
    SmartDashboard.putData("Drive Base", drive);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new DriveMecanum());
  }

  public void drive(double xIn, double yIn, double zIn) {
    drive(xIn, yIn, zIn, false, false);
  }

  public void drive(double xIn, double yIn, double zIn, boolean squaredInputs) {
    drive(xIn, yIn, zIn, squaredInputs, false);
  }

  public void drive(double xIn, double yIn, double zIn, boolean squaredInputs, boolean fieldOriented) {
    double x, y, z;

    if (precision) {
      xIn *= 0.5;
      yIn *= 0.5;
      zIn *= 0.5;
    }

    if (squaredInputs) {
      int xNegative = xIn < 0 ? -1 : +1;
      int yNegative = yIn < 0 ? -1 : +1;
      int zNegative = zIn < 0 ? -1 : +1;
      x = Math.pow(xIn, 2) * xNegative;
      y = Math.pow(yIn, 2) * yNegative;
      z = Math.pow(zIn, 2) * zNegative;
    } else {
      x = xIn;
      y = yIn;
      z = zIn;
    }

    if (reversed && !RobotMap.fieldOrientedDrive) {
      x *= -1;
      y *= -1;
      z *= +1;
    }

    double gyroAngle = 0;
    if (fieldOriented) {
      gyroAngle = Robot.m_telemetry.getGyroAngle() - gyroAngleZero;
    }

    drive.driveCartesian(x, y, z, -gyroAngle);
  }

  public void resetGyro() {
    gyroAngleZero = Robot.m_telemetry.getGyroAngle();
  }

  public void toggleDirection() {
    reversed = !reversed;
  }

  public void togglePrecision() {
    precision = !precision;
  }

  public void setBreaks(boolean set) {
    if (RobotMap.sparkDriveBase) {
      IdleMode mode = set ? IdleMode.kBrake : IdleMode.kBrake;
      frontLeft.setIdleMode(mode);
      frontRight.setIdleMode(mode);
      rearLeft.setIdleMode(mode);
      rearRight.setIdleMode(mode);
    } else {
      NeutralMode mode = set ? NeutralMode.Brake : NeutralMode.Coast;
      frontLeftTalon.setNeutralMode(mode);
      frontRightTalon.setNeutralMode(mode);
      rearLeftTalon.setNeutralMode(mode);
      rearRightTalon.setNeutralMode(mode);
    }

  }
}
