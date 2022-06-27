package com.smellyalater.corefunctionality.gui.tradegui.events

import com.smellyalater.corefunctionality.gui.tradegui.classes.VillagerInventory
import com.smellyalater.corefunctionality.gui.tradegui.classes.VillagerTrade
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList


class VillagerTradeCompleteEvent(val inventory: VillagerInventory, val player: Player, val trade: VillagerTrade) :
    Event() {

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        val handlerList = HandlerList()
    }
}