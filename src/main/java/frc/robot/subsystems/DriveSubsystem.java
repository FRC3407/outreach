// drive susy

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.Encoder;
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

    ADIS16470_IMU gyro = new ADIS16470_IMU();
    Encoder encoder = new Encoder(1, 2);

    public DriveSubsystem() {
        left.setInverted(true);
        encoder.setDistancePerPulse(1.0);
    }

    public void arcadeDrive(double speed, double rotation) {
        driveController.arcadeDrive(speed, rotation);
    }

    public double getGyroAngle() {
        return gyro.getAngle();
    }

    public double getDistance() {
        return encoder.getDistance();
    }
}