package net.sf.nightworks.player;

import static net.sf.nightworks.DB.number_range;

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

    public static int addToVirtue(int currentValue) {
        int ret = 0;
        if (currentValue < 50) {
            ret = 1;
        } else if (currentValue < 75) {
            ret = number_range(1, 3) == 2 ? 1 : 0;
        } else if (currentValue < 100) {
            ret = number_range(1, 10) == 5 ? 1 : 0;
        }
        return ret;
    }

    public int updateVirtue(int virtue) {
        switch (virtue) {
            case 0:
                return addToVirtue(this.faith);
            case 1:
                return addToVirtue(this.hope);
            case 2:
                return addToVirtue(this.compassion);
            case 3:
                return addToVirtue(this.humility);
            case 4:
                return addToVirtue(this.justice);
            case 5:
                return addToVirtue(this.sacrifice);
            case 6:
                return addToVirtue(this.fortitude);
            default:
                return 0;
        }
    }
}
