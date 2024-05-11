// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;

public class TurnCommand extends Command {
    private DriveSubsystem m_driveSubsystem;
    private double desiredAngle;
    private double angle;
    private boolean turnLeft;
    private Timer time;
    private PIDController control;

  /** Creates a new TurnRightCommand. */
  public TurnCommand(double angle, DriveSubsystem driveSubsystem) {
    m_driveSubsystem = driveSubsystem;
    addRequirements(m_driveSubsystem);
    this.angle = angle;
    this.turnLeft = turnLeft;
    time = new Timer();
    control = new PIDController(0.009, 0.004, 0.002);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    time.start();
    time.reset();
    desiredAngle = m_driveSubsystem.getAngle() - angle;
    control.setSetpoint(desiredAngle);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.print("tuuurning");
    m_driveSubsystem.arcadeDrive(0.0, MathUtil.clamp(control.calculate(m_driveSubsystem.getAngle()), -.5, .5));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_driveSubsystem.arcadeDrive(0.0, 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return time.hasElapsed(4.0);

  }
}
