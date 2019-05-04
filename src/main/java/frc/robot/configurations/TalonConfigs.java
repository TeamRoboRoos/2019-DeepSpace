/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.configurations;

import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRXPIDSetConfiguration;

/**
 * Add your docs here.
 */
public class TalonConfigs {
    public class Arm {
        public TalonSRXConfiguration base = new TalonSRXConfiguration();
        TalonSRXPIDSetConfiguration PIDNominal = new TalonSRXPIDSetConfiguration();
        SlotConfiguration slotNominal = new SlotConfiguration();
    }

    public class Elevator {
        TalonSRXConfiguration base = new TalonSRXConfiguration();
        TalonSRXPIDSetConfiguration PIDNominal = new TalonSRXPIDSetConfiguration();
        SlotConfiguration slotNominal = new SlotConfiguration();
    }
}
