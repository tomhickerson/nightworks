package net.sf.nightworks.quests;

import net.sf.nightworks.Nightworks;

public abstract class SimpleQuest {

    private String name;

    private String description;
    // strings we show the players
    private String preamble;
    private String epilogue;
    private String acceptMessage;
    private String alreadyRunMessage;
    private int achievement; // link to an enum to be developed later
    private int duration;
    private qualify qualifier;
    private reward reward;
    private String acceptPhrase = "i accept"; // default

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreamble() {
        return preamble;
    }

    public void setPreamble(String preamble) {
        this.preamble = preamble;
    }

    public String getEpilogue() {
        return epilogue;
    }

    public void setEpilogue(String epilogue) {
        this.epilogue = epilogue;
    }

    public String getAcceptMessage() {
        return acceptMessage;
    }

    public void setAcceptMessage(String acceptMessage) {
        this.acceptMessage = acceptMessage;
    }

    public String getAcceptPhrase() {
        return acceptPhrase;
    }

    public void setAcceptPhrase(String acceptPhrase) {
        this.acceptPhrase = acceptPhrase;
    }

    public String getAlreadyRunMessage() {
        return alreadyRunMessage;
    }

    public void setAlreadyRunMessage(String alreadyRunMessage) {
        this.alreadyRunMessage = alreadyRunMessage;
    }

    public int getAchievement() {
        return achievement;
    }

    public void setAchievement(int achievement) {
        this.achievement = achievement;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public SimpleQuest(String name) {
        this.name = name;
    }

    public SimpleQuest(int id, String name) {
        this.name = name;
    }

    //public abstract boolean doesQualify(Nightworks.CHAR_DATA ch);

    public interface qualify {
        boolean doesQualify(Nightworks.CHAR_DATA ch);
    }

    public void setQualify(qualify q) {
        this.qualifier = q;
    }

    public boolean getQualifier(Nightworks.CHAR_DATA ch) {
        return this.qualifier.doesQualify(ch);
    }

    public abstract boolean canRunAgain();

    public abstract boolean isStandalone();

    public interface reward {
        void deliverReward(Nightworks.CHAR_DATA ch);
    }

    public void setReward(reward r) {
        this.reward = r;
    }

    public reward getReward() {
        return this.reward;
    }

    public void applyReward(Nightworks.CHAR_DATA ch) {
        this.reward.deliverReward(ch);
    }
    // public abstract Object deliverReward(Nightworks.CHAR_DATA ch);
    // to be replaced with a reward class, or the character data class
}
