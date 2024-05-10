// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WackyWavyInflatableArmFlailingTubeManSubsystem extends SubsystemBase {
  public final int TUBE_MAN_CHANNEL = 0;

  DigitalOutput wackyWavyInflatableArmFlailingTubeMan;
  boolean isPulsing;

  /** Creates a new WackyWavyInflatableArmFlailingTubeMan. */
  public WackyWavyInflatableArmFlailingTubeManSubsystem() {
    wackyWavyInflatableArmFlailingTubeMan = new DigitalOutput(TUBE_MAN_CHANNEL);
    isPulsing = false;
  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }

  public void dance() {
    wackyWavyInflatableArmFlailingTubeMan.set(true);
    System.out.println(wackyWavyInflatableArmFlailingTubeMan.get());

  }
  public void stopDance() {
    wackyWavyInflatableArmFlailingTubeMan.set(false);

  }
  
}
