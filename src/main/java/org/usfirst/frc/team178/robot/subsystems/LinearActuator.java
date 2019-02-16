/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team178.robot.subsystems;

import org.usfirst.frc.team178.robot.RobotMap;
import org.usfirst.frc.team178.robot.commands.MoveActuator;
import org.usfirst.frc.team178.robot.Robot;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class LinearActuator extends Subsystem {
  public PWM actuator;

  public LinearActuator () {
    actuator = new PWM(RobotMap.linearActuator);
  }

  public void setPosition(double val) {
    actuator.setPosition(val);
  }

  public double getPosition() {
    return actuator.getPosition();
  }

  public void moveActuator(boolean movingForward) {
    double currentPosition = getPosition();
    if (movingForward) {
      if (currentPosition < 1 || currentPosition >= 0) {
        currentPosition+=0.004;
        setPosition(currentPosition);
      }
    } else {
      if (currentPosition <= 1 || currentPosition > 0) {
        currentPosition-=0.004;
        setPosition(currentPosition);
      }
    }
    System.out.println("Actuator " + getPosition());
  }



  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    //setDefaultCommand(new SetActuator(.5));
  }
}