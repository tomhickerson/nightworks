package net.sf.nightworks.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Player Lore, where we can add or remove lore from a player.
 * We can also add knowledge of:
 * - ancient languages
 * - gods long forgotten
 * - apocryphal magic items
 * - secret password for the thieves' guild
 * - etc.
 */
public enum PlayerLore {
    MINOR_KNOWLEDGE_SOULCUBE(1, "Minor Knowledge of a Soul Cube"),
    MEDIUM_KNOWLEDGE_SOULCUBE(2, "Medium Knowledge of a Soul Cube"),
    GREATER_KNOWLEDGE_SOULCUBE(3, "Greater Knowledge of a Soul Cube");

    PlayerLore(int id, String knowledge) {
        this.id = id;
        this.knowledge = knowledge;
    }

    private final int id;
    private final String knowledge;

    public int getId() {
        return id;
    }

    public String getKnowledge() {
        return knowledge;
    }

    private static final PlayerLore[] lore = PlayerLore.values();

    public static final Map<Integer, PlayerLore> loreMap = new HashMap<>();

    static {
        for (PlayerLore pl : lore) {
            loreMap.put(pl.getId(), pl);
        }
    }

    public static PlayerLore lookupLore(int id) {
        return loreMap.get(id);
    }
}
