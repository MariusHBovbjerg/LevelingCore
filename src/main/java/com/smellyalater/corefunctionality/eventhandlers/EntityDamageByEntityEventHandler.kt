package com.smellyalater.corefunctionality.eventhandlers

import com.smellyalater.corefunctionality.db.PlayerDataRepository
import com.smellyalater.corefunctionality.util.DamageFunctions
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class EntityDamageByEntityEventHandler(private val playerDataRepository: PlayerDataRepository) : Listener {

    @EventHandler
    fun onEntityDamageByPlayer(e: org.bukkit.event.entity.EntityDamageByEntityEvent){
        if(e.damager.type != EntityType.PLAYER) return

        val player = e.damager as Player
        var damage = e.damage

        if(DamageFunctions.isCritical(e)){
            damage /=  1.5
            player.sendMessage("Critical hit!")

        }

        player.sendMessage("Base Damage: ${damage}")

        damage = calculateDexterityBonus(player, damage)

        player.sendMessage("Dex Buff Damage: ${damage}")

        damage = calculateStrengthBonus(player, damage)

        player.sendMessage("Str Buff Damage: ${damage}")

        e.damage = damage
    }

    @EventHandler
    fun onPlayerDamageByEntity(e: org.bukkit.event.entity.EntityDamageByEntityEvent){
        if(e.entity.type != EntityType.PLAYER) return

        val player = e.entity as Player

        player.sendMessage("Base Damage: ${e.damage}")

        e.damage = calculateEnduranceBonus(player, e.damage)

        player.sendMessage("End Debuff Damage: ${e.damage}")
    }

    private fun calculateStrengthBonus(player: Player, baseDamage: Double): Double{

        val playerData = playerDataRepository.getPlayerDataOrEnsureCreateBaseUser(player.uniqueId)

        return baseDamage + (playerData.strength.toDouble() / 2)
    }

    private fun calculateEnduranceBonus(player: Player, baseDamage: Double): Double{

        val playerData = playerDataRepository.getPlayerDataOrEnsureCreateBaseUser(player.uniqueId)

        val postBonus = baseDamage - (playerData.endurance.toDouble() / 2)

        return if (postBonus >= 0.0) postBonus else 0.0
    }

    private fun calculateDexterityBonus(player: Player, baseDamage: Double): Double{

        val playerData = playerDataRepository.getPlayerDataOrEnsureCreateBaseUser(player.uniqueId)

        return when(playerData.dexterity){
            in 5..9 -> baseDamage + (baseDamage * 0.25)
            in 10..14 -> baseDamage + (baseDamage * 0.5)
            in 15..19 -> baseDamage + (baseDamage * 0.75)
            in 20..24 -> baseDamage + (baseDamage * 1.0)
            in 25..29 -> baseDamage + (baseDamage * 1.5)
            30 -> baseDamage + (baseDamage * 2.0)
            else -> baseDamage
        }
    }
}