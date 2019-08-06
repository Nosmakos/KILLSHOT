package me.nosmakos.killshot.commands.executors;

import me.nosmakos.killshot.KillShot;
import me.nosmakos.killshot.commands.AbstractCommand;
import me.nosmakos.killshot.utilities.Permission;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.command.CommandSender;

public class CommandHelp extends AbstractCommand {

    private KillShot plugin;
    private static final String COMMAND = "help";
    private static final Permission PERMISSION = Permission.HELP;
    private static final String[] HELP = {
            RED + "> " + GRAY + "/killshot help",
            RED + "> " + GRAY + "/killshot reload",
            RED + "> " + GRAY + "/killshot ammo list",
            RED + "> " + GRAY + "/killshot ammo give [player] [ammoType] [amount]",
            RED + "> " + GRAY + "/killshot weapons list",
            RED + "> " + GRAY + "/killshot weapons give [player] [weapon]"
    };

    public CommandHelp(KillShot plugin) {
        super(plugin, COMMAND, HELP, PERMISSION);
        this.plugin = plugin;
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        sender.sendMessage(DARK_GRAY + "==================[" + DARK_AQUA + "KillShot" + DARK_GRAY + "]==================");
        sender.sendMessage(RED + "> " + GRAY + "Aliases: " + DARK_AQUA + "/killshot, /ks");
        sender.sendMessage(RED + "> " + GRAY + "Version: " + DARK_AQUA + plugin.getDescription().getVersion());
        sender.sendMessage(RED + "> " + GRAY + "Author: " + DARK_AQUA + "Nosmakos");
        sender.sendMessage(RED + "> " + GRAY + "Discord Support: " + DARK_AQUA + "discord.com/invite/9v7BsVv");
        sendHelp(sender);

        String url = "https://github.com/Nosmakos/KILLSHOT/blob/master/updates/v1.0.md";
        String text = format( "&7For all the permissions and commands &8[&cClick Here&8]");

        sender.spigot().sendMessage(getComponent(text, ClickEvent.Action.OPEN_URL, url, GRAY + "Github - Using v1.0 Update"));
    }
}