/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

  
  //Joysticks
  //Joystic Ports
  public static int driveStick = 0;
  public static int opStick = 1;
  //Joystick Axis
  public static int driveStickX = 0;
  public static int driveStickY = 1;
  public static int driveStickZ = 2;
  //Operator Buttons
  public static int grabButton = 1;
  public static int liftButton = 1;
  public static int dropButton = 1;
  public static int armUpButton = 1;
  public static int armDownButton = 1;
  public static int climbUpButton = 3;
  public static int climbDownButton = 4;


  //CAN/PWM IDs
  //Power Distribution Panel
  public static int pdp = 0; //CAN
  //Pneumatic Control Module
  public static int pcm = 0; //CAN
  //Drive Base
  public static int frontLeftMotor = 1; //CAN SparkMax
  public static int frontRightMotor = 2; //CAN SparkMax
  public static int rearLeftMotor = 3; //CAN SparkMax
  public static int rearRightMotor = 4; //CAN SparkMax
  //Arm
  public static int armMotor = 1; //CAN TalonSRX
  //Elevator
  public static int elevatorMotor = 4; //CAN TalonSRX
  //Climber
  public static int climbExtensionMotor = 3; //CAN TalonSRX
  public static int climbGrabberMotor = 1; //CAN VictorSPX
  public static int climbDriveMotor = 2; //CAN VictorSPX
  //Grabber
  public static int grabberMotor = 1; //PWM Spark


  //Motor Speeds
  //Climber
  public static double climbUpSpeed = 0.5;
  public static double climbDownSpeed = -0.5;
  //Elevator
  public static double elevatorUpSpeen = 0.5;
  public static double elevatorDownSpeed = -0.1;
  //Grabber
  public static double grabInSpeed = 0.5;
  public static double grabOutSpeed = 0.5;
}
