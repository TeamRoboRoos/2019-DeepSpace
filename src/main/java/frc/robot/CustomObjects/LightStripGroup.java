/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.customobjects;

import java.awt.Color;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.SerialPort;
import frc.robot.customobjects.LightStrip.Animations;
import frc.robot.customobjects.LightStrip.Strips;

/**
 * Add your docs here.
 */
public class LightStripGroup {
    private ArrayList<LightStrip> lightStrips;

    public LightStripGroup(SerialPort arduino, Strips[] strips, Animations defaultAnimation, Color defaultColor) {
        for(Strips strip : strips) {
            lightStrips.add(new LightStrip(arduino, strip, defaultAnimation, defaultColor));
        }
    }

    public void setAnimation(Animations animation) {
        for(LightStrip lightStrip : lightStrips) {
            lightStrip.setAnimation(animation);
        }
    }

    public void setAnimation(Color color) {
        for(LightStrip lightStrip : lightStrips) {
            lightStrip.setColor(color);
        }
    }
}
