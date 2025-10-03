package io.github.passengerstrain.utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Class for handling in-game messages and formatting.
 */
public class Config {
    @NonNull Utils utils;

    public Config(@NonNull Utils utils) {
        this.utils = utils;
    }

    public String getFrozenPunishmentStaffMessage(@NonNull Player suspectedPlayer, @NonNull String punishmentReason) {
        final String punishmentStaffMessage = utils.getLanguageConfig().getString("language.frozen-feature.send-punishment-message-to-staff");
        if (punishmentStaffMessage != null) {
            return ChatColor.translateAlternateColorCodes('&', punishmentStaffMessage)
                    .replace("{one_frozen_suspected_player}", suspectedPlayer.getName())
                    .replace("{one_frozen_punishment_reason}", punishmentReason);
        }
        return "You have frozen " + suspectedPlayer.getName() + " for " + punishmentReason;
    }


    public String getFrozenPunishmentBroadcastMessage(@NonNull Player suspectedPlayer, @NonNull Player commandExecutor, @NonNull String punishmentReason) {
      final String  punishmentStaffBroadcastMessage = utils.getLanguageConfig().getString("language.frozen-feature.send-punishment-broadcast-message-to-staff");
      if(punishmentStaffBroadcastMessage != null) {
          return ChatColor.translateAlternateColorCodes('&', punishmentStaffBroadcastMessage)
                  .replace("{two_frozen_suspected_player}", suspectedPlayer.getName())
                  .replace("{two_frozen_punishment_reason}", punishmentReason)
                  .replace("{two_frozen_command_executor}", commandExecutor.getName());
      }
      return "A player with the name " + suspectedPlayer.getName() + " has been frozen for " + punishmentReason + " by " + commandExecutor.getName();
    }

    public String getPunishmentRemovalBroadcastMessage(@NonNull Player suspectedPlayer, @NonNull Player commandExecutor, @NonNull String punishmentRemovalReason) {
        final String punishmentRemovalBroadcastMessage = utils.getLanguageConfig().getString("language.unfrozen-feature.send-punishment-removal-broadcast-message-to-staff");
        if (punishmentRemovalBroadcastMessage != null) {
            return ChatColor.translateAlternateColorCodes('&', punishmentRemovalBroadcastMessage)
                    .replace("{three_un_frozen_suspected_player}", suspectedPlayer.getName())
                    .replace("{three_un_frozen_command_executor}", commandExecutor.getName())
                    .replace("{three_un_frozen_punishment_removal_reason}", punishmentRemovalReason);
        }
        return "A player with the name " + suspectedPlayer.getName() + " has been un-frozen for " + punishmentRemovalReason + " by " + commandExecutor.getName();
    }

    public String getFrozenPunishmentPlayerMessage(@NonNull Player suspectedPlayer, @NonNull String punishmentReason) {
        final List<String> frozenPunishmentPlayerMessage = utils.getLanguageConfig().getStringList("language.frozen-feature.send-punishment-message-to-player");
        String concatMessage = String.join("\n", frozenPunishmentPlayerMessage);
        return ChatColor.translateAlternateColorCodes('&', concatMessage)
                .replace("{three_frozen_suspected_player}", suspectedPlayer.getName())
                .replace("{three_frozen_punishment_reason}", punishmentReason);
    }

    public String getNoPermissionMessage() {
        final String noPermissionMessage = utils.getLanguageConfig().getString("language.other.no-permission");
        if (noPermissionMessage != null) {
            return ChatColor.translateAlternateColorCodes('&', noPermissionMessage);
        }
        return "You cannot execute this command.";
    }

    public String getPunishmentRemovalMessage(@NonNull Player suspectedPlayer, @NonNull String removalReason) {
        final String unfrozenPlayerMessage = utils.getLanguageConfig().getString("language.unfrozen-feature.send-punishment-removal-message");
        return ChatColor.translateAlternateColorCodes('&', unfrozenPlayerMessage)
                .replace("{one_punishment_removal_reason}", removalReason);
    }

    public String getPunishmentRemovalStaffMessage(@NonNull Player suspectedPlayer, @NonNull String removalReason) {
        final String unfrozenPlayerMessage = utils.getLanguageConfig().getString("language.unfrozen-feature.send-punishment-removal-message-to-staff");
        return ChatColor.translateAlternateColorCodes('&', unfrozenPlayerMessage)
                .replace("{two_punishment_removal_reason}", removalReason)
                .replace("{two_punishment_remove_of_suspected_player}", suspectedPlayer.getName());
    }

    public boolean shouldBroadcastPunishmentMessage() {
        return utils.getConfig().getBoolean("options.should-broadcast-message-to-staff");
    }

    public boolean shouldBroadcastPunishmentRemovalMessage() {
        return utils.getConfig().getBoolean("options.should-broadcast-punishment-removal-to-staff");
    }

    public boolean shouldPunishPlayerAddress() {
        return utils.getConfig().getBoolean("options.should-punish-suspected-player-address");
    }

    public String getAddressPunishmentTitle() {
     return utils.getLanguageConfig().getString("language.frozen-feature.player-address-punishment-title");
    }

    public String getInsufficientCommandArgs() {
        return utils.getLanguageConfig().getString("language.other.insufficient-command-args");
    }

    public String getNotPlayerCommandExecutor() {
        return utils.getLanguageConfig().getString("language.other.non-player-command-execution");
    }

    public String getAddressPunishmentSubTitle() {
        return utils.getLanguageConfig().getString("language.frozen-feature.player-address-punishment-subtitle");
    }

    public int getDefaultPunishmentDuration() {
        return utils.getConfig().getInt("options.default-address-punishment-duration");
    }
}

