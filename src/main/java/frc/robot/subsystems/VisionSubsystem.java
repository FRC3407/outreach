// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.FloatArrayTopic;
import edu.wpi.first.networktables.IntegerArraySubscriber;
import edu.wpi.first.networktables.IntegerArrayTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {
  public NetworkTableInstance instance;
  public IntegerArrayTopic topic1;
  public final IntegerArraySubscriber idSub;
  private boolean startAdd;
  private ArrayList<Long> idList = new ArrayList<Long>();
  private long lastTagSeen;
  private boolean canContinue;

  // public NetworkTable table;
  /** Creates a new VisionSubsystem. */
  public VisionSubsystem() {
    System.out.println("aaaaaaaaaaa");
    instance = NetworkTableInstance.getDefault();
    topic1 = instance.getIntegerArrayTopic("/Vision Server/Pipelines/bv2024/ids");
    idSub = topic1.subscribe(new long[0]);
    startAdd = false;
    lastTagSeen = 0;
    canContinue = false;
  }

  @Override
  public void periodic() {
        System.out.println(startAdd + "," + canContinue + "," + idList);

    // This method will be called once per scheduler run
    long[] ids = idSub.get();
    // if (ids.length != 0)
    setTagList();
  }

  // Search for a tag ID in the tags list
  public boolean isTagVisible(int tagID) {
    long[] ids = idSub.get();
    if (ids.length == 0) return false;
    if (ids.length == 1 && ids[0] == tagID) return true;
    for (int i=0;i<ids.length;i++) {
      if (ids[i]==tagID)
        return true;
    }
    return false;
  }

  public long whichTagVisible() {
    long[] ids = idSub.get();
    if (ids.length == 0)
    {
      return -1;
    }
    return ids[0];
  }
  
  public void setTagList() {
    long id = whichTagVisible();
    if (id == -1) {
      return;
    }
    if(id == 5) {
      startAdd = true;
      idList.clear();
    }
    if (id == 35) {
      startAdd = false;
      canContinue = false;
    }
    if(id == 11) {
      canContinue = true;
    }
    if(startAdd && canContinue && id != 11 && id != 5) {
      idList.add(id);
      System.out.println("TAG SCANNED: " + id);
      canContinue = false;
    }
  }

  // public void onTagVisible(int tagID, Command cmd) { }

}
