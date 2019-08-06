package me.nosmakos.killshot.utilities;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KUtil {

    public static final ItemStack GUI_GLASS = XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE.buildX(" ", 1);

    public static final String DS = ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "KILLSHOT" + org.bukkit.ChatColor.GRAY + "] ";

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String s(int amount) {
        return (amount > 1) ? "s" : "";
    }

    public static List<Block> getNearbyBlocks(Location l, int radius) {
        List<Block> blocks = new ArrayList<>();
        for (int x = -(radius); x <= radius; x++) {
            for (int y = -(radius); y <= radius; y++) {
                for (int z = -(radius); z <= radius; z++) {
                    Block b = l.getWorld().getBlockAt((int) l.getX() + x, (int) l.getY() + y, (int) l.getZ() + z);
                    if (b.getType() != Material.TNT) continue;
                    blocks.add(b);
                }
            }
        }
        return blocks;
    }

    public static boolean isInteger(String var0) {
        try {
            Integer.parseInt(var0);
            return true;
        } catch (NumberFormatException | NullPointerException var2) {
            return false;
        }
    }

    public static void removeItem(Player player) {
        ItemStack hand = player.getInventory().getItemInMainHand();
        if ((hand.getAmount() >= 1) && (player.getGameMode() != GameMode.CREATIVE)) {
            hand.setAmount(hand.getAmount() - 1);
        }
    }

}