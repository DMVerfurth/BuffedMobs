# BuffedMobs Plugin

## Overview

BuffedMobs is a Minecraft plugin that allows server owners to buff the health of mobs buy custom factors. 
By default, all mobs have double the health.
The default health multiplier can be customized as well as individual health multipliers for each mob.

## Features

- Blanket health multiplier for all mobs
- Specific health multipliers for individual mob types
- Health multipliers are stored in the servers `config.yml` and can be changed via command

## Installation

1. Download `/build/libs/BuffedMobs-v1.0.jar`
2. Place the `.jar` file into the `plugins` folder of your Minecraft server
3. Restart the server to enable the plugin

## Commands

| Command             | Usage                | Description                                   |
|---------------------|----------------------|-----------------------------------------------|
| `/setmobmultiplier` | `<mob> <multiplier>` | Sets the health multiplier for a specific mob |

### Example Usage

- `/setmobmultiplier ZOMBIE 2` → Doubles the base health of all newly spawned zombies

## Permissions

| Permission              | Description                                            | Default |
|-------------------------|--------------------------------------------------------|---------|
| `buffedMobs.set`        | Allows the player to set the health multiplier of mobs | `op`    |

## Configuration

The plugin saves health multipliers in `config.yml` located in the plugin's data folder. 
This file is automatically managed by the plugin.
To update the base health multiplier of all mobs, you must change the `default_multiplier` field of `config.yml`

## Dependencies

- Requires Minecraft `1.21+` (API version `1.21`)
- Uses [Adventure API](https://docs.adventure.kyori.net) for text formatting

## Development

### Main Classes

- `BuffedMobs.java` - Manages the plugin lifecycle and stores player colors
- `EntitySpawnListener` - Listens for all newly spawned living entities to update their health
- `SetMobMultiplierCommand.java` - Handles the `/setmobmultiplier` command
- `SetMobMultiplierTabCompleter.java` - Handles the tab completion for the `/setmobmultiplier` command

### Building from Source

1. Clone the repository
2. Build using `./gradlew build`
3. Place the compiled `.jar` into your server's `plugins` folder.

## License

This plugin is provided as open-source. Feel free to modify and use it according to your needs.

---

Enjoy tougher mobs! 💪