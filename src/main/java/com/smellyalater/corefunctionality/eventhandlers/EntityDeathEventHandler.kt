package com.smellyalater.corefunctionality.eventhandlers

import com.smellyalater.corefunctionality.db.PlayerDataRepository
import com.smellyalater.corefunctionality.db.PlayerTable
import com.smellyalater.corefunctionality.model.PlayerData
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import kotlin.math.ceil

class EntityDeathEventHandler : Listener{

    var playerDataRepository: PlayerDataRepository = PlayerDataRepository();

    var LEVELBRACKETCOLOURCODE = "§7"
    var LEVELCHARACTERCOLOURCODE = "§f"

    @EventHandler
    fun onEntityKilled(e: org.bukkit.event.entity.EntityDeathEvent){

        val killer = e.entity.killer
        if(killer?.type != EntityType.PLAYER) return

        killer.sendMessage("You killed ${e.entity.type} named ${e.entity.name}")

        // get the level from entity name with format [LvlX]
        val split = e.entity.name.split(" ")[0]
        // Remove MC colours
        val level = split.replace(LEVELBRACKETCOLOURCODE + "[" + LEVELCHARACTERCOLOURCODE + "L", "").replace(LEVELBRACKETCOLOURCODE + "]", "").toInt()

        gainExperienceAndEvaluateLevelUp(killer, ceil(calculateMobReward(level)).toInt())
    }

    private fun gainExperienceAndEvaluateLevelUp(player: Player, amount: Int) {
        transaction {
            var playerData: PlayerData? = playerDataRepository.selectById(player.uniqueId)

            if(playerData == null){
                playerData = playerDataRepository.create(PlayerData(player.uniqueId, 0.0, 0, 0, 0, 0))
            }

            val reqExp = calculateRequiredExperience(playerData.level)

            if(playerData.experience + amount >= reqExp){
                playerData.level++
                playerData.experience = playerData.experience + amount - reqExp
            }else{
                playerData.experience += amount
            }

            player.sendMessage("Current Experience: " +
                    playerData.experience.toString() + "\nCurrent Level " +
                    playerData.level.toString() + "\nRequired Experience " +
                    reqExp.toString())

            setProgressBar(player, playerData.level, playerData.experience, calculateRequiredExperience(playerData.level))

            PlayerTable.update({ PlayerTable.uuid eq playerData.id }) {
                it[experience] = playerData.experience
                it[level] = playerData.level
            }
        }
    }

    private fun setProgressBar(player: Player, level: Int, experience: Double, requiredExperience: Double){
        val progress = ((experience - 0) / (requiredExperience - 0)).toFloat()
        player.level = level
        player.exp = progress
    }

    private fun calculateRequiredExperience(level: Int): Double {
        return ceil(calculateMobReward(level) * (level/0.1) + 25.0)
    }

    private fun calculateMobReward(mobLevel: Int): Double {
        return ceil(mobLevel * 2.71828182845904523)
    }
}