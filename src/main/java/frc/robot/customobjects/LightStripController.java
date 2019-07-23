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
 * Thread designed to handle the ordered sending of data to the arduino
 */
public class LightStripController extends Thread {
  // private long lastExecution = 0;
  // private final int EXE_DELAY = 000*1000; //Delay in micro seconds (1,000,000us /s)
  private final int INIT_DELAY = 2000; //Delay for arduino to boot in milli seconds

  private Queue<byte[]> queue; //The queue to store all waiting commands
  private SerialPort arduino; //The actual arduino serial port

  /**
   * Creates the LightStripController, initializes variables and creates a thread for timing and sending data to the arduino
   * @param arduino Serial port of the arduino
   */
  public LightStripController(SerialPort arduino) {
    super("Arduino Command Sender");
    this.arduino = arduino;
    queue = new LinkedList<byte[]>();
    // lastExecution = RobotController.getFPGATime();
    this.start(); //Start the thread
  }

  /**
   * Thread to handle sending data to the arduino
   */
  public void run() {
    try {
      Thread.sleep(INIT_DELAY); //Wait for arduino to boot
    } catch (Exception e) {
      e.printStackTrace();
      this.interrupt();
    }
    while (!Thread.interrupted()) {
      // long time = RobotController.getFPGATime(); //Store current time
      if (/*lastExecution + EXE_DELAY <= time && */peekNextCommand() != null) { //Check next command exists
        byte[] command = getNextCommand();
        arduino.write(command, command.length); //Send command
        // outputCommandDebug(command);
        // lastExecution = time;
      } else { //If there was nothing to send, sleep for 100ms
        try {
          Thread.sleep(100);
        } catch (Exception e) {
          e.printStackTrace();
          this.interrupt();
        }
      }
    }

  }

  /**
   * Check the next command in the queue. Returns null if empty
   * @return The next command in the queue
   */
  public byte[] peekNextCommand() {
    return queue.peek();
  }

  /**
   * Gets the next command from the queue, removing it from the queue. Returns null if empty
   * @return The next command in the queue
   */
  private byte[] getNextCommand() {
    return queue.poll();
  }

  /**
   * Adds a command to the queue
   * @param data The byte array to be added to the queue
   */
  public void addToQueue(byte[] data) {
    queue.offer(data);
  }

  /**
   * Checks if the queue is empty
   * @return The empty status of the queue
   */
  public boolean getQueueEmpty() {
    return queue.isEmpty();
  }

  /**
   * Formats and prints the given command
   * @param command The byte array to be printed
   */
  public void outputCommandDebug(byte[] command) {
    System.out.println("CMD-NEW");
    for(byte segment : command) {
      System.out.println(segment);
    }
    System.out.println("CMD-END");
  }
}