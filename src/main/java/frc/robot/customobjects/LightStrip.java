/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.customobjects;

import java.awt.Color;

/**
 * Class designed to handle the settings and communication of a light strip
 */
public class LightStrip {
  /**
   * Enum to label the strip indexs with their locations
   */
  public enum Strips {
    FRONT_LEFT  (1),
    FRONT_RIGHT (6),
    SIDE_LEFT   (0),
    SIDE_RIGHT  (5),
    TOP_LEFT    (2),
    TOP_CENTRE  (3),
    TOP_RIGHT   (4);

    public byte index;
    Strips(int index) {
      this.index = (byte)index;
    }
  }

  /**
   * Enum to label the animation indexs
   */
  public enum Animations {
    BLACKOUT          (0),
    SOLID_COLOR       (1),
    BLINK             (2),
    FADE              (3),
    CARNIVAL          (4),
    RAINBOW_CYCLE     (5),
    RAINBOW_RANDOM    (6),
    RIPPLE_CENTRE     (7),
    RIPPLE_FORWARDS   (8),
    RIPPLE_REVERSE    (9),
    ALTERNATING_FADE  (10),
    // WAVE              (11),
    // BOUNCE            (12),
    // GROUP_SCROLL      (13),
    // STILL_RAINBOW     (14),
    // RAINBOW_FADE      (15),
    // RAINBOW_GROUP_SCROLL (16),
    // RAINBOW_ALTERNATING_FADE (17),
    ;
    public byte index;
    Animations(int index) {
      this.index = (byte)index;
    }
  }

  private LightStripController controller; //The controller which handles actually sending data to the arduino
  private Strips strip; //The strip which this class belongs to
  private Animations currentAnimation; //The current animation that should be running on this strip
  private byte[] currentColor = {0,0,0}; //The current color this strip should be (overriden by some animations)

  /**
   * Creates a light strip and initializes it to its default values
   * @param controller The light strip contoller object
   * @param strip The strip this class belongs to
   * @param defaultAnimation The initial animation that should be run
   * @param defaultColor The initial color that this strip should be
   */
  public LightStrip(LightStripController controller, Strips strip, Animations defaultAnimation, Color defaultColor) {
    this.controller = controller;
    this.strip = strip;
    currentAnimation = defaultAnimation;
    setColor(defaultColor);
  }

  /**
   * Sets the animation of the strip
   * @param animation The animation that should be run
   */
  public void setAnimation(Animations animation) {
    //Check animation is different to avoid spam
    if (currentAnimation != animation) {
      currentAnimation = animation;
      update();
    }
  }

  /**
   * Sets the color of the strip
   * <p>Note: Some animations override this
   * @param color The color the strip should be
   */
  public void setColor(Color color) {
    setColor(color.getRed(), color.getGreen(), color.getBlue());
  }

  /**
   * Sets the color of the strip
   * <p>Note: Some animations override this
   * @param r The red value of the color
   * @param g The green value of the color
   * @param b The blue value of the color
   */
  public void setColor(int r, int g, int b) {
    byte[] rgb = {(byte)r, (byte)g, (byte)b};
    //Check color is different to avoid spam
    if ((currentColor[0] != rgb[0]) || (currentColor[1] != rgb[1]) || (currentColor[2] != rgb[2])) {
      currentColor[0] = rgb[0];
      currentColor[1] = rgb[1];
      currentColor[2] = rgb[2];
      update();
    }
  }

  /**
   * Assembles the command and adds it to the controller's queue
   */
  private void update() {
    controller.addToQueue(assembleCommand());
  }

  /**
   * Gets the currently running animation
   * @return The current animation
   */
  public Animations getCurrentAnimation() {
    return currentAnimation;
  }

  /**
   * Assembles the command to be sent to the arduino from the currently stored values
   * @return The byte array to be sent to the arduino
   */
  private byte[] assembleCommand() {
    return new byte[] {strip.index, currentColor[0], currentColor[1], currentColor[2], currentAnimation.index};
  }
}