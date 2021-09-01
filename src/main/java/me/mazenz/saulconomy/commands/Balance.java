package me.mazenz.saulconomy.commands;

import me.mazenz.saulconomy.vault.SaulVaultEconomy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Balance implements CommandExecutor {

    private final SaulVaultEconomy economy;

    public Balance(SaulVaultEconomy economy) {
        this.economy = economy;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED
                    + "This command can only be used by players");
            return true;
        }
        sender.sendMessage(ChatColor.YELLOW
                + "Your balance is : $"
                + economy.getBalance((Player)
                sender));

        return true;
    }
}
