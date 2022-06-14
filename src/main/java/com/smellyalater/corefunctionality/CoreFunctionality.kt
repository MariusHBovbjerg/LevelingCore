package com.smellyalater.corefunctionality

import com.smellyalater.corefunctionality.db.PlayerTable
import com.smellyalater.corefunctionality.eventhandlers.*
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class CoreFunctionality : JavaPlugin(), Listener {
    override fun onEnable() {

        Database.connect("jdbc:h2:./CoreFunctionalityStore", "org.h2.Driver")

        transaction{
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(PlayerTable)
        }

        println("CoreFunctionality has been enabled!")
        Bukkit.getServer().pluginManager.registerEvents(PlayerExpChangeEventHandler(), this)
        Bukkit.getServer().pluginManager.registerEvents(AsyncPlayerChatEventHandler(), this)
        Bukkit.getServer().pluginManager.registerEvents(EntityDeathEventHandler(), this)
        Bukkit.getServer().pluginManager.registerEvents(EntityDamageByEntityEventHandler(), this)
        Bukkit.getServer().pluginManager.registerEvents(EntitySpawnEventHandler(), this)
    }
    override fun onDisable() {
        // Plugin shutdown logic
    }
}






















