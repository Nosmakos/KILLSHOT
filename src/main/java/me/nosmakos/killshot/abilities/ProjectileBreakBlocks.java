package me.nosmakos.killshot.abilities;

import me.nosmakos.killshot.KillShot;
import me.nosmakos.killshot.configuration.WeaponData;
import me.nosmakos.killshot.utilities.XSound;
import me.nosmakos.killshot.weapon.WeaponManagement;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.BlockIterator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectileBreakBlocks implements Listener {

    private KillShot plugin;

    private WeaponManagement weaponManagement;
    private Map<Location, Integer> stage = new HashMap<>();

    public ProjectileBreakBlocks(KillShot plugin) {
        this.plugin = plugin;
        this.weaponManagement = plugin.getWeaponManagement();
    }

    @EventHandler
    public void onExplosion(ProjectileHitEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        Player player = (Player) event.getEntity().getShooter();

        if (!weaponManagement.hasWeapon(player)) return;
        WeaponData data = WeaponData.getConfig(plugin, weaponManagement.getCategory(player));

        if (data.getString("abilities.breakBlocksList") == null) return;

        BlockIterator iterator = new BlockIterator(event.getEntity().getWorld(), event.getEntity().getLocation().toVector(), event.getEntity().getVelocity().normalize(), 0.0D, 4);
        Block hitBlock = null;
        while (iterator.hasNext()) {
            hitBlock = iterator.next();
            if (hitBlock.getType() != Material.AIR) break;
        }
        List<String> materialList = data.getStringList("abilities.breakBlocksList");

        if (hitBlock != null && !String.valueOf(materialList).split("-")[0].contains(hitBlock.getType().name())) return;

        Location loc = null;
        if (hitBlock != null) {
            loc = hitBlock.getLocation();
        }

        if (!stage.containsKey(loc)) stage.put(loc, 0);

        stage.put(loc, stage.get(loc) + 1);

        int maxStage = Integer.parseInt(String.valueOf(materialList).split("-")[1].replace("]", ""));

        if (stage.get(loc) < (maxStage == 0 ? 1 : maxStage)) return;
        stage.remove(loc);

        if (hitBlock.getType().name().contains("GLASS")) {
            XSound.BLOCK_GLASS_BREAK.parsePlayedSound(hitBlock.getLocation(), 1.0F, 1.0F);
        }

        hitBlock.getWorld().playEffect(hitBlock.getLocation(), Effect.STEP_SOUND, 1);
        hitBlock.breakNaturally();
    }
}
