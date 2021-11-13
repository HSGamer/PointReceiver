package me.hsgamer.pointreceiver;

import com.google.common.io.ByteArrayDataInput;
import me.hsgamer.hscore.bukkit.channel.BungeeSubChannel;
import me.hsgamer.hscore.common.CollectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class PointChannel extends BungeeSubChannel implements Listener {
    private final MainConfig mainConfig;
    private final StorageConfig storageConfig;
    private final Map<String, Integer> playerPoints = new ConcurrentHashMap<>();

    public PointChannel(JavaPlugin plugin) {
        super("PointReceiver", plugin);
        mainConfig = new MainConfig(plugin);
        storageConfig = new StorageConfig(plugin);
    }

    @Override
    public void register() {
        mainConfig.setup();
        storageConfig.setup();
        storageConfig.getValues(false).forEach((name, point) -> {
            try {
                int i = Integer.parseInt(String.valueOf(point));
                playerPoints.put(name, i);
            } catch (Exception e) {
                getPlugin().getLogger().log(Level.WARNING, "Failed to load player point: " + name, e);
            }
        });
        Bukkit.getPluginManager().registerEvents(this, getPlugin());
        super.register();
    }

    @Override
    public void unregister() {
        super.unregister();
        HandlerList.unregisterAll(this);
        storageConfig.getKeys(false).forEach(storageConfig::remove);
        playerPoints.forEach(storageConfig::set);
        storageConfig.save();
        playerPoints.clear();
    }

    @Override
    public void handleSubChannelMessage(Player player, ByteArrayDataInput dataInput) {
        String data = dataInput.readUTF();
        String[] split = data.split("\\|", 3);
        String serverName = split[0];
        String playerName = split[1];
        int point;
        try {
            point = Integer.parseInt(split[2]);
        } catch (Exception e) {
            getPlugin().getLogger().log(Level.WARNING, e, () -> "Failed to parse point: " + split[2] + " for " + playerName);
            return;
        }
        playerPoints.merge(playerName, point, Integer::sum);
        getPlugin().getLogger().info(() -> "Received point: " + point + " for " + playerName + " from server " + serverName);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        String name = event.getPlayer().getName();
        Optional.ofNullable(playerPoints.remove(name))
                .filter(point -> point > 0)
                .ifPresent(point -> {
                    List<String> commands = CollectionUtils.createStringListFromObject(mainConfig.get("give-commands"), true);
                    commands.replaceAll(s -> s
                            .replace("{player}", name)
                            .replace("{point}", String.valueOf(point))
                    );
                    for (String command : commands) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                    }
                });
    }
}
