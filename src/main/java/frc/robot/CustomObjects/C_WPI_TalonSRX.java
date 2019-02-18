/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.customObjects;

import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.StickyFaults;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Custom class which extends WPI_TalonSRX to add functions for getting limit switch and restart status
 */
public class C_WPI_TalonSRX extends WPI_TalonSRX {
  public C_WPI_TalonSRX(int id) {
    super(id);
  }

  public boolean getForwardLimitSwitch() {
    Faults faults = new Faults();
    this.getFaults(faults);
    if (faults.ForwardLimitSwitch) {
      return true;
    }
    return false;
  }

  public boolean getReverseLimitSwitch() {
    Faults faults = new Faults();
    this.getFaults(faults);
    if (faults.ReverseLimitSwitch) {
      return true;
    }
    return false;
  }

  public boolean getForwardLimitSwitchSticky() {
    StickyFaults stickyFaults = new StickyFaults();
    this.getStickyFaults(stickyFaults);
    if (stickyFaults.ForwardLimitSwitch) {
      return true;
    }
    return false;
  }

  public boolean getReverseLimitSwitchSticky() {
    StickyFaults stickyFaults = new StickyFaults();
    this.getStickyFaults(stickyFaults);
    if (stickyFaults.ReverseLimitSwitch) {
      return true;
    }
    return false;
  }

  public boolean getResetDuringEnSticky() {
    StickyFaults stickyFaults = new StickyFaults();
    this.getStickyFaults(stickyFaults);
    if (stickyFaults.ResetDuringEn) {
      return true;
    }
    return false;
  }
}
