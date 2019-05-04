/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SerialPort;

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

  public static SerialPort.Port arduinoPort = SerialPort.Port.kMXP;
  
  //Driver Preference Settings
  public static boolean fieldOrientedDrive = false;
  public static boolean squaredDriverInputs = true;
  public static double driveTranslatePower = 0.75;
  public static double driveRotatePower = 0.50;
  
  //Joysticks
  //Joystic Ports
  public static int driveStick = 0;
  public static int opStick = 1;
  //Joystick Axis
  public static int driveStickX = 0;
  public static int driveStickY = 1;
  public static int driveStickZ = 2;
  public static int opAxisArm = 3;
  //Operator Buttons
  public static int grabInButton =10;
  public static int grabOutButton = 5;
  public static int hatchLiftButton = 3;
  public static int hatchDropButton = 4;
  // public static int armUpButton = 1;
  // public static int armDownButton = 1;
  public static int climbUpButton = 8;
  public static int climbDownButton = 7;
  public static int solSlideButton = 2;
  public static int solPushButton = 1;
  public static int autoClimbButtonL3 = 14;
  public static int autoClimbButtonL2 = 13;
  public static int autoClimbArmUp = 12;
  // public static int climbDriveTestButton = 1;

  public static int cameraButton = 4;
  public static int driveStickReverse = 2;
  public static int driveStickPrecision = 1;
  public static int driveStickDriveAngleReset = 3;

  //Analog Inputs

  public static int pressureSensor = 0;


  //CAN/PWM IDs
  //Power Distribution Panel
  public static int pdp = 0; //CAN
  //Pneumatic Control Module
  public static int pcm = 0; //CAN
  //Drive Base
  public static boolean sparkDriveBase = false; //Set to true if using sparks on drive base or false for talons
  public static int frontLeftMotor = 1; //CAN SparkMax
  public static int frontRightMotor = 2; //CAN SparkMax
  public static int rearLeftMotor = 3; //CAN SparkMax
  public static int rearRightMotor = 4; //CAN SparkMax
  //Arm
  public static int armMotor = 3; //CAN TalonSRX
  //Elevator
  public static int elevatorMotor = 2; //CAN TalonSRX
  //Climber
  public static int climbExtensionMotor = 1; //CAN TalonSRX
  public static int climbGrabberMotor = 2; //CAN VictorSPX
  public static int climbDriveMotor = 1; //CAN VictorSPX
  //Grabber
  public static int grabberMotor1 = 0; //PWM Spark
  public static int grabberMotor2 = 1; //PWM Spark
  //-2160 Level 2 rocket position-6444level1


  //Motor Speeds
  //Climber
  public static double climbUpSpeed = 1.00;//]\[]
  public static double climbDownSpeed = -0.50;

  //Elevator
  public static double elevatorUpSpeen = 0.5;
  public static double elevatorDownSpeed = -0.1;

  //Grabber
  public static double grabInSpeed = 0.50;
  public static double grabOutSpeed = -0.50;


  //Solennoid Channels
  public static int[] grabberSolennoidChannel = {3};
  public static int[] hatchSolPusher = {4,5};
  public static int[] hatchSolSlide = {0,1};

  //Voltage Saturaiton
  public static double voltageSaturation = 12.5;

  //Arm PID
  public static double kP = 0.2;
  public static double kI = 0.00025;
  public static double kD = 0;
  public static double kF = 0;
  public static int kIzone = 5000;
  public static int allowableError = 75;
}
