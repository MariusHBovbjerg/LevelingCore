package com.smellyalater.corefunctionality.gui.tradegui.events

import com.smellyalater.corefunctionality.gui.tradegui.classes.VillagerInventory
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList


class VillagerInventoryCloseEvent(val inventory: VillagerInventory, val player: Player) : Event() {

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        val handlerList = HandlerList()
    }
}