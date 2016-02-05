package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.DriveWith2Joysticks;

/**
 * Created by Caleb on 1/22/2016.
 */
public class DriveTrain extends Subsystem {

    private RobotDrive drive;
    private VictorSP leftMotor;
    private VictorSP rightMotor;

    public DriveTrain(){
        leftMotor = new VictorSP(RobotMap.leftMotors);
        rightMotor = new VictorSP(RobotMap.rightMotors);
        drive = new RobotDrive(leftMotor,rightMotor);
    }




    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveWith2Joysticks());
    }

    public void sendAmpDraw() {
        SmartDashboard.putNumber("Current Draw/LeftMotor1", Robot.powerDistributionPanel.getCurrent(RobotMap.PDP_LEFT_MOTOR1)); //TODO: is this really where I'm getting current from? Check for all 4
        SmartDashboard.putNumber("Current Draw/LeftMotor2", Robot.powerDistributionPanel.getCurrent(RobotMap.PDP_LEFT_MOTOR2));
        SmartDashboard.putNumber("Current Draw/RightMotor1", Robot.powerDistributionPanel.getCurrent(RobotMap.PDP_RIGHT_MOTOR1));
        SmartDashboard.putNumber("Current Draw/RightMotor2", Robot.powerDistributionPanel.getCurrent(RobotMap.PDP_RIGHT_MOTOR2));
    }



    /**
     * Run drive motors at specified speeds
     * @param leftSpeed desired speed for left motors
     * @param rightSpeed desired speed for right motors
     */
    public void tankDrive(double leftSpeed, double rightSpeed){
        // Limit change in leftSpeed to +/- ACCELERATION_CAP
        leftSpeed = Math.min(leftSpeed, leftMotor.get() + Constants.Limits.ACCELERATION_CAP);
        leftSpeed = Math.max(leftSpeed, leftMotor.get() - Constants.Limits.ACCELERATION_CAP);

        // Limit change in rightSpeed to +/- ACCELERATION_CAP
        rightSpeed = Math.min(rightSpeed, rightMotor.get() + Constants.Limits.ACCELERATION_CAP);
        rightSpeed = Math.max(rightSpeed, rightMotor.get() - Constants.Limits.ACCELERATION_CAP);

        drive.tankDrive(leftSpeed, rightSpeed, false);
    }
}
