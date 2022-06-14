package com.smellyalater.corefunctionality.eventhandlers

import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class EntityDamageByEntityEventHandler : Listener {

    @EventHandler
    fun onEntityDamageByEntity(e: org.bukkit.event.entity.EntityDamageByEntityEvent){
        if(e.damager.type != EntityType.PLAYER) return

        e.damager.sendMessage(e.cause.toString())

        e.damager.sendMessage(e.damage.toString())
    }
}