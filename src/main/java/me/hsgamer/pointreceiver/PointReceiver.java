package me.hsgamer.pointreceiver;

import org.bukkit.plugin.java.JavaPlugin;

public final class PointReceiver extends JavaPlugin {
    private final PointChannel pointChannel = new PointChannel(this);

    @Override
    public void onEnable() {
        pointChannel.register();
    }

    @Override
    public void onDisable() {
        pointChannel.unregister();
    }
}
