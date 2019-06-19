/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.customobjects;

import java.util.LinkedList;
import java.util.Queue;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SerialPort;

/**
 * Add your docs here.
 */
public class LightStripController extends Thread {
  private long lastExecution = 0;
  private final int EXE_DELAY = 100000; //Delay in micro seconds (100000)

  private Queue<byte[]> queue;
  private SerialPort arduino;

  public LightStripController(SerialPort arduino) {
    super("Arduino Command Sender");
    this.arduino = arduino;
    queue = new LinkedList<byte[]>();
    this.start();
  }

  public void run() {
    while (!Thread.interrupted()) {
      if (lastExecution + EXE_DELAY <= RobotController.getFPGATime()) {
        byte[] command = getNextCommand();
        if(command != null) {
          arduino.write(command, command.length);
          outputCommandDebug(command);
        }
        lastExecution = RobotController.getFPGATime();
      }
    }

  }

  public byte[] peekNextCommand() {
    return queue.peek();
  }

  private byte[] getNextCommand() {
    return queue.poll();
  }

  public void addToQueue(byte[] data) {
    queue.offer(data);
  }

  public void outputCommandDebug(byte[] command) {
    System.out.println("CMD-NEW");
    for(byte segment : command) {
      System.out.println(segment);
    }
    System.out.println("CMD-COMPLETE");
  }
}
