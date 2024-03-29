package me.mazenz.saulconomy;

import me.mazenz.saulconomy.commands.*;
import me.mazenz.saulconomy.vault.SaulVaultEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Logger;

public class SaulConomy extends JavaPlugin {

    private Database db;

    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        Logger logger = this.getLogger();
        new UpdateChecker(this, 88223).getVersion(version -> {
            logger.info("Checking for Updates...");
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("No new version available");
            } else {
                logger.info("There is a new version of SaulConomy. https://www.spigotmc.org/resources/saulconomy.88223/");
            }
        });

        this.db = new Database(this);
        this.db.load();

        // Vault integration
        SaulVaultEconomy economy = new SaulVaultEconomy(db);
        Bukkit.getServicesManager().register(Economy.class, economy, this, ServicePriority.Normal);

        // Commands
        getCommand("balance").setExecutor(new Balance(economy, this));
        getCommand("setbalance").setExecutor(new SetBalance(economy, this));
        getCommand("pay").setExecutor(new Pay(economy, this));
        getCommand("saul").setExecutor(new Saul(this));
        getCommand("baltop").setExecutor(new Baltop(economy, this));
    }

    @Override
    public void onDisable() {
        try {
            db.getConnection().close();
        } catch (SQLException exception) {
            db.report(exception);
        }
    }
}
