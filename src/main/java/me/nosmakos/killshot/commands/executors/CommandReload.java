package me.nosmakos.killshot.commands.executors;


import me.nosmakos.killshot.KillShot;
import me.nosmakos.killshot.commands.AbstractCommand;
import me.nosmakos.killshot.utilities.KUtil;
import me.nosmakos.killshot.utilities.Permission;
import org.bukkit.command.CommandSender;

public class CommandReload extends AbstractCommand {

    private KillShot plugin;
    private static final String COMMAND = "reload";
    private static final Permission PERMISSION = Permission.RELOAD;
    private static final String[] HELP = {
            GRAY + "Use: " + RED + "/killshot reload" + GRAY + " - to reload the plugin configuration files."
    };

    public CommandReload(KillShot plugin) {
        super(plugin, COMMAND, HELP, PERMISSION);
        this.plugin = plugin;
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        plugin.reloadAmmunitionConfig();
        plugin.reloadLanguageConfig();

        sender.sendMessage(KUtil.DS + GRAY + "Plugin configuration files were successfully reloaded.");
    }
}