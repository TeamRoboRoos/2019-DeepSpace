/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Joystick;

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

  public static int armMotor = 1;
  
  
  //Value for WPI_VictorSPX
  public static int WPI_VictorSPX = 1; 

  //Speed for Climber Motor
  public static double climberSpeed = 0.5;
  public static double reverseClimberSpeed = -0.5;


  
  public static double elevatorSpeedUp = 0.5;
  public static double elevatorSpeedDown = -0.1;

  //Joysticks
  public static int driveStick = 1;

  //Joystick Axis
  public static int driveStickX = 1;
  public static int driveStickY = 2;
  public static int driveStickZ = 3;

  //Operator Buttons
  public static int grabButton = 0;
  public static int liftButton = 0;
  public static int dropButton = 0;
  public static int climbButton = 0;
  public static int reverseClimbButton = 0; 

  //Drivebase Motors
  public static int frontLeft = 1;
  public static int frontRight = 2;
  public static int rearLeft = 3;
  public static int rearRight = 4;

  //Grabber
  public static int grabMotor = 0;
  public static double grabSpeed = 0.5;

}
