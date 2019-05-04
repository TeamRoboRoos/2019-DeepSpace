/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.customobjects;

import java.awt.Color;
import java.util.ArrayList;

import frc.robot.customobjects.LightStrip.Animations;
import frc.robot.customobjects.LightStrip.Strips;

/**
 * Add your docs here.
 */
public class LightStripGroup {
    private ArrayList<LightStrip> lightStrips;
    private LightStripController controller;
    private Animations currentAnimation;
    private Color currentColor;

    public LightStripGroup(LightStripController controller, Strips[] strips, Animations defaultAnimation, Color defaultColor) {
        initGroup(controller, defaultAnimation, defaultColor);
        for(Strips strip : strips) {
           addStrip(strip);
        }
    }

    public LightStripGroup(LightStripController controller, Animations defaultAnimation, Color defaultColor) {
        initGroup(controller, defaultAnimation, defaultColor);
    }

    private void initGroup(LightStripController controller, Animations defaultAnimation, Color defaultColor) {
        this.controller = controller;
        currentAnimation = defaultAnimation;
        currentColor = defaultColor;
        lightStrips = new ArrayList<LightStrip>();
    }

    public void addStrip(Strips strip) {
        lightStrips.add(new LightStrip(controller, strip, currentAnimation, currentColor));
    }

    public void clearStrips() {
        lightStrips.clear();
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
