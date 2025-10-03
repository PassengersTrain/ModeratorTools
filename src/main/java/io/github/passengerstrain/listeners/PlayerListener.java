package io.github.passengerstrain.listeners;

import io.github.passengerstrain.commands.subcommands.FreezePlayerCommand;
import io.github.passengerstrain.utils.Config;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;

public class PlayerListener implements Listener {

    private final Config config;

    public PlayerListener(Config config) {
        this.config = config;
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
    private void onPlayerBlockInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(FreezePlayerCommand.frozenPlayers.contains(player)) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    private void onPlayerBlockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if(FreezePlayerCommand.frozenPlayers.contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onPlayerBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(FreezePlayerCommand.frozenPlayers.contains(player)) {
            event.setCancelled(true);
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

            if(config.shouldPunishPlayerAddress()) {

                final String addressPunishmentTitle = config.getAddressPunishmentTitle();

                final String addressPunishmentSubTitle = config.getAddressPunishmentSubTitle();

                final int addressPunishmentDuration = config.getDefaultPunishmentDuration();

                final Date addressPunishmentExpiration = new Date(System.currentTimeMillis() + addressPunishmentDuration * 60L * 1000L);

                Bukkit.getBanList(BanList.Type.IP).addBan(player.getAddress().getAddress().getHostAddress(), addressPunishmentTitle, addressPunishmentExpiration, addressPunishmentSubTitle);
            }
        }
        FreezePlayerCommand.frozenPlayers.remove(player);
    }
}
