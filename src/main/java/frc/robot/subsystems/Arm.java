/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;

import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Arm extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private WPI_TalonSRX armMotor;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public Arm() {
    armMotor = new WPI_TalonSRX(RobotMap.armMotor);
    armMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    armMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);

    /* Config the sensor used for Primary PID and sensor direction */
    armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

    /* Ensure sensor is positive when output is positive */
    // armMotor.setSensorPhase(true);

    /**
     * Config the allowable closed-loop error, Closed-Loop output will be neutral
     * within this range. See Table in Section 17.2.1 for native units per rotation.
     */
    armMotor.configAllowableClosedloopError(0, RobotMap.allowableClosedloopError);

    /**
     * Set based on what direction you want forward/positive to be. This does not
     * affect sensor phase.
     */
    armMotor.setInverted(InvertType.None);

    /* Config the peak and nominal outputs, 12V means full */
    armMotor.configNominalOutputForward(0);
    armMotor.configNominalOutputReverse(0);
    armMotor.configPeakOutputForward(1);
    armMotor.configPeakOutputReverse(-1);

    /* Config Position Closed Loop gains in slot0, tsypically kF stays zero. */
    armMotor.config_kF(0, RobotMap.kF);
    armMotor.config_kP(0, RobotMap.kP);
    armMotor.config_kI(0, RobotMap.kI);
    armMotor.config_kD(0, RobotMap.kD);

  }
  public void setArmPositon(int pos) {
    armMotor.set(ControlMode.Position, pos, DemandType.ArbitraryFeedForward, calculateFeedForward(pos));
  }

  public double calculateFeedForward(int pos) {
    return 0;
  }

  public double getArmPosition() {
    return armMotor.getSelectedSensorPosition();
  }

  public void ArmMove(double speed) {
    armMotor.set(ControlMode.PercentOutput, speed);
  }

}