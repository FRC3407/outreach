// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.SoundSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.WackyWavyInflatableArmFlailingTubeManSubsystem;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.FlailCommand;
import frc.robot.commands.SoundCommand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  DriveSubsystem driving = new DriveSubsystem(); 
  SoundSubsystem sound = new SoundSubsystem();

  WackyWavyInflatableArmFlailingTubeManSubsystem wacky = new WackyWavyInflatableArmFlailingTubeManSubsystem();
  VisionSubsystem vision = new VisionSubsystem(driving, wacky, sound);
  CommandXboxController xbox = new CommandXboxController(0); 
  
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
    xbox.a().onTrue(new SoundCommand(sound));
  }

  public Command getAutonomousCommand() {
    return new FlailCommand(wacky);
  }
}
