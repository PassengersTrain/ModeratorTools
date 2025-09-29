package io.github.passengerstrain.commands.subcommands;

import io.github.passengerstrain.commands.SubCommand;
import io.github.passengerstrain.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class FreezePlayerCommand extends SubCommand {

    private Utils utils;

    public static Set<Player> frozenPlayers = new HashSet<>();

    public FreezePlayerCommand(Utils utils) {
        this.utils = utils;
    }

    @Override
    public String getName() {
        return "freeze";
    }

    @Override
    public String getDescription() {
        return "Freeze the momentum of a player.";
    }

    @Override
    public String getSyntax() {
        return "moderatortools freeze <player>";
    }

    @Override
    public void executeCommand(Player player, String[] args) {

        if(args.length > 1) {
            Player suspectedPlayer = Bukkit.getPlayerExact(args[1]);

            frozenPlayers.add(suspectedPlayer);

            final String frozenPlayerStaffMessage = utils.getLanguageConfig().getString("language.sendFrozenMessageToStaffPlayer");

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', frozenPlayerStaffMessage).replace(suspectedPlayer.getName(), "{suspected_player}"));

            final boolean shouldBroadcastFrozenPlayerMessage = utils.getConfig().getBoolean("options.shouldBroadcastFrozenPlayerMessage");

            if(shouldBroadcastFrozenPlayerMessage == true) {
                final String frozenPlayerStaffMessageBroadcast = utils.getLanguageConfig().getString("language.broadcastFrozenPlayerMessage").replace(suspectedPlayer.getName(), "{suspected_player}");

                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(onlinePlayer.hasPermission("moderatortools.moderation.broadcastmessage")) {
                        onlinePlayer.sendMessage(frozenPlayerStaffMessageBroadcast);
                    }
                }
            }
        }
    }
}
