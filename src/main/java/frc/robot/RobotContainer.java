// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.commands.DriveCommand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class RobotContainer {
  DriveSubsystem driving = new DriveSubsystem(); 
  VisionSubsystem vision = new VisionSubsystem(driving);
  XboxController xbox = new XboxController(0); 
  
  public RobotContainer() {
    driving.setDefaultCommand(
      new DriveCommand(
        () -> xbox.getLeftY(), // Speed
        () -> xbox.getLeftX(), // Rotation
        driving
      )
    );
    configureBindings();
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
