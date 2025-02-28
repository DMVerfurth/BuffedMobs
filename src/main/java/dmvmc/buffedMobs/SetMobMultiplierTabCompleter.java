package dmvmc.buffedMobs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class SetMobMultiplierTabCompleter implements TabCompleter {

    private static final List<String> MOBS = Arrays.stream(EntityType.values())
            .filter(EntityType::isAlive)
            .map(EntityType::name)
            .toList();

    private static final List<String> COMMON_MULTIPLIERS = Arrays.asList(
            "0.1", "0.5", "1", "2", "5", "10", "20", "100", "1000"
    );

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String @NotNull [] args) {

        // Return recommended mobs
        if (args.length == 1)
            return MOBS.stream()
                    .filter(type -> type.startsWith(args[0].toUpperCase().replace(" ", "_")))
                    .collect(Collectors.toList());

        // Return recommended multipliers
        if (args.length == 2)
            return COMMON_MULTIPLIERS.stream()
                    .filter(number -> number.startsWith(args[1]))
                    .collect(Collectors.toList());

        return Collections.emptyList();

    }

}
