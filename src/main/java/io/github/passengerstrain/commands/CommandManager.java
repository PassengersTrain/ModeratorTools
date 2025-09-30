package io.github.passengerstrain.commands;

import io.github.passengerstrain.commands.subcommands.FreezePlayerCommand;
import io.github.passengerstrain.commands.subcommands.UnfreezePlayerCommand;
import io.github.passengerstrain.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;


public class CommandManager implements CommandExecutor {

    private Utils utils;

    private ArrayList<SubCommand> subcommands = new ArrayList<>();

    public CommandManager(Utils utils) {
        this.utils = utils;
    }

    public CommandManager(){
        subcommands.add(new FreezePlayerCommand(utils));
        subcommands.add(new UnfreezePlayerCommand(utils));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;

            if (args.length > 0){
                for (int i = 0; i < getSubcommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                        getSubcommands().get(i).executeCommand(player, args);
                    }
                }
            } else if(args.length == 0){
                for (int i = 0; i < getSubcommands().size(); i++){
                  final String insufficientCommandArgs = utils.getLanguageConfig().getString("language.insufficientCommandArgs");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', insufficientCommandArgs));
                }
            }

        }
        return true;
    }

    public ArrayList<SubCommand> getSubcommands(){
        return subcommands;
    }

}
