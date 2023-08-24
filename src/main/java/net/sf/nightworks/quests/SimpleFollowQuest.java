package net.sf.nightworks.quests;

/**
 * SimpleFollowQuest, where a MOB follows you until you reach a certain room
 * The MOB has to be part of the area, in the room, to make the proposition
 * Do we need a quest setup here? hopefully not, we can just make the mob follow the player
 * However, how do we then check that the quest is over?
 */
public class SimpleFollowQuest extends SimpleQuest {
    private int vnumToReach = -1;

    public int getVnumToReach() {
        return vnumToReach;
    }

    public void setVnumToReach(int vnumToReach) {
        this.vnumToReach = vnumToReach;
    }

    public SimpleFollowQuest(String name) {
        super(name);
    }

    public SimpleFollowQuest(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean isStandalone() {
        return false;
    }
}
