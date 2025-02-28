package dmvmc.buffedMobs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class SetMobMultiplierCommand implements CommandExecutor {

    private final BuffedMobs plugin;
    public SetMobMultiplierCommand(BuffedMobs plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {

        // Check permission ot set mob multiplier
        if (!sender.hasPermission("buffedMobs.set")) {
            sender.sendMessage(Component.text()
                    .content("You do not have permission to use this command!")
                    .color(NamedTextColor.RED));
            return true;
        }

        // Ensure correct usage
        if (args.length != 2) {
            sender.sendMessage("Usage: /setMobMultiplier <mob> <multiplier>");
            return true;
        }

        try {

            // Identify mob and multiplier
            String inputType = args[0].toUpperCase().replace(" ", "_");
            EntityType entityType = EntityType.valueOf(inputType);
            double multiplier = Double.parseDouble(args[1]);

            // Update mob multiplier and config
            plugin.setMobHealthMultiplier(entityType, multiplier);
            plugin.getConfig().set("mob_multipliers." + inputType, multiplier);
            plugin.saveConfig();

            // Success
            sender.sendMessage(Component.text()
                    .content("Mob multiplier for " + inputType + " set to " + multiplier)
                    .color(NamedTextColor.GREEN));
            return true;

        } catch (NumberFormatException e) {
            // Validate mob multiplier
            sender.sendMessage(Component.text()
                    .content("Invalid multiplier format! Please enter a valid number!")
                    .color(NamedTextColor.RED));
            return true;
        } catch (IllegalArgumentException e) {
            // Validate mob type
            sender.sendMessage(Component.text()
                    .content("Invalid mob type: " + args[0])
                    .color(NamedTextColor.RED));
            return true;
        }

    }

}
