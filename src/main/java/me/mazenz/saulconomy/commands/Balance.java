package me.mazenz.saulconomy.commands;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Balance implements CommandExecutor {
    private final Economy e;

    public Balance(Economy e) {
        this.e = e;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        if (args.length == 0) {
            return true;
        }

        Player p = (Player) sender;
        p.sendMessage("Your old balance is " + e.getBalance(p));

        // Add currentBalance - args[0]
        // newBalance = currentBalance + currentBalance - args[0]
        // Which becomes newBalance = args[0]
        e.depositPlayer(p, e.getBalance(p) - Double.parseDouble(args[0]));

        return true;
    }
}
