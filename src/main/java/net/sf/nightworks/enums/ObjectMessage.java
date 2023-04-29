package net.sf.nightworks.enums;

public enum ObjectMessage {

    CANT_HOLD_SHIELD(1, "You can't hold a shield right now.\n"),
    CANT_HOLD_THING(2, "You can't hold a thing right now.\n"),
    CANT_WEAR_WIELD_HOLD(3, "You can't wear, wield, or hold that.\n"),
    YOU_DO_NOT_HAVE(4,"You do not have that item.\n"),
    YOU_CANT_BUY_THAT(5, "Sorry, you can't buy that here.\n"),
    YOU_CANT_AFFORD_IT(6, "You can't afford it.\n"),
    YOU_CANT_CARRY_THAT_MANY(7, "You can't carry that many items.\n");


    ObjectMessage(int id, String message) {
        this.id = id;
        this.message = message;
    }

    private int id;
    private String message;

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
