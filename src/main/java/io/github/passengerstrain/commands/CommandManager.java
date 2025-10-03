package io.github.passengerstrain.commands;

import io.github.passengerstrain.commands.subcommands.FreezePlayerCommand;
import io.github.passengerstrain.commands.subcommands.UnfreezePlayerCommand;
import io.github.passengerstrain.utils.Config;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    private final Config config;
    private final ArrayList<SubCommand> subcommands = new ArrayList<>();

    public CommandManager(@NonNull Config config) {
        this.config = config;

        subcommands.add(new FreezePlayerCommand(config));
        subcommands.add(new UnfreezePlayerCommand(config));
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;

            if (args.length > 0){

                for (int i = 0; i < getSubcommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                        getSubcommands().get(i).executeCommand(player, args);
                    }
                }
            } else {
                for (int i = 0; i < getSubcommands().size(); i++){
                  final String insufficientCommandArgs = config.getInsufficientCommandArgs();
                    assert insufficientCommandArgs != null;
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', insufficientCommandArgs));
                }
            }
        } else {
            final String nonConsoleCommand = config.getNotPlayerCommandExecutor();
            if (nonConsoleCommand != null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', nonConsoleCommand));
            }
        }
        return true;
    }
    public ArrayList<SubCommand> getSubcommands(){
        return subcommands;
    }
}
