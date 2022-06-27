package com.smellyalater.corefunctionality.gui.tradegui.events

import com.smellyalater.corefunctionality.gui.tradegui.classes.VillagerInventory
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack


class VillagerInventoryModifyEvent(val inventory: VillagerInventory, val player: Player, val itemChanged: ItemStack) :
    Event() {

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        val handlerList = HandlerList()
    }
}