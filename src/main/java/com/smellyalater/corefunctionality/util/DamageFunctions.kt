package com.smellyalater.corefunctionality.util

import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType

class DamageFunctions {
    companion object {
        fun isCritical(e: org.bukkit.event.entity.EntityDamageByEntityEvent): Boolean {

            val player = e.damager as Player
            val entity = e.entity

            return player.fallDistance > 0.0F &&
                                !player.isOnGround &&
                                !player.isClimbing &&
                                !player.isInWater &&
                                !player.hasPotionEffect(PotionEffectType.BLINDNESS) &&
                                player.vehicle == null &&
                                !player.isSprinting &&
                                entity.isValid
        }
    }
}