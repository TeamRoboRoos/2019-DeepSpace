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

  private Joystick driveStick;//, opStick;

  private Button grabBallIn, grabBallOut;
  private Button liftElevator, dropElevator;
  private Button climb, reverseClimb;
  // private Button armUp, armDown;
  // private Button solGrabForward, solGrabReverse;
  private Button autoClimb;
  private Button cameraSwap;
  private Button reverseButton;
  private Button resetDriveAngleButton;
  private Button precisionDriveButton;
  private Button climbDriveTest;

  private Trigger breaks;

  public OI() {
    driveStick = new Joystick(RobotMap.driveStick);

    cameraSwap = new JoystickButton(driveStick, RobotMap.cameraButton);
    cameraSwap.whenPressed(new CameraSwap());

    grabBallIn = new JoystickButton(driveStick, RobotMap.grabInButton);
    grabBallIn.whileHeld(new Grab(RobotMap.grabInSpeed));

    grabBallOut = new JoystickButton(driveStick, RobotMap.grabOutButton);
    // grabBallOut.whenPressed(new MoveGrabberSolenoid(true));
    grabBallOut.whileHeld(new Grab(RobotMap.grabOutSpeed));
    // grabBallOut.whenReleased(new MoveGrabberSolenoid(false));

    // solGrabForward = new JoystickButton(driveStick, RobotMap.solForwardButton);
    // solGrabForward.whenPressed(new MoveGrabberSolenoid(true));
    // solGrabForward.whenReleased(new MoveGrabberSolenoid(false));

    // solGrabReverse = new JoystickButton(driveStick, RobotMap.solReverseButton);
    // solGrabReverse.whenPressed(new MoveGrabberSolenoid(true));
    // solGrabReverse.whenReleased(new MoveGrabberSolenoid(false));

    dropElevator = new JoystickButton(driveStick, RobotMap.hatchDropButton);
    dropElevator.whileHeld(new SetElevatorInstant(Elevator.ElevatorState.GOING_DOWN));

    liftElevator = new JoystickButton(driveStick, RobotMap.hatchLiftButton);
    liftElevator.whileHeld(new SetElevatorInstant(Elevator.ElevatorState.GOING_UP));


    climb = new JoystickButton(driveStick, RobotMap.climbUpButton);
    climb.whileHeld(new RobotClimb(RobotMap.climbUpSpeed));

    reverseClimb = new JoystickButton(driveStick, RobotMap.climbDownButton);
    reverseClimb.whileHeld(new RobotClimb(RobotMap.climbDownSpeed));

    autoClimb = new JoystickButton(driveStick, RobotMap.autoClimbButton);
    autoClimb.whileHeld(new AutoLiftClimber());

    // armUp = new JoystickButton(driveStick, RobotMap.armUpButton); 
    // armUp.whileHeld(new ArmMove(RobotMap.climbUpSpeed));

    // armDown = new JoystickButton(driveStick, RobotMap.armDownButton);
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

    climbDriveTest = new JoystickButton(driveStick, RobotMap.climbDriveTestButton);
    climbDriveTest.whileHeld(new Climb());
  }

  public double getDriveAxis(int axis) {
    return driveStick.getRawAxis(axis);
  }
}
