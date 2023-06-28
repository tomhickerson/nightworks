package net.sf.nightworks.quests;

import net.sf.nightworks.Nightworks;

/**
 * SimpleCollectQuest.java
 * Examples: Collect five copper cogs, scattered around a given area
 * Collect eight pieces of a puzzle in a given area, put them all in a paper bag
 * Someday: -- collect different items and put them in one container?
 * -- collect different items and put them in different containers?
 */
public class SimpleCollectQuest extends SimpleQuest {

    // private int[] vnumsToGet = new int[];

    private int questPoints = 0;
    private int vnumToCollect = -1;
    private int numberToCollect = -1;
    private String areaToCollect = "midgaard.are";
    private int vnumContainer = -1;
    // is picking up the container a catalyst for starting the quest?
    // container doesn't necessarily have to be involved

    public SimpleCollectQuest(String name) {
        super(name);
    }

    public SimpleCollectQuest(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean canRunAgain() {
        return false;
    }

    @Override
    public boolean isStandalone() {
        return false;
    }

    public int getQuestPoints() {
        return questPoints;
    }

    public void setQuestPoints(int questPoints) {
        this.questPoints = questPoints;
    }

    public int getVnumToCollect() {
        return vnumToCollect;
    }

    public void setVnumToCollect(int vnumToCollect) {
        this.vnumToCollect = vnumToCollect;
    }

    public int getNumberToCollect() {
        return numberToCollect;
    }

    public void setNumberToCollect(int numberToCollect) {
        this.numberToCollect = numberToCollect;
    }

    public String getAreaToCollect() {
        return areaToCollect;
    }

    public void setAreaToCollect(String areaToCollect) {
        this.areaToCollect = areaToCollect;
    }

    public int getVnumContainer() {
        return vnumContainer;
    }

    public void setVnumContainer(int vnumContainer) {
        this.vnumContainer = vnumContainer;
    }
}
