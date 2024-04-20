// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.VisionSubsystem;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  DriveSubsystem driving = new DriveSubsystem(); 
  XboxController xbox = new XboxController(0); 
  VisionSubsystem vision = new VisionSubsystem();
  
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

  private void configureBindings() {
    Trigger one_trigger = new Trigger(() -> vision.isTagVisible(1));
    one_trigger.onTrue(new );
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}