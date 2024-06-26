package net.sf.nightworks.quests;

/**
 * SimpleKillQuest, where we kill an already existing single mob
 * and then return to the quest keeper
 */
public class SimpleKillQuest extends SimpleQuest {

    private int vnumToKill = -1;
    private int questPoints = 0;

    public SimpleKillQuest(String name) {
        super(name);
    }

    public SimpleKillQuest(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean isStandalone() {
        return true;
    }

    public int getQuestPoints() {
        return questPoints;
    }

    public void setQuestPoints(int questPoints) {
        this.questPoints = questPoints;
    }

    public int getVnumToKill() {
        return vnumToKill;
    }

    public void setVnumToKill(int vnumToKill) {
        this.vnumToKill = vnumToKill;
    }
}
