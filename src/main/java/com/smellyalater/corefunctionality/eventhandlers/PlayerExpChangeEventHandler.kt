package com.smellyalater.corefunctionality.eventhandlers

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PlayerExpChangeEventHandler : Listener{

    @EventHandler
    fun onExpPickUp(e: org.bukkit.event.player.PlayerExpChangeEvent){
        e.amount = 0
    }
}