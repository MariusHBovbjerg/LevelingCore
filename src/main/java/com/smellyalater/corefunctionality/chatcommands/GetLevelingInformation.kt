package com.smellyalater.corefunctionality.chatcommands

import com.smellyalater.corefunctionality.db.PlayerDataRepository
import com.smellyalater.corefunctionality.util.ExperienceFunctions
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.transactions.transaction

class GetLevelingInformation(private val plugin: JavaPlugin, private val playerDataRepository: PlayerDataRepository) :
    Listener {

    init {
        plugin.getCommand("levelinfo")?.apply {
            plugin.logger.info("Level Info Command Defined")
        }?.setExecutor {sender, _, _, _ ->
            sender.getLevelingInformation(playerDataRepository)
        }
    }

    private fun CommandSender.getLevelingInformation(repo: PlayerDataRepository): Boolean {
        val player = this as? Player ?: return true
        getExpInformation(player, repo)
        return true
    }

    private fun getExpInformation(player: Player, repo: PlayerDataRepository){

        transaction {
            var playerData = repo.selectById(player.uniqueId)

            if (playerData == null) {
                playerData = repo.createBaseUser(player.uniqueId)
            }

            val reqExp = ExperienceFunctions.calculateRequiredExperience(playerData.level)

            ExperienceFunctions.printPlayerData(player, playerData, reqExp)
        }
    }
}