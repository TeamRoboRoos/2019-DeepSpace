/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Add your docs here.
 */
public class Elevator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private WPI_TalonSRX elevatorLift;

  private float maxUpPower = 0.5f;
  private float maxDownPower = -0.3f; 
  private float currentPower = 0;
  private float maxAcceleration = 0.01f;
  enum ElevatorState {GOING_UP, GOING_DOWN, UP, DOWN, PANIC} 
  private ElevatorState state;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public Elevator() {
    elevatorLift = new WPI_TalonSRX(RobotMap.elevatorMotor);
    elevatorLift.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    elevatorLift.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    this.state = ElevatorState.PANIC; 
  }

  public void setState(ElevatorState state) {
    this.state = state;
  }
  
  public void moveElevator(double speed) {

    switch (this.state) {
      case GOING_UP:
        this.calculatePower(this.maxUpPower, this.maxAcceleration);
        break;
      case GOING_DOWN:
        this.calculatePower(this.maxDownPower, this.maxAcceleration);
        break;
      case UP: 
        // a world ending device
        break;
      case DOWN:
        //I just died
        break;
      default:
        this.currentPower = 0;
        break;
    }

    elevatorLift.set(ControlMode.PercentOutput, this.currentPower);
  }

  private void calculatePower(float targetPower, float accelerationRate) {
    if (targetPower < this.currentPower) {
      this.currentPower -= accelerationRate;
      if (targetPower > this.currentPower) {
        this.currentPower = targetPower;
      }
    }
    
    if (targetPower > this.currentPower) {
      this.currentPower += accelerationRate;
      if (targetPower < this.currentPower) {
        this.currentPower = targetPower;
      }
    }
  }

}
