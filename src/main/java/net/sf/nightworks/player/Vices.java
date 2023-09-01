package net.sf.nightworks.player;

import net.sf.nightworks.Nightworks;
import net.sf.nightworks.enums.PlayerMessage;

import static net.sf.nightworks.Comm.send_to_char;
import static net.sf.nightworks.DB.number_range;
import static net.sf.nightworks.Nightworks.VICE_ANGER;

/**
 * Holder class for the Seven Vices.  More to come.
 * maybe put the update functions in here as well?
 */
public class Vices {

    public Vices() {
        lust = 0;
        envy = 0;
        sloth = 0;
        pride = 0;
        avarice = 0;
        gluttony = 0;
        anger = 0;
    }

    public int lust, envy, sloth, pride, avarice, gluttony, anger;

    public static int addToVice(int currentValue) {
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

    public int updateVice(int vice) {
        switch (vice) {
            case 0:
                return addToVice(this.lust);
            case 1:
                return addToVice(this.envy);
            case 2:
                return addToVice(this.sloth);
            case 3:
                return addToVice(this.pride);
            case 4:
                return addToVice(this.avarice);
            case 5:
                return addToVice(this.gluttony);
            case 6:
                return addToVice(this.anger);
            default:
                return 0;
        }
    }
}
