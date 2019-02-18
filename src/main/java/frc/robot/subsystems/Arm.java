/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import frc.robot.RobotMap;
import frc.robot.customObjects.C_WPI_TalonSRX;
import frc.robot.commands.TestArm;

/**
 * Add your docs here.
 */
public class Arm extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private C_WPI_TalonSRX armMotor;
  private boolean hasZeroed = false;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new TestArm());
  }

  public Arm() {
    armMotor = new C_WPI_TalonSRX(RobotMap.armMotor);
    armMotor.configFactoryDefault();
    armMotor.configVoltageCompSaturation(RobotMap.voltageSaturation);
    armMotor.enableVoltageCompensation(true);
    armMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    armMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    armMotor.configClearPositionOnLimitF(true, 10);

    /* Config the sensor used for Primary PID and sensor direction */
    armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

    /* Ensure sensor is positive when output is positive */
    armMotor.setSensorPhase(false);

    /**
     * Config the allowable closed-loop error, Closed-Loop output will be neutral
     * within this range. See Table in Section 17.2.1 for native units per rotation.
     */
    armMotor.configAllowableClosedloopError(0, RobotMap.allowableError);

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
    armMotor.config_IntegralZone(0, RobotMap.kIzone);
  }

  public void setArmPositon(double pos) {
    if (hasZeroed) {
      armMotor.set(ControlMode.Position, pos, DemandType.ArbitraryFeedForward, calculateFeedForward(pos));
      armStatusDashboard();
    } else {
      zeroArm();
    }
  }

  public void zeroArm() {
    if (!hasZeroed) {
      System.out.println("Error: Arm not zeroed! Zeroing now.");
      ArmMoveNoFF(0.5);
    }
    if (armMotor.getForwardLimitSwitch()) {
      System.out.println("Arm successfully zeroed!");
      hasZeroed = true;
    }
  }

  public double calculateFeedForward(double pos) {
    double ff = (-3 * Math.pow(10, -5)) * pos;
    // ff = 0;
    SmartDashboard.putNumber("FeedForward", ff);
    return ff;
  }

  public int getArmPosition() {
    return armMotor.getSelectedSensorPosition();
  }

  public double getArmTargetPosition() {
    return armMotor.getClosedLoopTarget();
  }

  public void ArmMove(double speed) {
    armMotor.set(ControlMode.PercentOutput, speed + calculateFeedForward(getArmPosition()));
    armStatusDashboard();
  }

  public void ArmMoveNoFF(double speed) {
    armMotor.set(ControlMode.PercentOutput, speed);
    armStatusDashboard();
  }

  public void setBreaks(boolean set) {
    armMotor.setNeutralMode(set ? NeutralMode.Brake : NeutralMode.Coast);
  }

  public void armStatusDashboard() {
    SmartDashboard.putNumber("ArmPowerOutPercent", armMotor.getMotorOutputPercent());
    SmartDashboard.putNumber("ArmPowerOutVoltage", armMotor.getMotorOutputVoltage());
    SmartDashboard.putNumber("ArmPowerOutCurrent", armMotor.getOutputCurrent());
  }

  public boolean getHasZeroed() {
    return hasZeroed;
  }
}