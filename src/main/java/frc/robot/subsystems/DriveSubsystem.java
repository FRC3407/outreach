// drive susy

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class DriveSubsystem extends SubsystemBase {
    WPI_TalonSRX left = new WPI_TalonSRX(0);
    WPI_TalonSRX leftB = new WPI_TalonSRX(1);
    WPI_TalonSRX right = new WPI_TalonSRX(2);
    WPI_TalonSRX rightB = new WPI_TalonSRX(3);
    private ADIS16470_IMU m_gyro;


    DifferentialDrive driveController = new DifferentialDrive(left, right);  
    public DriveSubsystem() {
        leftB.follow(left);
        rightB.follow(right);
        left.setInverted(true);
        leftB.setInverted(true);
        m_gyro = new ADIS16470_IMU();
    }

    public void arcadeDrive(double speed, double rotation) {
        driveController.arcadeDrive(speed, rotation);
    }

    public double getAngle() {
        return m_gyro.getAngle();
    }
}
