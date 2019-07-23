/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Climber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private WPI_TalonSRX climbMotor;
  private WPI_VictorSPX stiltMotor, grabberMotor;

  private int autoClimbState = 0;

  public Climber() {
    climbMotor = new WPI_TalonSRX(RobotMap.climbExtensionMotor);
    climbMotor.configFactoryDefault();
    climbMotor.setInverted(InvertType.InvertMotorOutput);
    climbMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
    climbMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
    climbMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    climbMotor.setSelectedSensorPosition(0);

    climbMotor.setSensorPhase(true);
    zeroEncoder();
    climbMotor.configReverseSoftLimitEnable(true);
    climbMotor.configReverseSoftLimitThreshold(0);

    stiltMotor = new WPI_VictorSPX(RobotMap.climbDriveMotor);
    stiltMotor.configFactoryDefault();
    stiltMotor.setInverted(InvertType.None);
    // stiltMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

    grabberMotor = new WPI_VictorSPX(RobotMap.climbGrabberMotor);
    grabberMotor.configFactoryDefault();
    grabberMotor.setInverted(InvertType.InvertMotorOutput);
  }

  @Override

  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());

  }

  public void controlClimberLift(double speed) {
    climbMotor.set(ControlMode.PercentOutput, speed);

  }

  public void setBreaks(boolean set) {
    NeutralMode mode = set ? NeutralMode.Brake : NeutralMode.Coast;
    climbMotor.setNeutralMode(mode);
    stiltMotor.setNeutralMode(mode);
    grabberMotor.setNeutralMode(mode);
  }

  public void runClimbDrive(double speed) {
    stiltMotor.set(ControlMode.PercentOutput, speed * 1.5);
    grabberMotor.set(ControlMode.PercentOutput, speed);
  }

  public int getDistance() {
    return climbMotor.getSelectedSensorPosition();
  }

  public boolean getForwardLimitSwitch() {
    Faults faults = new Faults();
    climbMotor.getFaults(faults);
    System.out.println("fwdswitchclimber: " + climbMotor.getSensorCollection().isFwdLimitSwitchClosed());
    if(climbMotor.getSensorCollection().isFwdLimitSwitchClosed()) {
      return true;
    }
    return false;
  }

  public boolean getReverseLimitSwitch() {
    Faults faults = new Faults();
    climbMotor.getFaults(faults);
    if(climbMotor.getSensorCollection().isRevLimitSwitchClosed()) {
      return true;
    }
    return false;
  }

  public void zeroEncoder() {
    climbMotor.getSensorCollection().setQuadraturePosition(0, 10);
  }

  public int getEncoder() {
    return climbMotor.getSensorCollection().getQuadraturePosition();
  }

  /**
   * @return the autoClimbState
   */
  public int getAutoClimbState() {
    return autoClimbState;
  }

  /**
   * @param autoClimbState the autoClimbState to set
   */
  public void setAutoClimbState(int state) {
    autoClimbState = state;
  }
}
