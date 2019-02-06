/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team178.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.ArrayList;
import java.util.Arrays;

import org.usfirst.frc.team178.robot.RobotMap;
import org.usfirst.frc.team178.robot.RobotMap.SubsystemIndex;

/**
 * Add your docs here.
 */
public class Arduino extends Subsystem {
  private I2C arduino;

  public Arduino() {
    arduino = new I2C(I2C.Port.kOnboard, RobotMap.ArduinoAddress); // check these values
  }

  public void sendMessage(SubsystemIndex subsystem, String pattern) {
    String message = subsystem.ordinal() + pattern;
    message = message.toLowerCase();
    System.out.println(message);
    arduino.writeBulk(message.getBytes());
    System.out.println(arduino.addressOnly());
  }

  public void receiveMessage()
  {
    byte[] dataFromPixy = new byte[1];
    boolean success = arduino.read(RobotMap.ArduinoAddress, 1, dataFromPixy);
    System.out.println(success);
    for (byte b : dataFromPixy) {
    String s1 = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    System.out.print(s1 + ", ");
    } 
    System.out.println();
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
