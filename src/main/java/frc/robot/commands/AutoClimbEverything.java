/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.awt.Color;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.customobjects.LightStrip.Animations;

public class AutoClimbEverything extends Command {
  private int state;
  private long time;
  private boolean l2;
  private int posStart;

  private float initialRoll;
  private float currentVel;

  public AutoClimbEverything(boolean level2) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_climber);
    requires(Robot.m_arm);
    requires(Robot.m_pneumatics);
    requires(Robot.m_driveBase);

    l2 = level2;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    state = Robot.m_climber.getAutoClimbState();
    Robot.m_pneumatics.disableCompressor();
    time = System.currentTimeMillis();
    if(Robot.m_climber.getAutoClimbState() == 0) {
      posStart = Robot.m_climber.getDistance();
      ///System.out.println("testaaa");
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    SmartDashboard.putNumber("ClimbState", state);
    // double stickPow = Math.abs(Robot.m_oi.getDriveAxis(RobotMap.driveStickY));
    // if(stickPow <= 0.1) stickPow = 0;

    System.out.println("encoder : " + Robot.m_climber.getEncoder());
    SmartDashboard.putNumber("climbEnc", Robot.m_climber.getEncoder());


    switch (state) {
    case 0: //Setup start
      Robot.m_climber.zeroEncoder();
      initialRoll = Robot.m_telemetry.getRoll();

      Robot.m_arduino.bottom.setColor(Color.RED);
      Robot.m_arduino.bottom.setAnimation(Animations.CARNIVAL);

      incState(); // incState() records stat time
      break;

    case 1: //Start climbing, counter tipping with arm
      /*float roll = Robot.m_telemetry.getRoll();
      roll *= 30.0;

      roll = Math.min(0, roll);

      System.out.println("moving");

      Robot.m_arm.setArmPositon(Robot.m_arm.getArmTargetPosition() + roll);
      Robot.m_climber.controlClimberLift(RobotMap.climbUpSpeed);
      if (!Robot.m_climber.getForwardLimitSwitch()) {
        incState();
        System.out.println("switch");
      }
      if(!l2 && Robot.m_climber.getDistance() - posStart <= -10700) { //L2 climb end condition
        Robot.m_climber.controlClimberLift(0);
        incState();
        System.out.println("help");
      }*/

      float roll = Robot.m_telemetry.getRoll() - initialRoll;
      
      currentVel += roll / 1000;
      Robot.m_arm.setArmVelocity(currentVel);

      Robot.m_arduino.bottom.setColor(Color.GREEN);

      Robot.m_climber.controlClimberLift(RobotMap.climbUpSpeed);
      if (!Robot.m_climber.getForwardLimitSwitch()) {
        incState();
        //System.out.println("switch");
      }
      if(!l2 && Robot.m_climber.getEncoder() >= 10000) { //L2 climb end condition
        Robot.m_climber.controlClimberLift(0);
        incState();
        //System.out.println("help");

      }


      //roll *= 30.0;

//level - roll = 46

      //roll = Math.min(0, roll);


      //System.out.println("FwdSwtch: " + Robot.m_climber.getForwardLimitSwitch());
      //System.out.println("RvrSwtch: " + Robot.m_climber.getReverseLimitSwitch());

//Leaning forward violently - roll = -500



      break;

    case 2: //Set arm down all the way
      // Robot.m_arm.setArmPositon(-11100);

      Robot.m_arduino.bottom.setColor(Color.BLUE);
          Robot.m_arm.ArmMoveNoFF(-1.0);
      if (checkDelay(500) || Robot.m_arm.getArmPosition() <= -10000) { //Wait for either arm or time
        incState();
      }
      break;

    case 3: //Hold arm down and drive forward
      // Robot.m_arm.setArmPositon(-11100);
      Robot.m_arduino.bottom.setColor(Color.ORANGE);
          Robot.m_arm.ArmMoveNoFF(-1.0);
      Robot.m_climber.runClimbDrive(1.00);
      Robot.m_driveBase.drive(0, -0.10, 0);
      // Robot.m_climber.runClimbDrive(stickPow*100);
      // Robot.m_driveBase.drive(0, stickPow/10, 0);
      if (checkDelay(1800) || Robot.m_oi.getOpButton(RobotMap.autoClimbArmUp)) { //Wait 5s OR wait for button press
        incState();
      }
      break;

    case 4: //Bring arm partially up, drive forwards
    Robot.m_arduino.bottom.setColor(Color.MAGENTA);
      Robot.m_arm.setArmPositon(-2500);

      Robot.m_climber.runClimbDrive(0);
      Robot.m_driveBase.drive(0, 0, 0);


      //Robot.m_climber.runClimbDrive(1.00);
      //Robot.m_driveBase.drive(0, -0.10, 0);

      // Robot.m_climber.runClimbDrive(stickPow*10);
      // Robot.m_driveBase.drive(0, stickPow/10, 0);
      if (checkDelay(500)) { //Wait 1.5s
        incState();
      }
      break;


      case 5: //Bring arm partially up, drive forwards
      Robot.m_arduino.bottom.setColor(Color.YELLOW);
      Robot.m_arm.setArmPositon(-2500);

      Robot.m_climber.runClimbDrive(1.00);
      Robot.m_driveBase.drive(0, -0.10, 0);

      // Robot.m_climber.runClimbDrive(stickPow*10);
      // Robot.m_driveBase.drive(0, stickPow/10, 0);
      if (checkDelay(1250)) { //Wait 1.5s
        incState();
      }
      break;



    case 6: //Move arm more up, hold little drive power, lift legs

    Robot.m_arduino.bottom.setColor(Color.WHITE);
      Robot.m_arm.setArmPositon(-2500);
      Robot.m_climber.runClimbDrive(-0.50);
      Robot.m_driveBase.drive(0, 0.10, 0);
      // Robot.m_climber.runClimbDrive(stickPow*10);
      // Robot.m_driveBase.drive(0, stickPow/10, 0);
      //  Robot.m_climber.controlClimberLift(-RobotMap.climbDownSpeed);
     //System.out.println("lift legs now pls robot");
      if (checkDelay(100)) { //Wait 3.5s
        incState();
      }
      break;


      case 7: //Move arm more up, hold little drive power, lift legs

      Robot.m_arduino.bottom.setColor(Color.CYAN);
      Robot.m_arm.setArmPositon(-2500);
      Robot.m_climber.runClimbDrive(000);
      Robot.m_driveBase.drive(0, 0, 0);
      // Robot.m_climber.runClimbDrive(stickPow*10);
      // Robot.m_driveBase.drive(0, stickPow/10, 0);
      Robot.m_climber.controlClimberLift(-RobotMap.climbDownSpeed);
      
     
     //System.out.println("lift legs now pls robot");
      if (Robot.m_climber.getEncoder() <= 0) { //Wait 3.5s
        incState();
      }
      break;

case 8:
Robot.m_arduino.bottom.setColor(Color.PINK);
      if (checkDelay(50)) {
        incState();
      }
break;


case 9:
Robot.m_arduino.bottom.setColor(Color.GRAY);
if(!Robot.m_climber.getForwardLimitSwitch()) {
  incState();
} else  {
  Robot.m_arm.setArmPositon(0);
  Robot.m_driveBase.drive(0, -0.10, 0);
  if(checkDelay(500)) {
      incState();
  }
}

break;


    case 10: //Stop driving and hold arm previous position
    Robot.m_arduino.bottom.setColor(Color.RED);
      Robot.m_arm.setArmPositon(0);
      Robot.m_climber.controlClimberLift(0);
      Robot.m_climber.runClimbDrive(0.00);
      Robot.m_driveBase.drive(0, 0, 0);
      // Robot.m_climber.runClimbDrive(stickPow*10);
      // Robot.m_driveBase.drive(0, stickPow/10, 0);
      incState();
      break;

    default:
      break;
    }
  }

  private void incState() {
    state++;
    Robot.m_climber.setAutoClimbState(state);
    time = System.currentTimeMillis();
  }

  private boolean checkDelay(int millis)  {
    return (time + millis <= System.currentTimeMillis());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (state >= 7) {
      System.out.println("FINISHED");
      // return true;
      return false;
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    // double stickPow = Math.abs(Robot.m_oi.getDriveAxis(RobotMap.driveStickY));
    // if(stickPow <= 0.1) stickPow = 0;

    Robot.m_pneumatics.eneableCompressor();
    Robot.m_arm.setArmPositon(Robot.m_arm.getArmTargetPosition());
    Robot.m_climber.controlClimberLift(0);
    Robot.m_climber.runClimbDrive(0.00);
    Robot.m_driveBase.drive(0, 0, 0);
    // Robot.m_climber.runClimbDrive(stickPow*10);
    // Robot.m_driveBase.drive(0, stickPow/10, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
