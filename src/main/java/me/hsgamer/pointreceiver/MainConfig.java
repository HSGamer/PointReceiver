package me.hsgamer.pointreceiver;

import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.common.CollectionUtils;
import me.hsgamer.hscore.config.BaseConfigPath;
import me.hsgamer.hscore.config.ConfigPath;
import me.hsgamer.hscore.config.PathableConfig;
import me.hsgamer.hscore.config.path.DoubleConfigPath;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.List;

public class MainConfig extends PathableConfig {
    public static final ConfigPath<List<String>> GIVE_COMMANDS = new BaseConfigPath<>(
            "give-commands",
            Collections.singletonList("points add {player} {point}"),
            o -> CollectionUtils.createStringListFromObject(o, true)
    );
    public static final DoubleConfigPath POINT_MULTIPLY = new DoubleConfigPath("point-multiply", 1.0);

    public MainConfig(Plugin plugin) {
        super(new BukkitConfig(plugin, "config.yml"));
    }
}
