package dmvmc.buffedMobs;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnListener implements Listener {

    private final BuffedMobs plugin;
    public EntitySpawnListener(final BuffedMobs plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        if (!(e.getEntity() instanceof LivingEntity entity))
            return;

        AttributeInstance maxHealthAttribute = entity.getAttribute(Attribute.MAX_HEALTH);
        if (maxHealthAttribute == null)
            return;

        //  Det entity new max health
        double multiplier = plugin.getMobHealthMultiplier(entity.getType());
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
