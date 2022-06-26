package com.smellyalater.corefunctionality.chatcommands

import com.smellyalater.corefunctionality.db.PlayerDataRepository
import com.smellyalater.corefunctionality.util.ExperienceFunctions.Companion.resetProgressBar
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.transactions.transaction

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

        plugin.getCommand("resetPlayer")?.apply {
            plugin.logger.info("Give Skill Points Defined")
        }?.setExecutor { sender, _, _, _ ->

            sender.resetPlayer(playerDataRepository)
        }

    }

    private fun CommandSender.getLevelingInformation(repo: PlayerDataRepository, count: Int): Boolean {
        val player = this as? Player ?: return true
        setSkillPoints(player, repo, count)
        return true
    }

    private fun CommandSender.resetPlayer(repo: PlayerDataRepository): Boolean {
        val player = this as? Player ?: return true
        resetPlayer(player, repo)
        return true
    }

    private fun setSkillPoints(player: Player, repo: PlayerDataRepository, count: Int) {

        if(count < 0) {
            player.sendMessage("You can't set a negative amount of skill points")
            return
        }

        val playerData = repo.getPlayerDataOrEnsureCreateBaseUser(player.uniqueId)

        playerData.skillPoints += count

        transaction { repo.update(playerData) }

    }

    private fun resetPlayer(player: Player, repo: PlayerDataRepository) {

        val playerData = repo.getPlayerDataOrEnsureCreateBaseUser(player.uniqueId)

        transaction {
            repo.delete(playerData.id)
            repo.createBaseUser(player.uniqueId)
        }

        resetProgressBar(player)
    }
}