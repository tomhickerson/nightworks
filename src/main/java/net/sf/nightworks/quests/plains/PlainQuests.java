package net.sf.nightworks.quests.plains;

import net.sf.nightworks.Nightworks;
import net.sf.nightworks.quests.SimpleGetQuest;
import net.sf.nightworks.quests.SimpleQuest;

import static net.sf.nightworks.Nightworks.IS_GOOD;
import static net.sf.nightworks.Nightworks.IS_NPC;

public class PlainQuests {

    public static SimpleGetQuest returnDruidQuest() {
        SimpleGetQuest sgq = new SimpleGetQuest(1,"Find the Fennel for the Druid");
        sgq.setQuestPoints(10);
        sgq.setVnumToGet(-1); // to be found
        sgq.setPreamble("Dear Adventurer, glad to see you!  Could you help me find some {Yfennel{x \nfor a stew I'm making?  Please say 'I accept' if so...");
        sgq.setDuration(30);
        sgq.setEpilogue("Thank you so much!  I'll really enjoy this stew!");
        sgq.setAcceptMessage("So excellent!  Please stay on the plains and you should find it somewhere nearby!");
        // setting how to qualify here
        sgq.setQualify(qualifies());
        return sgq;
    }
    
    private static SimpleQuest.qualify qualifies() {
        return ch -> {
            // set the quest bit here as well
            return ch.level > 5 && IS_GOOD(ch) && !IS_NPC(ch);
        };
    }
}
