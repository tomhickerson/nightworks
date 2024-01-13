package net.sf.nightworks.quests;

import net.sf.nightworks.Nightworks;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertNotNull;

/**
 * Quest Manager Test, running a little test here to make sure all the
 * required values are filled in
 */
public class QuestManagerTest {

    @Test
    public void testGetQuests() {
        System.out.print("Quests: ");
        HashMap<Integer, ArrayList<SimpleQuest>> quests = QuestManager.getQuests();
        quests.forEach((pk, values) -> {
            for (SimpleQuest q : values) {
                assertNotNull(q.getAcceptMessage());
                assertNotNull(q.getAcceptPhrase());
                if (q.getLoadedQuest() == null) {
                    assertNotNull(q.getReward());
                }
                assertNotNull(q.getEpilogue());
                System.out.print(".");
            }
            System.out.print("+");
        });
        System.out.println("");
    }
}
