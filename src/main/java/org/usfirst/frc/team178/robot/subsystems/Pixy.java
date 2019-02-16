/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team178.robot.subsystems;

import org.usfirst.frc.team178.robot.Robot;
import org.usfirst.frc.team178.robot.RobotMap;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Pixy extends Arduino {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public static int lLoc;
  public static int rLoc;

  public Pixy(int address)//use robotmap values
  {
    super(I2C.Port.kOnboard, address);
    lLoc = 0;
    rLoc = 0;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public byte[] receiveMessage(int address)
  {
    byte[] dataFromArduino = new byte[1];
    received = !arduino.read(address, 1, dataFromArduino);
    for (byte b : dataFromArduino) {//gets data in bytes from arduino and converts to binary 
      String s1 = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    //  System.out.print(s1 + ", ");
      } 
    //  System.out.println(": data from arduino");
      return dataFromArduino;
  }

  public static void updateTargetValues() {
    byte[] coordinatesFromPixy = Robot.pixy1.receiveMessage(RobotMap.pixy1Address);//gets first x value from pixy
    String x1Binary = ((Byte) coordinatesFromPixy[0]).toString();
    System.out.println(x1Binary);
    int counter = 1;
    int x1 = 0;
    for (int i = x1Binary.length() - 1; i >= 0; i--) {//converts binary to base 10
      if (x1Binary.charAt(i) == '1') {
        x1 = x1 + counter;
      }
      counter = counter * 2;
    }
    counter = 0;
    
    // delay
    coordinatesFromPixy = Robot.pixy2.receiveMessage(RobotMap.pixy2Address);//gets second x value from pixy 
    String x2Binary = ((Byte) coordinatesFromPixy[0]).toString();
    counter = 1;
    int x2 = 0;
    for (int i = x2Binary.length() - 1; i >= 0; i--) {//converts binary to base 10
      if (x2Binary.charAt(i) == '1') {
        x2 = x2 + counter;
      }
      counter = counter * 2;
    }
    lLoc = x1;
    rLoc = x2;
    
    if (x1 > x2)//determines which one is on the left 
    {
      lLoc = x2;
      rLoc = x1;
    }
    else 
    {
      lLoc = x1;
      rLoc = x2;
    }
  }

  public static int getLeft()
  {
    System.out.println("Left: " + lLoc);
    return lLoc;
  }

  public static int getRight()
  {
    System.out.println("Right: " + rLoc);
    return rLoc;
  }

}
