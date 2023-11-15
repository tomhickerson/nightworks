package net.sf.nightworks.behave;

import net.sf.nightworks.Nightworks;
import net.sf.nightworks.util.TextBuffer;

import java.util.ArrayList;

import static net.sf.nightworks.ActComm.do_say;
import static net.sf.nightworks.Comm.act;
import static net.sf.nightworks.DB.number_range;
import static net.sf.nightworks.Nightworks.TO_ROOM;

/**
 * MobProg2, where we will add more generic coded actions
 * This will include MobDeath, MobTaunt, MobGreet and MobChat
 * These classes will be rendered much the same way as in MobProg.java
 */
public class MobProg2 {

    public static boolean load_death_prog_saying(Nightworks.CHAR_DATA ch) {
        ArrayList<String> sayings = MobDeath.getSayings(ch.pIndexData.vnum);
        int pick = number_range(0, sayings.size());
        do_say(ch, sayings.get(pick));
        return false;
    }

    public static boolean load_death_prog_action(Nightworks.CHAR_DATA ch) {
        ArrayList<String> doings = MobDeath.getDoings(ch.pIndexData.vnum);
        int pick = number_range(0, doings.size());
        // TextBuffer buf = new TextBuffer();
        // buf.sprintf(doings.get(pick), ch.name);
        // we'll always need a single $n in each action
        act(doings.get(pick), ch, null, null, TO_ROOM);
        return false;
    }
}
