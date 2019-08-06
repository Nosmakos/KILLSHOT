package me.nosmakos.killshot.commands.executors;

import me.nosmakos.killshot.KillShot;
import me.nosmakos.killshot.commands.AbstractCommand;
import me.nosmakos.killshot.utilities.Lang;
import me.nosmakos.killshot.utilities.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAmmunition extends AbstractCommand {

    private KillShot plugin;
    private static final String COMMAND = "ammo";
    private static final Permission PERMISSION = Permission.GIVE_AMMO_COMMAND;
    private static final String[] HELP = {
            GRAY + "Use: " + RED + "/killshot ammo list" + GRAY + " - to get the list of all the available ammunition types.",
            GRAY + "Use: " + RED + "/killshot ammo give [player] [ammoType] [amount]" + GRAY + " - to give ammunition on the selected player with the defined amount."
    };

    public CommandAmmunition(KillShot plugin) {
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
                sender.sendMessage(GRAY + "Displaying all the available ammunition types:");
                sender.sendMessage(" ");
                plugin.getAmmunitionConfig().getConfigurationSection("ammunitionTypes").getKeys(false).forEach(a -> sender.sendMessage(RED + "> " + GRAY + a));
                sender.sendMessage(RED + "---------------------------------------");
            } else {
                sendHelp(sender);
            }
        } else if (args.length == 2 || args.length == 3) {
            String specify = args.length == 2 ? Lang.SPECIFY_AMMO.get() : Lang.SPECIFY_AMOUNT.get();
            sender.sendMessage(specify);

        } else if (args.length == 4) {
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
                int amount = Integer.parseInt(args[3]);
                if (amount <= 0) {
                    sender.sendMessage(Lang.NOT_VALID_AMOUNT.get());
                    return;
                }
                String ammoType = args[2].toLowerCase();
                if (plugin.getAmmunitionConfig().getString("ammunitionTypes." + ammoType) == null) {
                    sender.sendMessage(Lang.NOT_VALID_AMMO.get());
                    return;
                }
                try {
                    selectedPlayer.getInventory().addItem(plugin.getWeaponManagement().getAmmoItem(ammoType, amount));
                    sender.sendMessage(Lang.GAVE_AMMO.get().replace("%amount%", String.valueOf(amount)).replace("%ammo%", ammoType).replace("%player%", selectedPlayer.getName()));
                } catch (IllegalArgumentException exception) {
                    sender.sendMessage(RED + "The selected ammunition type is corrupted due to incorrect type, name or lore. Also, Make sure you're using the correct material type for the current server version.");
                }
            } catch (NumberFormatException e) {
                sender.sendMessage(Lang.NOT_VALID_AMOUNT.get());
            }
        } else {
            sendHelp(sender);
        }
    }
}