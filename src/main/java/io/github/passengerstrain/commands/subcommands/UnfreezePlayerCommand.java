package io.github.passengerstrain.commands.subcommands;

import io.github.passengerstrain.commands.SubCommand;
import io.github.passengerstrain.utils.Config;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * A command that unfreezes a previously frozen player.
 * Moderators with the appropriate permissions can execute this command and
 * will receive a notification when a player is unfrozen. The suspected player
 * will also receive a notification.
 */

public class UnfreezePlayerCommand extends SubCommand {

    @NonNull Config config;

    public UnfreezePlayerCommand(@NonNull Config config) {
        this.config = config;
    }

    @Override
    public String getName() {
        return "unfreeze";
    }

    @Override
    public String getDescription() {
        return "Un-freeze the momentum of a player.";
    }

    @Override
    public String getSyntax() {
        return "moderatortools unfreeze <player> <reason>";
    }

    /**
     * Unfreezes the previously suspected player, sends them a notification, and broadcasts the unfreeze message
     * to all online staff, including the reason. It also handles permission checks
     * and message formatting.
     *
     * @param player The player executing the command.
     * @param args   The arguments containing the previously suspected player to be unfrozen and the reason
     *               for punishment removal.
     */

    @Override
    public void executeCommand(Player player, String[] args) {
        if (player.hasPermission("moderatortools.moderator.unfreezecommand")) {

            if(args.length > 2) {

                Player frozenSuspectedPlayer = Bukkit.getPlayerExact(args[1]);
                String unfreezePlayerReason = args[2];

                if(FreezePlayerCommand.frozenPlayers.contains(frozenSuspectedPlayer)) {
                    FreezePlayerCommand.frozenPlayers.remove(frozenSuspectedPlayer);

                    final String unfrozenPlayerMessage = config.getPunishmentRemovalMessage(frozenSuspectedPlayer, unfreezePlayerReason);
                    if (unfrozenPlayerMessage != null) {
                        frozenSuspectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', unfrozenPlayerMessage));
                    }

                    final String unfrozenPlayerStaffMessage = config.getPunishmentRemovalStaffMessage(frozenSuspectedPlayer, unfreezePlayerReason);
                    if (unfrozenPlayerStaffMessage != null) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', unfrozenPlayerStaffMessage));
                    }

                    final boolean shouldBroadcastUnFrozenPlayerMessage = config.shouldBroadcastPunishmentRemovalMessage();

                    if (shouldBroadcastUnFrozenPlayerMessage) {
                        final String frozenPlayerStaffMessageBroadcast = config.getPunishmentRemovalBroadcastMessage(frozenSuspectedPlayer, player, unfreezePlayerReason);

                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            if (onlinePlayer.hasPermission("moderatortools.moderator.unfrozenbroadcastmessage") && onlinePlayer != player) {
                                onlinePlayer.sendMessage(frozenPlayerStaffMessageBroadcast);
                            }
                        }
                    }
                }
            }
        } else {
            final String noPermission = config.getNoPermissionMessage();
            if (noPermission != null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
            }
        }
    }
}
