package net.sf.nightworks.quests.plains;

import net.sf.nightworks.Nightworks;
import net.sf.nightworks.enums.PlayerAchievement;
import net.sf.nightworks.enums.PlayerMessage;
import net.sf.nightworks.quests.SimpleGetQuest;
import net.sf.nightworks.quests.SimpleQuest;

import static net.sf.nightworks.Comm.send_to_char;
import static net.sf.nightworks.Nightworks.*;

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
}
