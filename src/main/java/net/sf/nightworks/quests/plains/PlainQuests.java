package net.sf.nightworks.quests.plains;

import net.sf.nightworks.enums.PlayerAchievement;
import net.sf.nightworks.enums.PlayerMessage;
import net.sf.nightworks.quests.SimpleCollectQuest;
import net.sf.nightworks.quests.SimpleGetQuest;
import net.sf.nightworks.quests.SimpleKillQuest;
import net.sf.nightworks.quests.SimpleQuest;

import static net.sf.nightworks.ActComm.do_say;
import static net.sf.nightworks.Comm.act;
import static net.sf.nightworks.Comm.send_to_char;
import static net.sf.nightworks.Nightworks.*;
import static net.sf.nightworks.Update.gain_exp;

public class PlainQuests {

    public static SimpleGetQuest returnDruidQuest() {
        SimpleGetQuest sgq = new SimpleGetQuest(1,"Find the Fennel for the Druid");
        sgq.setQuestPoints(10); // do we need it any more? or do we set it in the reward?
        sgq.setVnumToGet(313);
        sgq.setAdvancedPreamble(null);
        sgq.setPreamble("{WDear Adventurer, glad to see you!  Could you help me find some {Yfennel{W \nfor a stew I'm making?  Please say '{YI accept{W' if so...{x");
        sgq.setDuration(30);
        sgq.setEpilogue("Thank you so much!  I'll really enjoy this stew!");
        sgq.setAcceptMessage("So excellent!  Please stay on the plains and you should find it somewhere \nnearby!");
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
            int humility = ch.pcdata.virtues.updateVirtue(VIRTUE_HUMILITY);
            if (humility > 0) {
                ch.pcdata.virtues.humility++;
                send_to_char(PlayerMessage.FEEL_BY_HUMILITY.getMessage(), ch);
            }
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
            int anger = ch.pcdata.vices.updateVice(VICE_ANGER);
            if (anger > 0) {
                ch.pcdata.vices.anger++;
                send_to_char(PlayerMessage.CONSUMED_BY_ANGER.getMessage(), ch);
            }
        };
    }

    public static SimpleCollectQuest returnPilgrimQuest() {
        SimpleCollectQuest scq = new SimpleCollectQuest(3, "Find alms for the Blind Pilgrim");
        return scq;
    }

}
