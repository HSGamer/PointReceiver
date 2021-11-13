package me.hsgamer.pointreceiver;

import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import org.bukkit.plugin.Plugin;

import java.util.Collections;

public class MainConfig extends BukkitConfig {
    public MainConfig(Plugin plugin) {
        super(plugin, "config.yml");
    }

    @Override
    public void setup() {
        super.setup();
        addDefault("give-commands", Collections.singletonList("point add {player} {amount}"));
        save();
    }
}
