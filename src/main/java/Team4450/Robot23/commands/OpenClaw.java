package Team4450.Robot23.commands;

import Team4450.Lib.Util;
import Team4450.Robot23.subsystems.Claw;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Drops the arm by running winch down until low position limit
 * switch reports true.
 */
public class OpenClaw extends CommandBase 
{
    private final Claw      claw;

    public OpenClaw(Claw claw)
    {
        Util.consoleLog();

        this.claw = claw;

        addRequirements(claw);
    }

    @Override
    public void initialize()
    {
        Util.consoleLog();

        claw.setPower(.50);

        SmartDashboard.putBoolean("OpenClaw", true);
    }

    @Override
    public boolean isFinished()
    {
        return claw.getSwitch();
    }

    @Override
    public void end(boolean interrupted) 
    {
        claw.stop();

        Util.consoleLog("interrupted=%b", interrupted);

        SmartDashboard.putBoolean("OpenClaw", false);
    }
}

