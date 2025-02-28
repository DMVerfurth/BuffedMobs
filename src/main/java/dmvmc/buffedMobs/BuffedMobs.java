package dmvmc.buffedMobs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class BuffedMobs extends JavaPlugin {

    public final Map<EntityType, Double> mobHealthMultipliers = new HashMap<>();
    public double defaultMultiplier = 2.0;

    @Override
    public void onEnable() {

        // Load config
        saveDefaultConfig();
        loadNewConfig();

        // Register command and listener
        getCommand("setmobmultiplier").setExecutor(new SetMobMultiplierCommand(this));
        Bukkit.getPluginManager().registerEvents(new EntitySpawnListener(this), this);
        getLogger().info("BuffedMobs has been enabled!");

    }

    @Override
    public void onDisable() {
        getLogger().info("BuffedMobs has been disabled!");
    }

    public double getMobHealthMultiplier(EntityType type) {
        return mobHealthMultipliers.getOrDefault(type, defaultMultiplier);
    }

    public void setMobHealthMultiplier(EntityType type, double value) {
        mobHealthMultipliers.put(type, value);
    }

    private void loadNewConfig() {

        reloadConfig();
        FileConfiguration config = getConfig();

        config.getConfigurationSection("mob_multipliers").getKeys(false).forEach(key -> {

            EntityType type = EntityType.valueOf(key.toUpperCase());
            double multiplier = config.getDouble("mob_multipliers." + key);
            mobHealthMultipliers.put(type, multiplier);

        });

    }

}
