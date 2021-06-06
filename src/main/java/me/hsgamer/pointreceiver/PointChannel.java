package me.hsgamer.pointreceiver;

import com.google.common.io.ByteArrayDataInput;
import me.hsgamer.hscore.bukkit.channel.BungeeSubChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class PointChannel extends BungeeSubChannel {
    public PointChannel(Plugin plugin) {
        super("PointReceiver", plugin);
    }

    @Override
    public void handleSubChannelMessage(Player player, ByteArrayDataInput dataInput) {
        String serverName = dataInput.readUTF();
        String playerName = dataInput.readUTF();
        String point = dataInput.readUTF();
        System.out.println(String.join(" - ", serverName, playerName, point));

        List<String> commands = getPlugin().getConfig().getStringList("give-commands");
        commands.replaceAll(s ->
                s.replace("{server}", serverName)
                        .replace("{player}", playerName)
                        .replace("{point}", point)
        );
        for (String command : commands) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }
}
