package me.hsgamer.pointreceiver;

import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import org.bukkit.plugin.Plugin;

public class StorageConfig extends BukkitConfig {
    public StorageConfig(Plugin plugin) {
        super(plugin, "storage.yml");
    }
}
