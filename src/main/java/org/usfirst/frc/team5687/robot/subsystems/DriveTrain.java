package org.usfirst.frc.team5687.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.RobotMap;
import org.usfirst.frc.team5687.robot.commands.DriveWith2Joysticks;

public class DriveTrain extends Subsystem implements PIDSource {
    private RobotDrive drive;
    private VictorSP leftFrontMotor;
    private VictorSP leftRearMotor;
    private VictorSP rightFrontMotor;
    private VictorSP rightRearMotor;
    private Encoder rightEncoder;
    private Encoder leftEncoder;

    private PIDSourceType pidSourceType = PIDSourceType.kDisplacement;

    public DriveTrain() {
        leftFrontMotor = new VictorSP(RobotMap.Drive.LEFT_MOTOR_FRONT);
        leftRearMotor = new VictorSP(RobotMap.Drive.LEFT_MOTOR_REAR);
        rightFrontMotor = new VictorSP(RobotMap.Drive.RIGHT_MOTOR_FRONT);
        rightRearMotor = new VictorSP(RobotMap.Drive.RIGHT_MOTOR_REAR);

        leftFrontMotor.setInverted(Constants.Drive.LEFT_MOTOR_FRONT_INVERTED);
        leftRearMotor.setInverted(Constants.Drive.LEFT_MOTOR_REAR_INVERTED);
        rightFrontMotor.setInverted(Constants.Drive.RIGHT_MOTOR_FRONT_INVERTED);
        rightRearMotor.setInverted(Constants.Drive.RIGHT_MOTOR_REAR_INVERTED);

        drive = new RobotDrive(leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor);
        rightEncoder = initializeEncoder(RobotMap.Drive.RIGHT_ENCODER_CHANNEL_A, RobotMap.Drive.RIGHT_ENCODER_CHANNEL_B, Constants.Encoders.RightDrive.REVERSED, Constants.Encoders.RightDrive.INCHES_PER_PULSE);
        leftEncoder = initializeEncoder(RobotMap.Drive.LEFT_ENCODER_CHANNEL_A, RobotMap.Drive.LEFT_ENCODER_CHANNEL_B, Constants.Encoders.LeftDrive.REVERSED, Constants.Encoders.LeftDrive.INCHES_PER_PULSE);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveWith2Joysticks());
    }

    public void resetDriveEncoders() {
        rightEncoder.reset();
        leftEncoder.reset();
    }

    public double getLeftDistance() {
        return leftEncoder.getDistance();
    }

    public double getLeftTicks() {
        return leftEncoder.getRaw();
    }

    public double getLeftRate() {
        return leftEncoder.getRate();
    }

    public double getLeftSpeed() {
        return leftFrontMotor.getSpeed();
    }

    public double getLeftRPS() {
        return getLeftRate() / (Constants.Encoders.Defaults.PULSES_PER_ROTATION * Constants.Encoders.Defaults.INCHES_PER_PULSE);
    }

    public double getRightDistance() {
        return rightEncoder.getDistance();
    }

    public double getRightTicks() {
        return rightEncoder.getRaw();
    }

    public double getRightRate() {
        return rightEncoder.getRate();
    }

    public double getRightSpeed() {
        return rightFrontMotor.getSpeed();
    }

    public double getRightRPS() {
        return getRightRate() / (Constants.Encoders.Defaults.PULSES_PER_ROTATION * Constants.Encoders.Defaults.INCHES_PER_PULSE);
    }

    private Encoder initializeEncoder(int channelA, int channelB, boolean reversed, double distancePerPulse) {
        Encoder encoder = new Encoder(channelA, channelB, reversed, Encoder.EncodingType.k4X);
        encoder.setDistancePerPulse(distancePerPulse);
        encoder.setMaxPeriod(Constants.Encoders.Defaults.MAX_PERIOD);
        encoder.setSamplesToAverage(Constants.Encoders.Defaults.SAMPLES_TO_AVERAGE);
        encoder.reset();
        return encoder;
    }

    /**
     * @return average of leftDistance and rightDistance
     */
    public double getDistance() {
        return (getLeftDistance()+getRightDistance())/2;
    }
    public double getSpeed() {
        return (getLeftSpeed()+getRightSpeed())/2;
    }

    public void sendAmpDraw() {
        SmartDashboard.putNumber("Current Draw/LeftFrontMotor", Robot.powerDistributionPanel.getCurrent(RobotMap.Drive.PDP_LEFT_MOTOR_FRONT));
        SmartDashboard.putNumber("Current Draw/LeftRearMotor", Robot.powerDistributionPanel.getCurrent(RobotMap.Drive.PDP_LEFT_MOTOR_REAR));
        SmartDashboard.putNumber("Current Draw/RightFrontMotor", Robot.powerDistributionPanel.getCurrent(RobotMap.Drive.PDP_RIGHT_MOTOR_FRONT));
        SmartDashboard.putNumber("Current Draw/RightRearMotor", Robot.powerDistributionPanel.getCurrent(RobotMap.Drive.PDP_RIGHT_MOTOR_REAR));
    }

    /**
     * Run drive motors at specified speeds
     * @param leftSpeed  desired speed for left motors
     * @param rightSpeed desired speed for right motors
     */
    public void tankDrive(double leftSpeed, double rightSpeed) {
        tankDrive(leftSpeed, rightSpeed, false);
    }

    public void tankDrive(double leftSpeed, double rightSpeed, boolean overrideCaps) {
        if (!overrideCaps) {
            // Limit change in leftSpeed to +/- ACCELERATION_CAP
            leftSpeed = Math.min(leftSpeed, leftFrontMotor.get() + Constants.Limits.ACCELERATION_CAP);
            leftSpeed = Math.max(leftSpeed, leftFrontMotor.get() - Constants.Limits.ACCELERATION_CAP);

            // Limit change in rightSpeed to +/- ACCELERATION_CAP
            rightSpeed = Math.min(rightSpeed, rightFrontMotor.get() + Constants.Limits.ACCELERATION_CAP);
            rightSpeed = Math.max(rightSpeed, rightFrontMotor.get() - Constants.Limits.ACCELERATION_CAP);
        }

        // DriverStation.reportError("Driving " + leftSpeed + ", " + rightSpeed, false);
        drive.tankDrive(leftSpeed, rightSpeed, false);


        SmartDashboard.putNumber("drive/Right distance", getRightDistance());
        SmartDashboard.putNumber("drive/Left distance", getLeftDistance());

        SmartDashboard.putNumber("drive/Right ticks", getRightTicks());
        SmartDashboard.putNumber("drive/Left ticks", getLeftTicks());

        SmartDashboard.putNumber("drive/Right rate", getRightRate());
        SmartDashboard.putNumber("drive/Left rate", getLeftRate());

        SmartDashboard.putNumber("drive/Right speed", getRightSpeed());
        SmartDashboard.putNumber("drive/Left speed", getLeftSpeed());

        SmartDashboard.putNumber("drive/Right RPS" , getRightRPS());
        SmartDashboard.putNumber("drive/Left RPS" , getLeftRPS());
    }

    public void setSafeMode(boolean enabled) {
        drive.setSafetyEnabled(enabled);
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        pidSourceType = pidSource;
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return pidSourceType;
    }

    @Override
    public double pidGet() {
        return pidSourceType.equals(PIDSourceType.kDisplacement) ? getDistance() : getSpeed();
    }
}
