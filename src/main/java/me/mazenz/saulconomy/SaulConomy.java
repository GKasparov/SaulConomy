package me.mazenz.saulconomy;

import me.mazenz.saulconomy.commands.Balance;
import me.mazenz.saulconomy.vault.SaulVaultEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class SaulConomy extends JavaPlugin {

    private Database db;

    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        this.db = new Database(this);
        this.db.load();

        // Vault integration
        SaulVaultEconomy economy = new SaulVaultEconomy(db);
        Bukkit.getServicesManager().register(Economy.class, economy, this, ServicePriority.Normal);

        // Commands
        getCommand("balance").setExecutor(new Balance(economy));
    }

    @Override
    public void onDisable() {
        try {
            db.getConnection().close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
