package me.mazenz.saulconomy.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Saul implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.YELLOW + "-- [SaulConomy] Current available commands --");
        sender.sendMessage(ChatColor.YELLOW + "/saul : Check available SaulConomy commands [Aliases]: /saulconomy");
        sender.sendMessage(ChatColor.YELLOW + "/pay : Send  money to other players");
        sender.sendMessage(ChatColor.YELLOW + "/bal : Check your account balance [Aliases]: /balance");
        sender.sendMessage(ChatColor.YELLOW + "/setbal : Set a user's balance [Aliases]: /setbalance, /setmoney");

        return true;
    }
}
