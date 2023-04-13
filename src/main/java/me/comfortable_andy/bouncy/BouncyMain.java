package me.comfortable_andy.bouncy;

import me.comfortable_andy.bouncy.bouncible.BouncibleManager;
import me.comfortable_andy.bouncy.bouncible.implementations.Laser;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

public final class BouncyMain extends JavaPlugin implements Listener {

    private static BouncyMain INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        BouncibleManager.getInstance().start(this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getItem() == null || event.getItem().getType() != Material.STICK) return;

        final Player player = event.getPlayer();

        BouncibleManager.getInstance().startTicking(new Laser(player.getEyeLocation(), player.getLocation().getDirection()));
    }

    public static BouncyMain getInstance() {
        return INSTANCE;
    }

}
