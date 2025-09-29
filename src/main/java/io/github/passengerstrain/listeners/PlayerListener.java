package io.github.passengerstrain.listeners;

import io.github.passengerstrain.commands.subcommands.FreezePlayerCommand;
import io.github.passengerstrain.utils.Utils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Instant;
import java.util.Date;

public class PlayerListener implements Listener {

    private Utils utils;

    public PlayerListener(Utils utils) {
        this.utils = utils;
    }

    @EventHandler
    private void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if(FreezePlayerCommand.frozenPlayers.contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onPlayerDamageEvent(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if(entity instanceof Player) {
            if(FreezePlayerCommand.frozenPlayers.contains(entity)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onPlayerDamageAnotherPlayer(EntityDamageByEntityEvent event) {
        Entity entity = event.getDamager();

        if(entity instanceof Player) {
            if(FreezePlayerCommand.frozenPlayers.contains(entity)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onPlayerDisconnectEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if(FreezePlayerCommand.frozenPlayers.contains(player)) {
            final boolean punishFrozenPlayerAddress = utils.getConfig().getBoolean("options.shouldPunishPlayerAddress");
            if(punishFrozenPlayerAddress == true) {
                final String addressPunishmentTitle = utils.getLanguageConfig().getString("language.addressPunishmentTitle");
                final String addressPunishmentSubTitle = utils.getLanguageConfig().getString("language.addressPunishmentSubTitle");
                final int addressPunishmentDuration = utils.getConfig().getInt("options.defaultPunishPlayerAddressDuration");
                final Date addressPunishmentExpiration = new Date(System.currentTimeMillis() + addressPunishmentDuration * 60L * 1000L);

                Bukkit.getBanList(BanList.Type.IP).addBan(player.getAddress().getAddress().getHostAddress(), addressPunishmentTitle, addressPunishmentExpiration, addressPunishmentSubTitle);
            }
        }


    }

}
