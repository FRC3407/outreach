// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.FloatArrayTopic;
import edu.wpi.first.networktables.IntegerArraySubscriber;
import edu.wpi.first.networktables.IntegerArrayTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.DriveForwardCommand;

public class VisionSubsystem extends SubsystemBase {
    public final int TAG_START = 5;
    public final int TAG_CONTINUE = 11;
    public final int TAG_EXECUTE = 35;
    public final int TAG_DRIVE = 492; // the tag that looks like <:P


    public DriveSubsystem m_DriveSubsystem;
    public NetworkTableInstance instance;
    public IntegerArrayTopic topic1;
    public final IntegerArraySubscriber idSub;
    private boolean startAdd;
    private ArrayList<Long> idList = new ArrayList<Long>();
    private long lastTagSeen;
    private boolean canContinue;
    private boolean isRunning;
    private int currentTag;

    // public NetworkTable table;
    /** Creates a new VisionSubsystem. */
    public VisionSubsystem(DriveSubsystem driveSubsystem) {
        instance = NetworkTableInstance.getDefault();
        topic1 = instance.getIntegerArrayTopic("/Vision Server/Pipelines/bv2024/ids");
        idSub = topic1.subscribe(new long[0]);
        startAdd = true;
        lastTagSeen = 0;
        canContinue = false;
        isRunning = false;
        currentTag = 0;
        idList.add(492L);
        m_DriveSubsystem = driveSubsystem;
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
        if (ids.length == 0)
            return false;
        if (ids.length == 1 && ids[0] == tagID)
            return true;
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == tagID)
                return true;
        }
        return false;
    }

    public long whichTagVisible() {
        long[] ids = idSub.get();
        if (ids.length == 0) {
            return -1;
        }
        return ids[0];
    }

    public void scheduleCommands() {
        System.out.println("Scheduling commands");
        System.out.println(idList);


        List<Command> cmdList = new ArrayList<>();
        for (Long id : idList) {
            Command nextCommand = null;
            switch (id.intValue()) {
                case TAG_DRIVE:
                System.out.println("im shmovin'");
                    nextCommand = new DriveForwardCommand(1.0, m_DriveSubsystem);
                    break;
            
                default:
                    break;
            }

            if (nextCommand!=null) cmdList.add(nextCommand);
        }
        Command seqCommand = new SequentialCommandGroup(cmdList.toArray(new Command[0]));
        seqCommand.schedule();

        for (int i=0; i<idList.size(); i++) {
            int id = idList.get(i).intValue();
        }
    }

    public void setTagList() {
        long id = whichTagVisible();
        if (id == -1) {
            return;
        }
        if (id == TAG_START) {
            startAdd = true;
            idList.clear();
            isRunning = false;
        }
        if (id == TAG_EXECUTE) {
            startAdd = false;
            // canContinue = false;
            if (!isRunning) {
                isRunning = true;
                scheduleCommands();
            }
        }
        if (id == TAG_CONTINUE) {
            canContinue = true;
        }
        if (startAdd && canContinue && id != TAG_CONTINUE && id != TAG_START) {
            idList.add(id);
            System.out.println("TAG SCANNED: " + id);
            canContinue = false;
        }
    }

    // public void onTagVisible(int tagID, Command cmd) { }

}
