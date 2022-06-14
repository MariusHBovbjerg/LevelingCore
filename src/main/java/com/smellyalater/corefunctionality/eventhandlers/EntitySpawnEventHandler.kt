package com.smellyalater.corefunctionality.eventhandlers

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntitySpawnEvent


class EntitySpawnEventHandler : Listener {

    @EventHandler
    fun onMobSpawn(event: EntitySpawnEvent) {
    }

}