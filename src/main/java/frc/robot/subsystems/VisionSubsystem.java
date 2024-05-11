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
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.DriveForwardCommand;
import frc.robot.commands.FlailCommand;
import frc.robot.commands.TurnCommand;
import frc.robot.commands.SoundCommand;

public class VisionSubsystem extends SubsystemBase {
    public final int TAG_START = 5;
    public final int TAG_CONTINUE = 11;
    public final int TAG_EXECUTE = 35;
    public final int TAG_DRIVE = 492; // the tag that looks like <:P
    public final int TAG_TURNRIGHT = 3;
    public final int TAG_TURNLEFT = 15;
    public final int TAG_SPINLEFT = 62;
    public final int TAG_SPINRIGHT = 44;
    public final int TAG_DRIVEBACKWARDS = 166;
    public final int TAG_WACKYWAVYINFLATEABLEARMFLAILINGTUBEMAN = 189;
    public final int BUZZER_CHANNEL = 1;


    public DriveSubsystem m_DriveSubsystem;
    public SoundSubsystem m_SoundSubsystem;
    public WackyWavyInflatableArmFlailingTubeManSubsystem m_WackySubsystem;
    public NetworkTableInstance instance;
    public IntegerArrayTopic topic1;
    public final IntegerArraySubscriber idSub;
    private boolean startAdd;
    private ArrayList<Long> idList = new ArrayList<Long>();
    private long lastTagSeen;
    private boolean canContinue;
    private boolean isRunning;
    private int currentTag;
    private Timer time;
    public DigitalOutput buzzer;

    // public NetworkTable table;
    /** Creates a new VisionSubsystem. */
    public VisionSubsystem(DriveSubsystem driveSubsystem, WackyWavyInflatableArmFlailingTubeManSubsystem wackySubystem, SoundSubsystem soundSubsystem) {
        instance = NetworkTableInstance.getDefault();
        topic1 = instance.getIntegerArrayTopic("/Vision Server/Pipelines/bv2024/ids");
        idSub = topic1.subscribe(new long[0]);
        startAdd = true;
        lastTagSeen = 0;
        canContinue = false;
        isRunning = false;
        currentTag = 0;
        idList.add(15L);
        m_DriveSubsystem = driveSubsystem;
        m_WackySubsystem = wackySubystem;
        m_SoundSubsystem = soundSubsystem;
        buzzer = new DigitalOutput(BUZZER_CHANNEL);
        time = new Timer();
        time.start();
        time.reset();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        long[] ids = idSub.get();
        // if (ids.length != 0)
        setTagList();
        if(whichTagVisible()>-1) {
            buzz();
        } else {
            unbuzz();
        }
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
                    nextCommand = new DriveForwardCommand(false, 1.0, m_DriveSubsystem);
                    break;
                
                case TAG_TURNRIGHT:
                    nextCommand = new TurnCommand(90.0, m_DriveSubsystem);
                    break;

                case TAG_TURNLEFT:
                    nextCommand = new TurnCommand(-90.0, m_DriveSubsystem);
                    break;

                case TAG_SPINLEFT:
                    nextCommand = new TurnCommand(180.0, m_DriveSubsystem);
                    break;

                case TAG_SPINRIGHT:
                    nextCommand = new TurnCommand(-180.0, m_DriveSubsystem);
                    break;

                case TAG_DRIVEBACKWARDS:
                    nextCommand = new DriveForwardCommand(true, 1.0, m_DriveSubsystem);
                    break;

                case TAG_WACKYWAVYINFLATEABLEARMFLAILINGTUBEMAN:
                    nextCommand = new FlailCommand(m_WackySubsystem);

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

    /**
     * 
     */
    public void buzz() {
            buzzer.set(true); 
    }

    public void unbuzz() {
        buzzer.set(false);
    }

    // public void onTagVisible(int tagID, Command cmd) { }

}
