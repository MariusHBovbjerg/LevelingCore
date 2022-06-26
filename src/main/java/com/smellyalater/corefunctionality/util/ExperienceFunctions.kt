package com.smellyalater.corefunctionality.util

import com.smellyalater.corefunctionality.model.PlayerData
import org.bukkit.entity.Player
import kotlin.math.ceil

class ExperienceFunctions {

    companion object {

        fun setProgressBar(player: Player, level: Int, experience: Double, requiredExperience: Double){
            val progress = ((experience - 0) / (requiredExperience - 0)).toFloat()
            player.level = level
            player.exp = progress
        }

        fun resetProgressBar(player: Player){
            player.level = 0
            player.exp = 0.0F
        }

        fun calculateRequiredExperience(level: Int): Double {
            return ceil(calculateMobReward(level) * (level/0.1) + 25.0)
        }

        fun calculateMobReward(mobLevel: Int): Double {
            return ceil(mobLevel * 2.718281828459045)
        }

        fun printPlayerData(player: Player, playerData: PlayerData, reqExp: Double){
            player.sendMessage("Current Experience: " +
                    playerData.experience.toString() + "\nCurrent Level " +
                    playerData.level.toString()  + "\nRequired Experience " +
                    reqExp.toString() + "\nUnused Skill Points " +
                    playerData.skillPoints.toString())
        }
    }
}