package cs540.checkers;

/**
 * This interface defines the methods of a countdown clock.
 * <p>
 * A countdown clock to implement this interface updates its state only when 
 * one of its methods is invoked. The countdown clock will not generate events. 
 * Code which uses the game clock must poll its status periodically. 
 * @author David He
 */
public interface CountdownClock
{
    /** The value indicating the PAUSED state */
    public static final int PAUSED    = 0;
    /** The value indicating the RUNNING state */
    public static final int RUNNING   = 1;
    /** The value indicating the FINISHED state */
    public static final int FINISHED  = 2;

    /** Resets this countdown clock. The remaining time will be cleared to zero. */
    public void reset();

    /** Pauses this countdown clock. */
    public void pause();

    /** Resumes this countdown clock. */
    public void resume();

    /** 
     * Returns how much time, in milliseconds, remains in this countdown. 
     * @return          how much time remains in this countdown, in milliseconds
     */
    public long getTimeRemain();

    /**
     * Gets the state of this countdown clock.
     * @return          the integer specifying the state of this clock
     */
    public int getState();

    /**
     * Gets the time, in milliseconds, that this clock counts down from.
     * @return          the time this clock counts down from, in milliseconds
     */
    public long getDelay();

    /**
     * Sets the time, in milliseconds, that this clock counts down from.
     * @param delay     the time this clock counts down from, in milliseconds
     */
    public void setDelay(long delay);
}
