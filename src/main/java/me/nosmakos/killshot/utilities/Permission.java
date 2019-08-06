package me.nosmakos.killshot.utilities;

public enum Permission {
    HELP("killshot.help"),
    RELOAD("killshot.reload"),
    WEAPONS_OP("killshot.weapons.*"),
    GIVE_AMMO_COMMAND("killshot.ammo.cmd"),
    GIVE_WEAPON_COMMAND("killshot.weapons.cmd"),

    OP("killshot.*");

    private String type;

    Permission(String type) {
        this.type = type;
    }

    public String get() {
        return this.type;
    }
}