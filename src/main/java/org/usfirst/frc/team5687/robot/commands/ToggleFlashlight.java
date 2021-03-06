package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.Robot;
import static org.usfirst.frc.team5687.robot.Robot.lights;

/**
 * Command to toggle ring light
 * @author wil
 */
public class ToggleFlashlight extends Command {

    public ToggleFlashlight() {
        requires(lights);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        lights.toggleFlashlight();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
