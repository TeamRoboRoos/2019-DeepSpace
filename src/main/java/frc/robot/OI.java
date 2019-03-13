/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import frc.robot.commands.*;
import frc.robot.subsystems.Elevator;
import frc.robot.triggers.UserButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());

  private Joystick driveStick, opStick;

  private Button grabBallIn, grabBallOut;
  private Button liftElevator, dropElevator;
  private Button climb, reverseClimb;
  // private Button armUp, armDown;
  private Button solGrabSlide, solGrabPusher;
  private Button autoClimbL1, autoClimbL2;
  // private Button cameraSwap;
  private Button reverseButton;
  private Button resetDriveAngleButton;
  private Button precisionDriveButton;
  private Button climbDriveTest;

  private Trigger breaks;

  public OI() {
    driveStick = new Joystick(RobotMap.driveStick);
    opStick = new Joystick(RobotMap.opStick);


    // cameraSwap = new JoystickButton(driveStick, RobotMap.cameraButton);
    // cameraSwap.whenPressed(new CameraSwap());

    grabBallIn = new JoystickButton(opStick, RobotMap.grabInButton);
    grabBallIn.whileHeld(new Grab(RobotMap.grabInSpeed));

    grabBallOut = new JoystickButton(opStick, RobotMap.grabOutButton);
    // grabBallOut.whenPressed(new MoveGrabberSolenoid(true));
    grabBallOut.whileHeld(new Grab(RobotMap.grabOutSpeed));
    // grabBallOut.whenReleased(new MoveGrabberSolenoid(false));

    solGrabSlide = new JoystickButton(opStick, RobotMap.solSlideButton);
    solGrabSlide.whenPressed(new HatchSolenoidSlide());

    solGrabPusher = new JoystickButton(opStick, RobotMap.solPushButton);
    solGrabPusher.whenPressed(new HatchSolenoidPusher(true));
    solGrabPusher.whenReleased(new HatchSolenoidPusher(false));

    dropElevator = new JoystickButton(opStick, RobotMap.hatchDropButton);
    dropElevator.whileHeld(new SetElevatorInstant(Elevator.ElevatorState.GOING_DOWN));

    liftElevator = new JoystickButton(opStick, RobotMap.hatchLiftButton);
    liftElevator.whileHeld(new SetElevatorInstant(Elevator.ElevatorState.GOING_UP));


    // climb = new JoystickButton(opStick, RobotMap.climbUpButton);
    // climb.whileHeld(new RobotClimb(RobotMap.climbUpSpeed));

    reverseClimb = new JoystickButton(opStick, RobotMap.climbDownButton);
    reverseClimb.whileHeld(new RobotClimb(RobotMap.climbDownSpeed));

    autoClimbL1 = new JoystickButton(opStick, RobotMap.autoClimbButtonL1);
    autoClimbL1.whileHeld(new AutoClimbEverything(false));

    autoClimbL2 = new JoystickButton(opStick, RobotMap.autoClimbButtonL2);
    autoClimbL2.whileHeld(new AutoClimbEverything(true));

    // armUp = new JoystickButton(opStick, RobotMap.armUpButton); 
    // armUp.whileHeld(new ArmMove(RobotMap.climbUpSpeed));

    // armDown = new JoystickButton(opStick, RobotMap.armDownButton);
    // armDown.whileHeld(new ArmMove(RobotMap.climbDownSpeed));

    breaks = new UserButton();
    breaks.whenActive(new SetAllBreaksInstant());
    
    reverseButton = new JoystickButton(driveStick, RobotMap.driveStickReverse);
    reverseButton.whenPressed(new ReverseDriveInstant());

    resetDriveAngleButton = new JoystickButton(driveStick, RobotMap.driveStickDriveAngleReset);
    resetDriveAngleButton.whenPressed(new ResetDriveAngleInstant());

    precisionDriveButton = new JoystickButton(driveStick, RobotMap.driveStickPrecision);
    precisionDriveButton.whenPressed(new PrecisionDriveInstant());
    precisionDriveButton.whenReleased(new PrecisionDriveInstant());

    // climbDriveTest = new JoystickButton(opStick, RobotMap.climbDriveTestButton);
    // climbDriveTest.whileHeld(new ClimbDrive());
  }

  public double getDriveAxis(int axis) {
    return driveStick.getRawAxis(axis);
  }

  public boolean getDriveButton(int button) {
    return driveStick.getRawButton(button);
  }

  public double getOpAxis(int axis) {
    return opStick.getRawAxis(axis);
  }

  public boolean getOpButton(int button) {
    return opStick.getRawButton(button);
  }
}
