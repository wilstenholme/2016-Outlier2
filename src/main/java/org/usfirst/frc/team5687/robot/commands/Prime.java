package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.utils.Color;

import static org.usfirst.frc.team5687.robot.Robot.oi;

/**
 * Command group to prime the shooter subsystem
 * @author wil
 */
public class Prime extends CommandGroup{

    public Prime() {
        addSequential(new SetLEDStrip(Color.BLACK));
        addSequential(new EnableFlashlight());
        addSequential(new EnableVisionLight());
        addSequential(new PrimeBoulder());
        addSequential(new ToggleShooter(true));
        addSequential(new SetLEDStrip(Color.BLACK));
    }
}
