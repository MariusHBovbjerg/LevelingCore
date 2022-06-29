package com.smellyalater.corefunctionality

import com.smellyalater.corefunctionality.chatcommands.GetLevelingInformation
import com.smellyalater.corefunctionality.chatcommands.LevelCommands
import com.smellyalater.corefunctionality.db.PlayerDataRepository
import com.smellyalater.corefunctionality.db.PlayerTable
import com.smellyalater.corefunctionality.eventhandlers.*
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class CoreFunctionality : JavaPlugin(), Listener {

    private var playerDataRepository: PlayerDataRepository = PlayerDataRepository()

    override fun onEnable() {

        Database.connect("jdbc:h2:./CoreFunctionalityStore", "org.h2.Driver")

        transaction{
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(PlayerTable)
        }

        println("CoreFunctionality has been enabled!")
        server.pluginManager.registerEvents(PlayerExpChangeEventHandler(), this)
        server.pluginManager.registerEvents(AsyncPlayerChatEventHandler(), this)
        server.pluginManager.registerEvents(EntityDeathEventHandler(playerDataRepository), this)
        server.pluginManager.registerEvents(EntityDamageByEntityEventHandler(playerDataRepository), this)
        server.pluginManager.registerEvents(EntitySpawnEventHandler(), this)
        server.pluginManager.registerEvents(GetLevelingInformation(this, playerDataRepository), this)
        server.pluginManager.registerEvents(LevelCommands(this, playerDataRepository), this)
        server.pluginManager.registerEvents(OpenGeneralMenu(this, playerDataRepository), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}






















