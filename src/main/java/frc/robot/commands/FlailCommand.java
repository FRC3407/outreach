// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.WackyWavyInflatableArmFlailingTubeManSubsystem;

public class FlailCommand extends Command {
  Timer timer;
  WackyWavyInflatableArmFlailingTubeManSubsystem wackyWaver;
  /** Creates a new FlailCommand. */
  public FlailCommand(WackyWavyInflatableArmFlailingTubeManSubsystem f) {
    timer = new Timer();
    wackyWaver = f;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    wackyWaver.dance();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    wackyWaver.dance();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    wackyWaver.stopDance();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.hasElapsed(5.0);
  }
}
