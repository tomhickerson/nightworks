package net.sf.nightworks.behave;

import net.sf.nightworks.MobProg;
import net.sf.nightworks.Nightworks;
import net.sf.nightworks.enums.PlayerAchievement;
import net.sf.nightworks.quests.SimpleGetQuest;
import net.sf.nightworks.quests.SimpleKillQuest;
import net.sf.nightworks.quests.SimpleQuest;
import net.sf.nightworks.quests.plains.PlainQuests;
import net.sf.nightworks.util.TextBuffer;

import java.util.ArrayList;

import static net.sf.nightworks.ActComm.do_say;
import static net.sf.nightworks.Comm.act;
import static net.sf.nightworks.Comm.send_to_char;
import static net.sf.nightworks.DB.number_range;
import static net.sf.nightworks.Nightworks.*;
import static net.sf.nightworks.Update.*;
import static net.sf.nightworks.Update.gain_exp;

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
                        if (q instanceof SimpleKillQuest) {
                            if (ch.pcdata.questmob == -1) {
                                finish_loaded_quest(ch, mob, q);
                            }
                        } else if (q instanceof SimpleGetQuest) {
                            do_say(mob, "Maybe you have something for me...?");
                        }
                    } else if (q.getLoadedQuest().doesQualify(ch)) {
                        // print out the preamble
                        do_say(mob, q.getPreamble());
                    } else if (q.getLoadedQuest().doesQualifyExceptLevel(ch)) {
                        // print out the come back later phrase
                        do_say(mob, q.getLoadedQuest().getComeBackLater());
                    }
                }
            }
        }
    }

    private static void finish_loaded_quest(CHAR_DATA ch, CHAR_DATA mob, SimpleQuest q) {
        do_say(mob, q.getEpilogue());
        MobProg.clear_quest(ch);
        // add the questpoints
        if (q.getLoadedQuest().getQuestPoints() > 0) {
            ch.pcdata.questpoints += q.getLoadedQuest().getQuestPoints();
            int exp = q.getLoadedQuest().getQuestPoints() * number_range(1 , 5);
            send_to_char("You receive {W" + exp + "{x experience points.\n", ch);
            gain_exp(ch, exp);
        } else {
            send_to_char("You receive {W100{x experience points.\n", ch);
            gain_exp(ch, 100);
        }
        // add the exp - at this point we get a range between 1 and 5 and multiply QPs
        // or just a set number of exp

        // add the gold and silver
        if (q.getLoadedQuest().getGold() > 0) {
            ch.gold += q.getLoadedQuest().getGold();
            send_to_char("You receive {W" + q.getLoadedQuest().getGold() + "{x gold pieces.\n", ch);
        }
        if (q.getLoadedQuest().getSilver() > 0) {
            ch.silver += q.getLoadedQuest().getSilver();
            send_to_char("You receive {W" + q.getLoadedQuest().getSilver() + "{x silver pieces.\n", ch);
        }
        // add the virtue or vice
        if (q.getLoadedQuest().getVice() >= 0) {
            // 0-6 is a certain vice
            switch(q.getLoadedQuest().getVice()) {
                case 0:
                    updateLust(ch);
                case 1:
                    updateEnvy(ch);
                case 2:
                    updateSloth(ch);
                case 3:
                    updatePride(ch);
                case 4:
                    updateAvarice(ch);
                case 5:
                    updateGluttony(ch);
                case 6:
                    updateAnger(ch);
            }
        }
        if (q.getLoadedQuest().getVirtue() >= 0) {
            // 0-6 is a certain virtue
        }
        ch.pcdata.achievements.add(PlayerAchievement.lookupAchievement(q.getAchievement()));
    }

    public static void speech_prog_loadedquest_mob(Nightworks.CHAR_DATA mob, Nightworks.CHAR_DATA ch, String speech) {

    }

    public static void give_prog_loadedquest_mob(Nightworks.CHAR_DATA mob, Nightworks.CHAR_DATA ch, Nightworks.OBJ_DATA obj) {

    }
}
