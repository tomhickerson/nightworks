package net.sf.nightworks.behave;

import net.sf.nightworks.Nightworks;
import net.sf.nightworks.quests.SimpleQuest;
import net.sf.nightworks.util.TextBuffer;

import java.util.ArrayList;

import static net.sf.nightworks.ActComm.do_say;
import static net.sf.nightworks.Comm.act;
import static net.sf.nightworks.DB.number_range;
import static net.sf.nightworks.Nightworks.*;

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

    public static boolean death_prog_roach_shaman(Nightworks.CHAR_DATA ch) {
        do_say(ch, "You do not understand.  This is our sacrifice.  " +
                "This is our tribute.  So that the way is closed.  Taking her " +
                "back means opening the way.  All will be uncertain.  The way will be open");
        return false;
    }

    public static void fight_prog_roach_adept(Nightworks.CHAR_DATA mob, Nightworks.CHAR_DATA ch) {
        if (mob.hit < (mob.max_hit * 0.45) && mob.hit > (mob.max_hit * 0.55)) {
            do_say(mob, "Protect the sacrifice!");
        }
    }

    public static void greet_prog_loadedquest_mob(Nightworks.CHAR_DATA mob, Nightworks.CHAR_DATA ch) {
        if (IS_NPC(ch)) {
            return;
        }
        ArrayList<SimpleQuest> quests = quest_table.get(mob.pIndexData.vnum);
        if (quests != null) {
            for (SimpleQuest q : quests) {
                if (q.getLoadedQuest() != null) {
                    if (IS_SET(ch.act, PLR_QUESTOR) && ch.pcdata.questid == q.getAchievement()) {
                        // figure out if the quest is done

                        // generate the reward

                    } else if (q.getLoadedQuest().doesQualify(ch)) {
                        // print out the preamble

                    } else if (q.getLoadedQuest().doesQualifyExceptLevel(ch)) {
                        // print out the come back later phrase

                    }
                }
            }
        }
    }

    public static void speech_prog_loadedquest_mob(Nightworks.CHAR_DATA mob, Nightworks.CHAR_DATA ch, String speech) {

    }

    public static void give_prog_loadedquest_mob(Nightworks.CHAR_DATA mob, Nightworks.CHAR_DATA ch, Nightworks.OBJ_DATA obj) {

    }
}
