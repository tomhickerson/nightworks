package net.sf.nightworks.player;

/**
 * Holder class for the Seven Virtues.  Note there are no getters/setters, this is intentional,
 * something we are trying out with the new questing system
 * thinking about where to update, possibly just in the code
 */
public class Virtues {
    public Virtues() {
        faith = 0;
        hope = 0;
        compassion = 0;
        humility = 0;
        justice = 0;
        sacrifice = 0;
        fortitude = 0;
    }

    public int faith, hope, compassion, humility, justice, sacrifice, fortitude;
}
