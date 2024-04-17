// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class DriveCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem m_drivetrain;

  DoubleSupplier m_speedSupplier;
  DoubleSupplier m_rotationSupplier;

  /**
   * Creates a new DriveTrainCommand.
   * @param m_speedSupplier The speed used by this command.
   * @param m_rotationSupplier The rotation used by this command.
   * @param m_subsystem The subsystem used by this command.
   */
  public DriveCommand(DoubleSupplier m_speedSupplier, DoubleSupplier m_rotationSupplier, DriveSubsystem m_subsystem) {
    this.m_speedSupplier = m_speedSupplier;
    this.m_rotationSupplier = m_rotationSupplier;
    m_drivetrain = m_subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() 
  {
    m_drivetrain.arcadeDrive(m_speedSupplier.getAsDouble(), m_rotationSupplier.getAsDouble());

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}