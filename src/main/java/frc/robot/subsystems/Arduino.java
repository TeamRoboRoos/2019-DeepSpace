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
import frc.robot.customobjects.LightStrip;
import frc.robot.customobjects.LightStripGroup;
import frc.robot.customobjects.LightStrip.Animations;
import frc.robot.customobjects.LightStrip.Strips;

/**
 * Add your docs here.
 */
public class Arduino extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private SerialPort arduino;
  private boolean sensors[] = {false, false, false, false, false, false};
  private boolean fakeData = true;

  private LightStrip top4;
  private LightStripGroup bottom, top;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public Arduino() {
    arduino = new SerialPort(9600, RobotMap.arduinoPort);
    // top4 = new LightStrip(arduino, Strips.TOP_CENTRE, Animations.RAINBOWRANDOM, Color.MAGENTA);
    // top4 = new LightStrip(arduino, Strips.TOP_CENTRE, Animations.RAINBOWRANDOM, Color.MAGENTA);
    // top4 = new LightStrip(arduino, Strips.TOP_CENTRE, Animations.RAINBOWRANDOM, Color.MAGENTA);
    // top4 = new LightStrip(arduino, Strips.TOP_CENTRE, Animations.RAINBOWRANDOM, Color.MAGENTA);
    // top4 = new LightStrip(arduino, Strips.TOP_CENTRE, Animations.RAINBOWRANDOM, Color.MAGENTA);
    // top4 = new LightStrip(arduino, Strips.TOP_CENTRE, Animations.RAINBOWRANDOM, Color.MAGENTA);

    // top4 = new LightStrip(arduino, Strips.SIDE_LEFT, Animations.BLINK, Color.MAGENTA);
    // top4 = new LightStrip(arduino, Strips.FRONT_LEFT, Animations.BLINK, Color.GREEN);
    // top4 = new LightStrip(arduino, Strips.SIDE_RIGHT, Animations.BLINK, Color.BLUE);
    // top4 = new LightStrip(arduino, Strips.FRONT_RIGHT, Animations.BLINK, Color.RED);
    // top4 = new LightStrip(arduino, Strips.TOP_LEFT, Animations.BLINK, Color.YELLOW);
    // top4 = new LightStrip(arduino, Strips.TOP_RIGHT, Animations.BLINK, Color.CYAN);
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
