package Team4450.Robot23.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import Team4450.Lib.Util;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static Team4450.Robot23.Constants.*;

public class Winch  extends SubsystemBase
{
    private CANSparkMax     motor = new CANSparkMax(WINCH_MOTOR, MotorType.kBrushless);
    private RelativeEncoder encoder = motor.getEncoder();
    private DigitalInput    lowLimitSwitch = new DigitalInput(WINCH_SWITCH);

    private final double    WINCH_MAX = 1000;

    public Winch()
    {
        Util.consoleLog();

        motor.setInverted(true);

        // Winch will start at max up position so that is encoder zero. Encoder max will
        // be winch at lowest position.

        encoder.setPosition(0);
    }

    /**
     * Set winch power.
     * @param power + is up, - is down.
     */
    public void setPower(double power)
    {
        // If power negative, which means go down, check limit switch stop if true.
        // If power positive, which means go up, check encoder for max height, stop if there.

        if ((power < 0 && lowLimitSwitch.get()) || (power > 0 && encoder.getPosition() >= WINCH_MAX)) power = 0;

        if ((power < 0 && encoder.getPosition() >= WINCH_MAX) || (power > 0 && encoder.getPosition() <= 0)) power = 0;

        if (lowLimitSwitch.get()) encoder.setPosition(0);

        power = Util.clampValue(power, .30);

        motor.set(power);
    }

    public void stop()
    {
        motor.stopMotor();
    }

    /**
     * Return Winch encoder position.
     * @return Position in revolutions.
     */
    public double getPosition()
    {
        return encoder.getPosition();
    }

    /**
     * Returns state of low position limit switch.
     * @return True is at low position.
     */
    public boolean getLowSwitch()
    {
        return lowLimitSwitch.get();
    }

    public void updateDS()
    {

    }
}
