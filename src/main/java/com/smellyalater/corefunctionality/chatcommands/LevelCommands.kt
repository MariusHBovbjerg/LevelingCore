package com.smellyalater.corefunctionality.chatcommands

import com.smellyalater.corefunctionality.db.PlayerDataRepository
import com.smellyalater.corefunctionality.db.PlayerTable
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class LevelCommands(private val plugin: JavaPlugin, private val playerDataRepository: PlayerDataRepository) :
    Listener {

    init {
        plugin.getCommand("giveSkillPoints")?.apply {
            plugin.logger.info("Give Skill Points Defined")
        }?.setExecutor { sender, _, _, args ->

            if (args.isEmpty()) {
                sender.sendMessage("Usage: /giveSkillPoints <amount>")
                return@setExecutor true
            }

            sender.getLevelingInformation(playerDataRepository, args[0].toInt())
        }
    }

    private fun CommandSender.getLevelingInformation(repo: PlayerDataRepository, count: Int): Boolean {
        val player = this as? Player ?: return true
        setSkillPoints(player, repo, count)
        return true
    }

    private fun setSkillPoints(player: Player, repo: PlayerDataRepository, count: Int) {

        if(count < 0) {
            player.sendMessage("You can't set a negative amount of skill points")
            return
        }

        transaction {
            var playerData = repo.selectById(player.uniqueId)

            if (playerData == null) {
                playerData = repo.createBaseUser(player.uniqueId)
            }

            PlayerTable.update ({ PlayerTable.uuid eq playerData.id }) {
                it[skillPoints] = playerData.skillPoints + count
            }
        }
    }
}