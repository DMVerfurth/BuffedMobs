package dmvmc.buffedMobs;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class BuffedMobs extends JavaPlugin implements Listener {

    private final Map<EntityType, Double> mobHealthMultipliers = new HashMap<>();
    private double defaultMultiplier = 2.0;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfigValues();
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("BuffedMobs has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("BuffedMobs has been disabled!");
    }

    private void loadConfigValues() {
        reloadConfig();
        FileConfiguration config = getConfig();

        // Load mob health multiplier configuration settings
        defaultMultiplier = config.getDouble("default_multiplier");
        config.getConfigurationSection("mob_multipliers").getKeys(false).forEach(key -> {
            try {
                EntityType type = EntityType.valueOf(key.toUpperCase());
                double multiplier = config.getDouble("mob_multipliers." + key);
                mobHealthMultipliers.put(type, multiplier);
            } catch (Exception e) {
                getLogger().warning("Invalid mob type: " + key);
            }
        });

    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        if (!(e.getEntity() instanceof LivingEntity entity))
            return;

        AttributeInstance maxHealthAttribute = entity.getAttribute(Attribute.MAX_HEALTH);
        if (maxHealthAttribute == null)
           return;

        //  Det entity new max health
        double multiplier = mobHealthMultipliers.getOrDefault(entity.getType(), defaultMultiplier);
        double newHealth = Math.min(maxHealthAttribute.getBaseValue() * multiplier, 1024.0);

        maxHealthAttribute.setBaseValue(newHealth);
        entity.setHealth(newHealth);

        // Sync ender dragon health with client
        if (entity instanceof EnderDragon dragon) {
            dragon.setHealth(newHealth);
            dragon.setPhase(dragon.getPhase());
        }

    }

}
