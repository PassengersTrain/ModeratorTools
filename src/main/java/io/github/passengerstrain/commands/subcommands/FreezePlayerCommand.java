package io.github.passengerstrain.commands.subcommands;

import io.github.passengerstrain.commands.SubCommand;
import io.github.passengerstrain.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FreezePlayerCommand extends SubCommand {

    private final Utils utils;

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
        return "moderatortools freeze <player> <reason>";
    }

    @Override
    public void executeCommand(Player player, String[] args) {

        if(player.hasPermission("moderatortools.moderator.freezecommand")) {
            if (args.length > 2) {
                Player suspectedPlayer = Bukkit.getPlayerExact(args[1]);

                String frozenReason = args[2];

                frozenPlayers.add(suspectedPlayer);

                final String frozenPlayerStaffMessage = utils.getLanguageConfig().getString("language.sendFrozenMessageToStaffPlayer");

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', frozenPlayerStaffMessage)
                        .replace(suspectedPlayer.getName(), "{frozen_suspected_player}")
                        .replace(frozenReason, "{frozen_punishment_reason}"));

                final boolean shouldBroadcastFrozenPlayerMessage = utils.getConfig().getBoolean("options.shouldBroadcastFrozenPlayerStaffMessage");

                if (shouldBroadcastFrozenPlayerMessage) {
                    final String frozenPlayerStaffMessageBroadcast = utils.getLanguageConfig().getString("language.shouldBroadcastFrozenPlayerStaffMessage")
                            .replace(suspectedPlayer.getName(), "{frozen_suspected_player}").
                            replace(frozenReason, "{frozen_punishment_reason}");

                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (onlinePlayer.hasPermission("moderatortools.moderator.frozenbroadcastmessage") && onlinePlayer != player) {
                            onlinePlayer.sendMessage(frozenPlayerStaffMessageBroadcast);
                        }
                    }

                    final List<String> frozenPunishmentMessage = utils.getLanguageConfig().getStringList("language.frozenPunishmentPlayerMessage");
                    suspectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(frozenPunishmentMessage)));
                }
            }
        } else {
            final String noPermission = utils.getLanguageConfig().getString("language.noPermission");
            if (noPermission != null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
            }
        }
    }
}
