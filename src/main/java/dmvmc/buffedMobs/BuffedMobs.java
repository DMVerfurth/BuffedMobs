package dmvmc.buffedMobs;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class BuffedMobs extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("BuffedMobs has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("BuffedMobs has been disabled!");
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        if (!(e.getEntity() instanceof LivingEntity entity))
            return;

        AttributeInstance maxHealthAttribute = entity.getAttribute(Attribute.MAX_HEALTH);
        if (maxHealthAttribute == null)
           return;

        // Double and set entity max health
        double newHealth = maxHealthAttribute.getBaseValue() * 2;
        maxHealthAttribute.setBaseValue(newHealth);
        entity.setHealth(newHealth);

        // Sync ender dragon health with client
        if (entity instanceof EnderDragon dragon) {
            dragon.setHealth(newHealth);
            dragon.setPhase(dragon.getPhase());
        }

    }

}
