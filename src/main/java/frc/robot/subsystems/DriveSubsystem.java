// drive susy

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class DriveSubsystem extends SubsystemBase {
    WPI_TalonSRX leftA = new WPI_TalonSRX(0);
    WPI_TalonSRX leftB = new WPI_TalonSRX(1);
    WPI_TalonSRX rightA = new WPI_TalonSRX(2);
    WPI_TalonSRX rightB = new WPI_TalonSRX(3);

    MotorControllerGroup left = new MotorControllerGroup(leftA, leftB);
    MotorControllerGroup right = new MotorControllerGroup(rightA, rightB);
    
    DifferentialDrive driveController = new DifferentialDrive(left, right);  
    public DriveSubsystem() {
        left.setInverted(true);
    }

    public void arcadeDrive(double speed, double rotation) {
        driveController.arcadeDrive(speed, rotation);
    }
}