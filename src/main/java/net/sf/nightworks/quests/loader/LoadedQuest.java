package net.sf.nightworks.quests.loader;

public class LoadedQuest {

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public String getQuestName() {
        return questName;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public String getQuestType() {
        return questType;
    }

    public void setQuestType(String questType) {
        this.questType = questType;
    }

    public String getPreamble() {
        return preamble;
    }

    public void setPreamble(String preamble) {
        this.preamble = preamble;
    }

    public String getComeBackLater() {
        return comeBackLater;
    }

    public void setComeBackLater(String comeBackLater) {
        this.comeBackLater = comeBackLater;
    }

    public String getAcceptKeyword() {
        return acceptKeyword;
    }

    public void setAcceptKeyword(String acceptKeyword) {
        this.acceptKeyword = acceptKeyword;
    }

    public String getAcceptPhrase() {
        return acceptPhrase;
    }

    public void setAcceptPhrase(String acceptPhrase) {
        this.acceptPhrase = acceptPhrase;
    }

    public String getEpilogue() {
        return epilogue;
    }

    public void setEpilogue(String epilogue) {
        this.epilogue = epilogue;
    }

    public int getQuestMinLevel() {
        return questMinLevel;
    }

    public void setQuestMinLevel(int questMinLevel) {
        this.questMinLevel = questMinLevel;
    }

    public int getPrereqAchvId() {
        return prereqAchvId;
    }

    public void setPrereqAchvId(int prereqAchvId) {
        this.prereqAchvId = prereqAchvId;
    }

    public int getQuestRace() {
        return questRace;
    }

    public void setQuestRace(int questRace) {
        this.questRace = questRace;
    }

    public int getQuestAlign() {
        return questAlign;
    }

    public void setQuestAlign(int questAlign) {
        this.questAlign = questAlign;
    }

    public int getVnumQuestGiver() {
        return vnumQuestGiver;
    }

    public void setVnumQuestGiver(int vnumQuestGiver) {
        this.vnumQuestGiver = vnumQuestGiver;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getVnumToKill() {
        return vnumToKill;
    }

    public void setVnumToKill(int vnumToKill) {
        this.vnumToKill = vnumToKill;
    }

    public int getVnumToGet() {
        return vnumToGet;
    }

    public void setVnumToGet(int vnumToGet) {
        this.vnumToGet = vnumToGet;
    }

    public int getVnumContainer() {
        return vnumContainer;
    }

    public void setVnumContainer(int vnumContainer) {
        this.vnumContainer = vnumContainer;
    }

    public int getQuestPoints() {
        return questPoints;
    }

    public void setQuestPoints(int questPoints) {
        this.questPoints = questPoints;
    }

    public int getSilver() {
        return silver;
    }

    public void setSilver(int silver) {
        this.silver = silver;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getVirtue() {
        return virtue;
    }

    public void setVirtue(int virtue) {
        this.virtue = virtue;
    }

    public int getVice() {
        return vice;
    }

    public void setVice(int vice) {
        this.vice = vice;
    }

    private int questId;
    private String questName;

    private String questType;
    private String preamble;
    private String comeBackLater;
    private String acceptKeyword;
    private String acceptPhrase;
    private String epilogue;

    // quest qualifiers line in the import file
    private int questMinLevel;
    private int prereqAchvId;
    private int questRace;
    private int questAlign;

    // quest details line in the import file
    private int vnumQuestGiver;
    private int duration;
    private int vnumToKill;
    private int vnumToGet;
    private int vnumContainer;

    // reward row in the import file
    private int questPoints;
    private int silver;
    private int gold;
    private int virtue;
    private int vice;

}
