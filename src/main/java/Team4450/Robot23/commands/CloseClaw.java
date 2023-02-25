package Team4450.Robot23.commands;

import Team4450.Lib.SynchronousPID;
import Team4450.Lib.Util;
import Team4450.Robot23.subsystems.Claw;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Moves the arm to a target position.
 */
public class CloseClaw extends CommandBase 
{
    public static double DEFAULT_POSITION = 100;

    private final Claw      claw;
    private double          targetPostion;    // Revolutions of motor.
    private SynchronousPID  controller = new SynchronousPID(.01, 0, 0);
    private final double    tolerance = .5, maxPower = .3;
    private double          lastTimeCalled;

    public CloseClaw(Claw claw, double target)
    {
        Util.consoleLog();

        this.claw = claw;

        this.targetPostion = target;

        addRequirements(claw);
    }

    @Override
    public void initialize()
    {
        Util.consoleLog();

        controller.reset();

        controller.setSetpoint(targetPostion);

        controller.setOutputRange(-maxPower, maxPower);

        SmartDashboard.putBoolean("CloseClaw", true);

        lastTimeCalled = Util.timeStamp();
    }

    @Override
    public void execute()
    {
        double time = Util.getElaspedTime(lastTimeCalled);

        lastTimeCalled = Util.timeStamp();

        double power = controller.calculate(claw.getPosition(), time);

        claw.setPower(power);
    }

    @Override
    public boolean isFinished()
    {
        return controller.onTarget(tolerance);
    }

    @Override
    public void end(boolean interrupted) 
    {
        claw.stop();

        Util.consoleLog("interrupted=%b", interrupted);

        SmartDashboard.putBoolean("CloseClaw", false);
    }
}
