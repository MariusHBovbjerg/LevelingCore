package com.smellyalater.corefunctionality.eventhandlers

import de.tr7zw.nbtapi.NBTContainer
import de.tr7zw.nbtapi.NBTItem
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.inventory.ItemStack

class AsyncPlayerChatEventHandler() : Listener {

    @EventHandler
    fun onPlayerChat(e: AsyncPlayerChatEvent){
        e.player.sendMessage("You said: .${e.message}.")
        // switch statement to check for the message and do the appropriate action
        when(e.message) {
            "!addItem" -> {
                addItem(e)
                e.isCancelled = true
            }
        }
    }

    private fun addItem(e: AsyncPlayerChatEvent){
        val item = ItemStack(Material.DIAMOND_SWORD, 1)
        // configure item nbt data
        val ntbItem = NBTItem(item)
        ntbItem.mergeCompound(NBTContainer("{AttributeModifiers:[{AttributeName:\"generic.attack_speed\",Name:\"generic.attack_speed\",Amount:10,Operation:0,UUID:[I;94078136,941114403,-1210508070,-963957080],Slot:\"mainhand\"}]}"))
        item.itemMeta = ntbItem.item.itemMeta
        // add item to player inventory
        e.player.inventory.addItem(item)
    }
}