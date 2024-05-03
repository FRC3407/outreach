// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;

public class TurnCommand extends Command {
    private DriveSubsystem m_driveSubsystem;
    private double startAngle;
    private double angle;
    private boolean turnLeft;

  /** Creates a new TurnRightCommand. */
  public TurnCommand(boolean turnLeft, double angle, DriveSubsystem driveSubsystem) {
    m_driveSubsystem = driveSubsystem;
    addRequirements(m_driveSubsystem);
    this.angle = angle;
    this.turnLeft = turnLeft;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startAngle = m_driveSubsystem.getAngle();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(turnLeft) {
      m_driveSubsystem.arcadeDrive(0.0, -.5);
    }else {
      m_driveSubsystem.arcadeDrive(0.0, .5);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_driveSubsystem.arcadeDrive(0.0, 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Math.abs(startAngle-m_driveSubsystem.getAngle()) > angle) {
      return true;
    }
    return false;

  }
}
