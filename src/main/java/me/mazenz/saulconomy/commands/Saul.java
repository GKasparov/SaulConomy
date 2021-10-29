package me.mazenz.saulconomy.commands;

import me.mazenz.saulconomy.SaulConomy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Saul implements CommandExecutor {

    private final SaulConomy plugin;

    public Saul(SaulConomy plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (args.length == 1) {

            if (!args[0].equalsIgnoreCase("reload")) {

                sender.sendMessage(ChatColor.RED + "Invalid command. Did you mean /saul reload");
                
                return true;
            }
            if (!(sender instanceof Player p && sender instanceof ConsoleCommandSender)) {

                plugin.reloadConfig();
                sender.sendMessage(ChatColor.RED + "[SaulConomy] Reloaded Configurations");
                
                return true;
            }

            if (!p.hasPermission("saul.reload")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin
                                .getConfig()
                                .getString("noPermission"))
                        .replace("%name%", p.getName())));
                return true;

            }

            plugin.reloadConfig();
            p.sendMessage(ChatColor.RED + "[SaulConomy] Reloaded Configurations");
                
            return true;
        }

        sender.sendMessage(ChatColor.YELLOW + "-- [SaulConomy] Current available commands --");
        sender.sendMessage(ChatColor.YELLOW + "/saul : Check available SaulConomy commands [Aliases]: /saulconomy");
        sender.sendMessage(ChatColor.YELLOW + "/pay : Send  money to other players");
        sender.sendMessage(ChatColor.YELLOW + "/bal : Check your account balance [Aliases]: /balance");
        sender.sendMessage(ChatColor.YELLOW + "/setbal : Set a user's balance [Aliases]: /setbalance, /setmoney");

        return true;
    }
}
