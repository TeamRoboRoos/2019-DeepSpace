/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.robot.RobotMap;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.VideoSource;

/**
 * Add your docs here.
 */
public class Telemetry extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private AHRS navx;
  private PowerDistributionPanel pdp;
  private AnalogInput pressureSensorReading;

  private UsbCamera camera1;
  private UsbCamera camera2;
  private VideoSink server;
  private boolean cameraStatus = false;

  public Telemetry() {
    navx = new AHRS(SPI.Port.kMXP);

    pdp = new PowerDistributionPanel(RobotMap.pdp);
    LiveWindow.add(pdp);

    camera1 = CameraServer.getInstance().startAutomaticCapture(0);
    camera2 = CameraServer.getInstance().startAutomaticCapture(1);
    server = CameraServer.getInstance().addServer("Switched camera");
    camera1.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
    camera2.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
    pressureSensorReading = new AnalogInput(RobotMap.pressureSensor);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  
  public double getGyroAngle() {
    return navx.getAngle();
   }

  public void switchCamera() {
    if(cameraStatus = false) {
      cameraStatus = true;
      server.setSource(camera2);
    } else {
      cameraStatus = false;
      server.setSource(camera1);
    }
  }

  public double getPressure() {
    return 250 * (pressureSensorReading.getVoltage()/5) - 25;
  }
}
