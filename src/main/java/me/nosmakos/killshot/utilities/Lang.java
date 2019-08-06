package me.nosmakos.killshot.utilities;

import me.nosmakos.killshot.KillShot;
import org.bukkit.ChatColor;

public enum Lang {
    NO_PERMISSION("&cYou do not have the permission to do that."),
    INSUFFICIENT_PERMISSIONS("&cInsufficient Permissions"),
    PLAYER_NOT_FOUND("Player not found online."),
    PLAYER_COMMAND("&cThis command is only available to players."),
    SPECIFY_PLAYER("Specify a player."),
    SPECIFY_AMOUNT("Specify an amount."),
    SPECIFY_AMMO("Specify an ammunition type."),
    NOT_VALID_AMMO("&cInvalid ammunition type."),
    SPECIFY_WEAPON("Specify an existing weapon."),
    NOT_VALID_WEAPON("&cInvalid weapon type."),
    NOT_VALID_AMOUNT("&cInvalid amount."),
    NOT_ENOUGH_SPACE("&cNot enough space."),
    GAVE_AMMO("&7You gave &c(%amount%) %ammo% ammunition &7to &2%player%."),
    GAVE_WEAPON("&7You gave a %weapon% weapon &7to &2%player%."),
    KILLED_PLAYER_DEATH_MESSAGE("&7%killer% &c&l%gun% &7%victim%");

    private String type;
    private KillShot plugin;

    Lang(String type) {
        this.type = type;
        plugin = KillShot.getPlugin(KillShot.class);
    }

    public String get() {
        String value = plugin.getLanguageConfig().getString(name());
        if (value == null) {
            plugin.getLogger().warning("Missing lang message data: " + name());
            value = type;
        }
        return ChatColor.translateAlternateColorCodes('&', value);
    }
}
