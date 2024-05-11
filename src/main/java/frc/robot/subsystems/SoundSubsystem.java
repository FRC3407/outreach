// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.networktables.IntegerPublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.Publisher;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



public class SoundSubsystem extends SubsystemBase {
  public NetworkTableInstance instance;
  public IntegerPublisher selectorPub;
  public IntegerPublisher playPub;

  /** Creates a new SoundSubsystem. */
  public SoundSubsystem() {
    instance = NetworkTableInstance.getDefault();
    NetworkTable table = instance.getTable("Sounds");
    selectorPub = table.getIntegerTopic("Selector").publish();
    playPub = table.getIntegerTopic("Play").publish();
  }

  @Override
  public void periodic() {
    send(0);
    // This method will be called once per scheduler run
  }

  public void send(int selector) {
    selectorPub.set(selector);
    playPub.set(1);
    // playPub.set(0);
  }
}
