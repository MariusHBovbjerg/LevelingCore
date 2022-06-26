package com.smellyalater.corefunctionality.eventhandlers

import com.smellyalater.corefunctionality.db.PlayerDataRepository
import com.smellyalater.corefunctionality.util.ExperienceFunctions
import com.smellyalater.corefunctionality.util.ExperienceFunctions.Companion.calculateMobReward
import com.smellyalater.corefunctionality.util.ExperienceFunctions.Companion.calculateRequiredExperience
import com.smellyalater.corefunctionality.util.ExperienceFunctions.Companion.setProgressBar
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.math.ceil

class EntityDeathEventHandler(private var playerDataRepository: PlayerDataRepository) : Listener{

    private var LEVELBRACKETCOLOURCODE = "§7"
    private var LEVELCHARACTERCOLOURCODE = "§f"

    @EventHandler
    fun onEntityKilled(e: org.bukkit.event.entity.EntityDeathEvent){

        val killer = e.entity.killer
        if(killer?.type != EntityType.PLAYER) return

        killer.sendMessage("You killed ${e.entity.type} named ${e.entity.name}")

        // get the level from entity name with format [LvlX]
        val split = e.entity.name.split(" ")[0]
        // Remove MC colours
        val level = split
            .replace("$LEVELBRACKETCOLOURCODE[$LEVELCHARACTERCOLOURCODE" + "L", "")
            .replace("$LEVELBRACKETCOLOURCODE]", "")
            .toInt()

        gainExperienceAndEvaluateLevelUp(killer, ceil(calculateMobReward(level)).toInt())
    }

    private fun gainExperienceAndEvaluateLevelUp(player: Player, amount: Int) {

        val playerData = playerDataRepository.getPlayerDataOrEnsureCreateBaseUser(player.uniqueId)

        val reqExp = calculateRequiredExperience(playerData.level)

        if(playerData.experience + amount >= reqExp){
            playerData.level++
            playerData.skillPoints++
            playerData.experience = playerData.experience + amount - reqExp
        }else{
            playerData.experience += amount
        }

        ExperienceFunctions.printPlayerData(player, playerData, reqExp)

        setProgressBar(player, playerData.level, playerData.experience, calculateRequiredExperience(playerData.level))

        transaction{ playerDataRepository.update(playerData) }
    }
}