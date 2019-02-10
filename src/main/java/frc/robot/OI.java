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
import frc.robot.commands.*;
import frc.robot.subsystems.Elevator;

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

  private Button grabBall;
  private Button liftElevator, dropElevator;
  private Button climb, reverseClimb;
  private Button armUp, armDown;
  private Button solForward, solReverse;
  private Button autoClimb;

  private Button cameraSwap;
  public OI() {
    driveStick = new Joystick(RobotMap.driveStick);

    cameraSwap = new JoystickButton(driveStick, RobotMap.cameraButton);
    cameraSwap.whenPressed(new CameraSwap());

    grabBall = new JoystickButton(driveStick, RobotMap.grabButton);
    grabBall.whileHeld(new Grab());

    solForward = new JoystickButton(driveStick, RobotMap.solForwardButton);
    solForward.whenPressed(new MoveSolenoid(true));
    solForward.whenReleased(new MoveSolenoid(false));
    solReverse = new JoystickButton(driveStick, RobotMap.solReverseButton);
    solReverse.whenPressed(new MoveSolenoid(true));
    solReverse.whenReleased(new MoveSolenoid(false));

    dropElevator = new JoystickButton(driveStick, RobotMap.dropButton);
    dropElevator.whileHeld(new MoveElevator(Elevator.ElevatorState.GOING_DOWN));

    liftElevator = new JoystickButton(driveStick, RobotMap.liftButton);
    liftElevator.whileHeld(new MoveElevator(Elevator.ElevatorState.GOING_UP));


    climb = new JoystickButton(driveStick, RobotMap.climbUpButton);
    climb.whileHeld(new RobotClimb(RobotMap.climbUpSpeed));

    reverseClimb = new JoystickButton(driveStick, RobotMap.climbDownButton);
    reverseClimb.whileHeld(new RobotClimb(RobotMap.climbDownSpeed));

    autoClimb = new JoystickButton(driveStick, RobotMap.autoClimbButton);
    autoClimb.whenPressed(new AutoClimb());

    armUp = new JoystickButton(driveStick, RobotMap.armUpButton); 
    armUp.whileHeld(new ArmMove(RobotMap.climbUpSpeed));

    armDown = new JoystickButton(driveStick, RobotMap.armDownButton);
    armDown.whileHeld(new ArmMove(RobotMap.climbDownSpeed));
  }

  public double getDriveAxis(int axis) {
    return driveStick.getRawAxis(axis);
  }
}
