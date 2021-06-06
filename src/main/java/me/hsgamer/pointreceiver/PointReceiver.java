package me.hsgamer.pointreceiver;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

public final class PointReceiver extends JavaPlugin {
    private final PointChannel pointChannel = new PointChannel(this);

    @Override
    public void onEnable() {
        loadDefault();
        pointChannel.register();
    }

    private void loadDefault() {
        FileConfiguration configuration = getConfig();
        configuration.options().copyDefaults(true);
        configuration.addDefault("give-commands", Collections.singletonList("point add {player} {amount}"));
        saveConfig();
    }

    @Override
    public void onDisable() {
        pointChannel.unregister();
    }
}
