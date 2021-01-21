package me.mazenz.saulconomy.commands;

import me.mazenz.saulconomy.vault.SaulVaultEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Pay implements CommandExecutor {

    private final SaulVaultEconomy economy;

    public Pay(SaulVaultEconomy economy) {
        this.economy = economy;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                OfflinePlayer target = Bukkit.getPlayer(args[0]);

                if (target != null) {
                    if (Helper.isDouble(args[1])) {
                        double amount = Double.parseDouble(args[1]);

                        if (economy.has(p, amount)) {
                            if (economy.withdrawPlayer(p, amount).type == EconomyResponse.ResponseType.SUCCESS) {
                                economy.depositPlayer(target, amount);
                            } else {
                                p.sendMessage(ChatColor.RED + "Unable to process transaction");
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Insufficient funds for this transaction");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "The number you provided is invalid");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "The player you provided does not exist");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "This command can only be run in-game");
            }
        }

        return true;
    }
}
