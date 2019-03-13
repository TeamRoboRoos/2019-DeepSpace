/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.customobjects;

import java.awt.Color;

import edu.wpi.first.wpilibj.SerialPort;

/**
 * Add your docs here.
 */
public class LightStrip {
  // public class LightStripGroup {
  //   private LightStrip[] lightStrips;

  //   public LightStripGroup(LightStrip[] lightStrips) {
  //     this.lightStrips = lightStrips;
  //   }

  //   public void setAnimations(Animations animation) {
  //     for (int i = 0; i < lightStrips.length; i++) {
  //       lightStrips[i].setAnimation(animation);
  //     }
  //   }
  // }

  public enum Strips {
    FRONT_LEFT  (0),
    FRONT_RIGHT (6),
    SIDE_LEFT   (1),
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

  private SerialPort arduino;
  private Strips strip;
  private Animations currentAnimation;
  private Color currentColor;

  public LightStrip(SerialPort arduino, Strips strip, Animations defaultAnimation, Color defaultColor) {
    this.arduino = arduino;
    this.strip = strip;
    this.currentAnimation = defaultAnimation;
    this.currentColor = defaultColor;
    setAnimation(currentAnimation);
    setColor(currentColor);
  }

  public void setAnimation(Animations animation) {
    currentAnimation = animation;
    arduino.writeString(assembleCommand(currentAnimation));
  }

  public void setColor(Color color) {
    currentColor = color;
    arduino.writeString(assembleCommand(currentColor));
  }

  public Color getCurrentColor() {
    return currentColor;
  }

  public Animations getCurrentAnimation() {
    return currentAnimation;
  }

  private String assembleCommand(Animations animation) {
    return Integer.toString(MODE_ANIMATION) + Integer.toString(strip.index) + Integer.toString(animation.index);
  }

  private String assembleCommand(Color color) {
    return Integer.toString(MODE_COLOR) + Integer.toString(strip.index) + Integer.toString(color.getRed())
        + Integer.toString(color.getGreen()) + Integer.toString(color.getBlue());
  }
}
