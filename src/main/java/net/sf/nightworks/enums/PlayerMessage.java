package net.sf.nightworks.enums;

public enum PlayerMessage {
	
	YOU_HAVE_BEEN_KILLED(1, "You have been KILLED!\n"),
	WEAPON_DOING_GREAT(2, "Your weapon whispers, 'You're doing great!'\n"),
	WEAPON_GOOD_WORK(3, "Your weapon whispers, 'Keep up the good work!'\n"),
	WEAPON_CAN_DO_IT(4, "Your weapon whispers, 'You can do it!'\n"),
	WEAPON_RUN_AWAY(5, "Your weapon whispers, 'Run away! Run away!'\n"),
	WEAPON_GLOWS_BLUE(6, "Your weapon glows {cblue.{x"),
	GODS_ARE_FURI(7, "The gods are infuriated!"),
	CONSUMNED_BY_RAGE(8, "Your pulse races as you are consumned by rage!\n"),
	TATOO_GLOWS_RED(9, "{rThe tattoo on your shoulder glows red.{x"),
	TATOO_GLOWS_BLUE(10, "{cThe tattoo on your shoulder glows blue.{x");

	PlayerMessage(int id, String message) {
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
