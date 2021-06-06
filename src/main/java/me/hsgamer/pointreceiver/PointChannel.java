package me.hsgamer.pointreceiver;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import me.hsgamer.hscore.bukkit.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class PointChannel extends Channel {
    public PointChannel(Plugin plugin) {
        super("point:receive", plugin);
    }

    @Override
    public void handleMessage(Player player, byte[] data) {
        player.sendMessage("Received Points");
        System.out.println("Received Points for " + player.getName());
        ByteArrayDataInput input = ByteStreams.newDataInput(data);
        String serverName = input.readUTF();
        String playerName = input.readUTF();
        String point = input.readUTF();

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
