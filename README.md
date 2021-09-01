# SaulEconomy
Minecraft Economy Plugin.

# Dependencies
If you wish to build this yourself, add Vault.jar to your build path (https://www.spigotmc.org/resources/vault.34315/). 
On Maven projects add the following lines to your pom.xml:

`        <dependency>
            <groupId>com.github.MilkBowl</groupId>
            <artifactId>VaultAPI</artifactId>
            <version>1.7</version>
            <scope>provided</scope>
        </dependency>`
`        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>`

On Gradle Projects, add:

`repositories {
    maven { url 'https://jitpack.io' }
}
dependencies {
    compileOnly "com.github.MilkBowl:VaultAPI:1.7"
}`

As per the instructions on https://github.com/MilkBowl/VaultAPI

# Features
- setBalance EconomyResponse
- Uses SQLite, more suitable for large-scale servers. (However, if you wish to use MySQL, all you'll need to edit is the DataBase.Java)
- Pay/ Setbalance Configurations
- Baltop Command

SaulEconomy does not support "Banks" (Multiple banks for each user). It only supports single User bank accounts. This won't change.

# Setup
No database setup is required as it is SQLite, once you build the plugin, just drag n' drop the .jar into your plugins folder.
