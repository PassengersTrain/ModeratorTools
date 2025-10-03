package io.github.passengerstrain.commands.subcommands;

import io.github.passengerstrain.commands.SubCommand;
import io.github.passengerstrain.utils.Config;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * A command that freezes a player, preventing any movement or momentum.
 * Moderators with the appropriate permissions can execute this command and will
 * receive notifications when a player is frozen. Additionally, the suspected player
 * will receive a notification.
 */

public class FreezePlayerCommand extends SubCommand {

    @NonNull Config config;

    public static Set<Player> frozenPlayers = new HashSet<>();

    public FreezePlayerCommand(@NonNull Config config) {
        this.config = config;
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

    /**
     * Freezes the suspected player, sends them a notification, and broadcasts the punishment message
     * to all online staff, including the reason for the punishment. It also handles permission checks
     * and message formatting.
     *
     * @param player The player executing the command.
     * @param args   The arguments containing the suspected player to be frozen and the reason
     *               for the punishment.
     */

    @Override
    public void executeCommand(Player player, String[] args) {
        if (player.hasPermission("moderatortools.moderator.freezecommand")) {

            if (args.length > 2) {

                Player suspectedPlayer = Bukkit.getPlayerExact(args[1]);
                String frozenReason = args[2];

                frozenPlayers.add(suspectedPlayer);

                final String frozenPlayerStaffMessage = config.getFrozenPunishmentStaffMessage(suspectedPlayer, frozenReason);
                player.sendMessage(frozenPlayerStaffMessage);

                if (config.shouldBroadcastPunishmentMessage()) {

                    final String frozenPlayerStaffMessageBroadcast = config.getFrozenPunishmentBroadcastMessage(suspectedPlayer, player, frozenReason);
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (onlinePlayer.hasPermission("moderatortools.moderator.frozenbroadcastmessage") && onlinePlayer != player) {
                            onlinePlayer.sendMessage(frozenPlayerStaffMessageBroadcast);
                        }
                    }

                    final String frozenPlayerMessage = config.getFrozenPunishmentPlayerMessage(suspectedPlayer, frozenReason);
                    suspectedPlayer.sendMessage(frozenPlayerMessage);
                }
            } else {
                final String noPermission = config.getNoPermissionMessage();
                if (noPermission != null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
                }
            }
        }
    }
}
