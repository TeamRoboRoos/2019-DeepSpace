/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.customobjects;

import java.awt.Color;

/**
 * Add your docs here.
 */
public class LightStrip {
  public enum Strips {
    FRONT_LEFT  (1),
    FRONT_RIGHT (6),
    SIDE_LEFT   (0),
    SIDE_RIGHT  (5),
    TOP_LEFT    (2),
    TOP_CENTRE  (3),
    TOP_RIGHT   (4);

    public int index;
    Strips(int index) {
      this.index = index;
    }
  }

  public enum Animations {
    BLACKOUT        (0),
    SOLIDCOLOR      (1),
    BLINK           (2),
    FADE            (3),
    CARNIVAL        (4),
    RAINBOWCYCLE    (5),
    RAINBOWRANDOM   (6),
    RIPPLECENTRE    (7),
    RIPPLEFORWARDS  (8),
    RIPPLEREVERSE   (9),
    ALTERNATINGFADE (10),
    WAVE            (11);

    public int index;

    Animations(int index) {
      this.index = index;
    }
  }

  private final int MODE_ANIMATION = 0;
  private final int MODE_COLOR = 1;

  private LightStripController controller;
  private Strips strip;
  private Animations currentAnimation;
  private Color currentColor;

  public LightStrip(LightStripController controller, Strips strip, Animations defaultAnimation, Color defaultColor) {
    this.controller = controller;
    this.strip = strip;
    // this.currentAnimation = defaultAnimation;
    // this.currentColor = defaultColor;
    setAnimation(defaultAnimation);
    setColor(defaultColor);
  }

  public void setAnimation(Animations animation) {
    if (currentAnimation != animation) {
      currentAnimation = animation;
      // arduino.writeString(assembleCommand(currentAnimation));
      // arduino.write(assembleCommand(animation), 3);
      controller.addToQueue(assembleCommand(animation));
    }
  }

  public void setColor(Color color) {
    if (currentColor != color) {
      currentColor = color;
      // arduino.writeString(assembleCommand(currentColor));
      // arduino.write(assembleCommand(color), 5);
      controller.addToQueue(assembleCommand(color));
    }
  }

  public Color getCurrentColor() {
    return currentColor;
  }

  public Animations getCurrentAnimation() {
    return currentAnimation;
  }

  // private byte intToByte(int i) {
  //   return (byte) ((byte) i & (byte) 0xFF);
  //   // return (i<0) ? 256+i : i;
  // }

  private byte[] assembleCommand(Animations animation) {
    return new byte[] {MODE_ANIMATION, (byte)strip.index, (byte)animation.index};
  }

  private byte[] assembleCommand(Color color) {
    // for (int i=0; i < 256; i++) {
    //   System.out.println("NOTICE: " + (byte)i);
    //   SmartDashboard.putRaw("Bytes " + i, new byte[] {(byte)i});
    // }
    // System.out.println("NOTICE: " + b);
    return new byte[]{MODE_COLOR, (byte)strip.index, (byte)color.getRed(), (byte)color.getGreen(), (byte)color.getBlue()};
  }
}
