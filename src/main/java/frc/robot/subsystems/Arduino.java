/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.awt.Color;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.customobjects.LightStrip.Animations;
import frc.robot.customobjects.LightStrip.Strips;
import frc.robot.customobjects.LightStrip;
import frc.robot.customobjects.LightStripController;
import frc.robot.customobjects.LightStripGroup;

/**
 * Add your docs here.
 */
public class Arduino extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private SerialPort arduino;
  private boolean sensors[] = {false, false, false, false, false, false};
  private boolean fakeData = true;

  private LightStripController controller;
  public LightStripGroup bottom, top;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public Arduino() {
    arduino = new SerialPort(9600, RobotMap.arduinoPort);

    controller = new LightStripController(arduino);

    top = new LightStripGroup(controller, Animations.FADE, Color.MAGENTA);
    top.addStrip(Strips.TOP_LEFT);
    top.addStrip(Strips.TOP_RIGHT);
    top.addStrip(Strips.TOP_CENTRE);

    bottom = new LightStripGroup(controller, Animations.SOLID_COLOR, Color.YELLOW);
    bottom.addStrip(Strips.FRONT_LEFT);
    bottom.addStrip(Strips.FRONT_RIGHT);
    bottom.addStrip(Strips.SIDE_LEFT);
    bottom.addStrip(Strips.SIDE_RIGHT);

    // LightStrip s1 = new LightStrip(controller, Strips.FRONT_RIGHT, Animations.FADE, Color.CYAN);
    // LightStrip s2 = new LightStrip(controller, Strips.SIDE_RIGHT, Animations.FADE, Color.DARK_GRAY);
    // LightStrip s3 = new LightStrip(controller, Strips.TOP_RIGHT, Animations.FADE, Color.GREEN);
    // LightStrip s4 = new LightStrip(controller, Strips.TOP_CENTRE, Animations.FADE, Color.PINK);
    // LightStrip s5 = new LightStrip(controller, Strips.TOP_LEFT, Animations.FADE, Color.ORANGE);
    // LightStrip s6 = new LightStrip(controller, Strips.FRONT_LEFT, Animations.FADE, Color.MAGENTA);
    // LightStrip s7 = new LightStrip(controller, Strips.SIDE_LEFT, Animations.FADE, Color.YELLOW);

  }

  public String readString() {
    return arduino.readString();
  }

  public void writeString(String data) {
    arduino.writeString(data);
  }

  public int readInt() {
    return Integer.parseInt(readString());
  }

  /**
   * Sets sensors value as big-endian binary sequence (boolean array) representation of integer
   * @param data
   */
  private void updateVars(int data) {
    int p = sensors.length;
    for(int i=0; i < p; i++) {
      double pow = Math.pow(2, p-i-1);
      //System.out.println(data-pow);
      if(data - pow >= 0) {
        data -= pow;
        sensors[i] = true;
      } else {
        sensors[i] = false;
      }
    }
    //System.out.println(sensors[0] + " " + sensors[1] + " " + sensors[2]);
  }

  public void updateVars() {
    if(!fakeData) {
      updateVars(readInt());
    } else {
      //System.out.println("Warning: Fake arduino data being used!");
      updateVars((int)SmartDashboard.getNumber("FakeArduinoNumber", 0));
    }
  }

  private boolean[] getSensors() {
    updateVars();
    return sensors;
  }

  public boolean getLeft() {
    return getSensors()[0];
  }

  public boolean getMid() {
    return getSensors()[1];
  }

  public boolean getRight() {
    return getSensors()[2];
  }
}
