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
 * Class designed to combine multiple LightStrip instances into one class for easy control
 */
public class LightStripGroup {
    private ArrayList<LightStrip> lightStrips; //Array of LightStrip objects
    private LightStripController controller; //The controller to be handed to LightStrip instances
    private Animations currentAnimation; //The current animation being run on the group
    private Color currentColor; //The current color of the group

    /**
     * Creates a LightStripGroup from an array of strips, also instantiates the LightStrips the group is comprised of
     * @param controller The controller object to be passed to LightStrip instances
     * @param strips The strips the groups encapsulates
     * @param defaultAnimation The animation that should be run on the strips
     * @param defaultColor The color the strips should be
     */
    public LightStripGroup(LightStripController controller, Strips[] strips, Animations defaultAnimation, Color defaultColor) {
        initGroup(controller, defaultAnimation, defaultColor);
        for (Strips strip : strips) {
           addStrip(strip);
        }
    }

    /**
     * Creates an empty group, LightStrips can be added later with the {@link #addStrip(LightStrip)} or {@link #addStrip(Strips)} methods
     * @param controller The controller object to be passed to LightStrip instances
     * @param defaultAnimation The animation that should be run on the strips
     * @param defaultColor The color the strips should be
     */
    public LightStripGroup(LightStripController controller, Animations defaultAnimation, Color defaultColor) {
        initGroup(controller, defaultAnimation, defaultColor);
    }

    /**
     * Initializes the LightStripGroup object with its default values
     * @param controller The controller object to be passed to LightStrip instances
     * @param defaultAnimation The animation that should be run on the strips
     * @param defaultColor The color the strips should be
     */
    private void initGroup(LightStripController controller, Animations defaultAnimation, Color defaultColor) {
        this.controller = controller;
        currentAnimation = defaultAnimation;
        currentColor = defaultColor;
        lightStrips = new ArrayList<LightStrip>();
    }

    /**
     * Adds a strip to the group, creates a new LightStrip instance
     * @param strip The strip to be added
     */
    public void addStrip(Strips strip) {
        lightStrips.add(new LightStrip(controller, strip, currentAnimation, currentColor));
    }

    /**
     * Adds a strip to the group
     * @param strip The LightStrip to be added
     */
    public void addStrip(LightStrip strip) {
        lightStrips.add(strip);
    }

    /**
     * Removes all LightStrips from the group
     */
    public void clearStrips() {
        lightStrips.clear();
    }

    /**
     * Sets the animation of the group
     * @param animation The animation to be set
     */
    public void setAnimation(Animations animation) {
        for (LightStrip lightStrip : lightStrips) {
            lightStrip.setAnimation(animation);
        }
    }

    /**
     * Sets the color of the strip
     * <p>Note: Some animations override this
     * @param r The red value of the color
     * @param g The green value of the color
     * @param b The blue value of the color
     */
    public void setColor(int r, int g, int b) {
        for (LightStrip lightStrip : lightStrips) {
            lightStrip.setColor(r, g, b);
        }
    }

    /**
     * Sets the color of the strip
     * <p>Note: Some animations override this
     * @param color The color the strip should be
     */
    public void setColor(Color color) {
        for (LightStrip lightStrip : lightStrips) {
            lightStrip.setColor(color);
        }
    }

    /**
     * Sets both the animation and color of the group
     * @param animation The animation to be set
     * @param color The color the strip should be
     */
    public void setAniCol(Animations animation, Color color) {
        setAnimation(animation);
        setColor(color);
    }
}
