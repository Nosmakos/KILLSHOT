package me.nosmakos.killshot.commands.executors;

import me.nosmakos.killshot.KillShot;
import me.nosmakos.killshot.commands.AbstractCommand;
import me.nosmakos.killshot.utilities.Lang;
import me.nosmakos.killshot.utilities.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWeapons extends AbstractCommand {

    private KillShot plugin;
    private static final String COMMAND = "weapons";
    private static final Permission PERMISSION = Permission.GIVE_WEAPON_COMMAND;
    private static final String[] HELP = {
            GRAY + "Use: " + RED + "/killshot weapons list" + GRAY + " - to get the list of all the available weapons.",
            GRAY + "Use: " + RED + "/killshot weapons give [player] [weapon]" + GRAY + " - to give a weapon on the selected player."
    };

    public CommandWeapons(KillShot plugin) {
        super(plugin, COMMAND, HELP, PERMISSION);
        this.plugin = plugin;
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        if (args.length == 0 || args.length >= 2 && !args[0].equalsIgnoreCase("give")) {
            sendHelp(sender);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("give")) {
                sender.sendMessage(Lang.SPECIFY_PLAYER.get());

            } else if (args[0].equalsIgnoreCase("list")) {
                sender.sendMessage(RED + "---------------------------------------");
                sender.sendMessage(GRAY + "Displaying all the available weapons:");
                sender.sendMessage(" ");
                plugin.weapon().keySet().forEach(w -> sender.sendMessage(RED + "> " + GRAY + w));
                sender.sendMessage(RED + "---------------------------------------");
            } else {
                sendHelp(sender);
            }
        } else if (args.length == 2) {
            sender.sendMessage(Lang.SPECIFY_WEAPON.get());

        } else if (args.length == 3) {
            Player selectedPlayer = Bukkit.getServer().getPlayer(args[1]);
            try {
                if (selectedPlayer == null) {
                    sender.sendMessage(Lang.PLAYER_NOT_FOUND.get());
                    return;
                }
                if (selectedPlayer.getInventory().firstEmpty() == -1) {
                    sender.sendMessage(Lang.NOT_ENOUGH_SPACE.get());
                    return;
                }
                String weapon = args[2].toLowerCase();
                if (plugin.getWeaponManagement().getWeapon(weapon) == null) {
                    sender.sendMessage(Lang.NOT_VALID_WEAPON.get());
                    return;
                }
                try {
                    selectedPlayer.getInventory().addItem(plugin.getWeaponManagement().getWeaponItem(weapon));
                    sender.sendMessage(Lang.GAVE_WEAPON.get().replace("%weapon%", weapon).replace("%player%", selectedPlayer.getName()));
                } catch (IllegalArgumentException exception) {
                    sender.sendMessage(RED + "The selected weapon type is corrupted due to incorrect type, name or lore. Also, Make sure you're using the correct item(material) type.");
                }
            } catch (NumberFormatException e) {
                sender.sendMessage(Lang.NOT_VALID_AMOUNT.get());
            }
        } else {
            sendHelp(sender);
        }
    }
}