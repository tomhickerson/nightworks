package net.sf.nightworks.quests.plains;

import net.sf.nightworks.enums.PlayerAchievement;
import net.sf.nightworks.quests.*;

import java.util.ArrayList;

import static net.sf.nightworks.ActComm.do_say;
import static net.sf.nightworks.Comm.act;
import static net.sf.nightworks.Comm.send_to_char;
import static net.sf.nightworks.DB.*;
import static net.sf.nightworks.Handler.obj_to_room;
import static net.sf.nightworks.Nightworks.*;
import static net.sf.nightworks.Update.*;

public class PlainQuests {

    public static SimpleGetQuest returnDruidQuest() {
        SimpleGetQuest sgq = new SimpleGetQuest(1,"Find the Fennel for the Druid");
        sgq.setQuestPoints(10); // do we need it any more? or do we set it in the reward?
        sgq.setVnumToGet(313);
        sgq.setAdvancedPreamble(null);
        sgq.setPreamble("{WDear Adventurer, glad to see you!  Could you help me find some {Yfennel{W for a stew I'm making?  Please say '{YI accept{W' if so...{x");
        sgq.setDuration(30);
        sgq.setEpilogue("Thank you so much!  I'll really enjoy this stew!");
        sgq.setAcceptMessage("So excellent!  Please stay on the plains and you should find it somewhere nearby!");
        // setting how to qualify here
        sgq.setQualify(qualifiesFennel());
        sgq.setReward(deliverFennel());
        sgq.setAchievement(PlayerAchievement.FIND_FENNEL_FOR_THE_DRUID.getId());
        return sgq;
    }
    
    private static SimpleQuest.qualify qualifiesFennel() {
        return ch -> ch.level >= 5 && (IS_GOOD(ch) || IS_NEUTRAL(ch)) && !IS_NPC(ch) && !ch.pcdata.achievements.contains(PlayerAchievement.FIND_FENNEL_FOR_THE_DRUID);
    }

    private static SimpleQuest.reward deliverFennel() {
        return ch -> {
            ch.pcdata.questpoints += 10;
            ch.silver += 10;
            send_to_char("You receive {W10{x quest points and {W10{x silver from the Druid.\n", ch);
            // maybe add some humility
            updateHumility(ch);
        };
    }

    public static SimpleKillQuest returnHermitQuest() {
        SimpleKillQuest skq = new SimpleKillQuest(2, "Slay a Rabbit for the Hermit");
        skq.setVnumToKill(309); // kill the rabbit
        skq.setAdvancedPreamble(getHermitPreamble());
        skq.setDuration(30);
        skq.setEpilogue("Aha, I see you've done it!  Please accept my reward.");
        skq.setAcceptPhrase("yes");
        skq.setAcceptMessage("Great, now please go forth and kill it!");
        skq.setQualify(qualifiesHermit());
        skq.setReward(killTheRabbit());
        skq.setAchievement(PlayerAchievement.KILL_RABBIT_FOR_THE_HERMIT.getId());
        return skq;
    }

    private static SimpleQuest.advPreamble getHermitPreamble() {
        return (ch, mob) -> {
            act("$n stands up and looks about.", mob, null, null, TO_ROOM);
            do_say(mob, "There's a rascally rabbit around here that needs killing.  Do you think you're up to the task?  Just say YES if so.");
        };
    }

    private static SimpleQuest.qualify qualifiesHermit() {
        return ch -> ch.level >= 5
                && (IS_EVIL(ch) || IS_NEUTRAL(ch))
                && !IS_NPC(ch)
                && !ch.pcdata.achievements.contains(PlayerAchievement.KILL_RABBIT_FOR_THE_HERMIT);
    }

    private static SimpleQuest.reward killTheRabbit() {
        return ch -> {
            ch.pcdata.questpoints += 10;
            gain_exp(ch, 100);
            ch.silver += 40;
            send_to_char("You receive {W10{x quest points and {W100{x experience.\n", ch);
            send_to_char("You also receive {W40{x silver from the Hermit.\n", ch);
            updateAnger(ch);
        };
    }

    public static SimpleCollectQuest returnPilgrimQuest() {
        SimpleCollectQuest scq = new SimpleCollectQuest(3, "Find alms for the Blind Pilgrim");
        scq.setNumberToCollect(5);
        scq.setVnumContainer(-1);
        scq.setVnumToCollect(314);
        // vnum for the pilgrim is 351
        scq.setAdvancedPreamble(null);
        scq.setPreamble("Oh my stars!  I've just found a hole in me bag, and all my alms have scattered all over " +
                "the plains!  I don't suppose I can ask you for help to gather them up?  Please just say YES if so.");
        scq.setAcceptPhrase("yes");
        scq.setAcceptMessage("Thank you kindly!  They should be on the plains somewhere...");
        scq.setAchievement(PlayerAchievement.FIND_ALMS_FOR_THE_PILGRIM.getId());
        scq.setDuration(15); // will an area refresh clean out the items? hmm
        scq.setQualify(qualifyPilgrim());
        scq.setReward(getTheAlms());
        scq.setQuestSetup(setupPilgrim());
        scq.setEpilogue("I'm so happy you were able to do it!  Now I can deliver these to Ofcol!  Thank you again, kind stranger!");
        return scq;
    }

    private static SimpleQuest.reward getTheAlms() {
        return ch -> {
            ch.pcdata.questpoints += 10;
            gain_exp(ch, 150);
            send_to_char("You receive {W10{x quest points and {W150{x experience.\n", ch);
            // update some compassion
            updateCompassion(ch);
        };
    }

    private static SimpleQuest.qualify qualifyPilgrim() {
        return ch -> ch.level > 5 &&
                !IS_NPC(ch) &&
                !ch.pcdata.achievements.contains(PlayerAchievement.FIND_ALMS_FOR_THE_PILGRIM);
    }

    private static SimpleDefendQuest.setup setupPilgrim() {
        return new SimpleDefendQuest.setup() {
            @Override
            public void run() {
                // place six alms in different places
                final int[] rooms = {302, 304, 307, 327, 313, 320};
                for (int i = 0; i < 6; i++) {
                    OBJ_DATA obj = create_object(get_obj_index(314), 4);
                    obj_to_room(obj, get_room_index(rooms[i]));
                }
            }
        };
    }

    public static SimpleHuntQuest killMoreRabbits() {
        SimpleHuntQuest shq = new SimpleHuntQuest(4, "Kill more rabbits for the Hermit");
        shq.setNumberToKill(5);
        shq.setVnumToKill(-1);
        shq.setRooms(new ArrayList<>());
        shq.setQuestSetup(null);
        shq.setAdvancedPreamble(null);
        shq.setDuration(25);
        shq.setAcceptPhrase("yes");
        shq.setAchievement(-1);
        shq.setAcceptMessage("Great, now get out there and kill them rabbits!");
        return null;
    }

}
