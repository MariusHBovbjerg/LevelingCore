package com.smellyalater.corefunctionality.gui.tradegui.adapter

import com.smellyalater.corefunctionality.gui.tradegui.classes.VillagerInventory
import com.smellyalater.corefunctionality.gui.tradegui.classes.VillagerTrade
import com.smellyalater.corefunctionality.gui.tradegui.events.VillagerInventoryCloseEvent
import com.smellyalater.corefunctionality.gui.tradegui.events.VillagerInventoryModifyEvent
import com.smellyalater.corefunctionality.gui.tradegui.events.VillagerInventoryOpenEvent
import com.smellyalater.corefunctionality.gui.tradegui.events.VillagerTradeCompleteEvent
import org.bukkit.craftbukkit.v1_18_2.inventory.CraftMerchantCustom
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.MerchantRecipe


class MerchantAdapter_v1_18_2_R0(toAdapt: VillagerInventory) : BaseAdapter(toAdapt), Listener {
    private var wrapped: CraftMerchantCustom? = null

    init {
        // TODO: Get rid of this magical value here somehow
        Bukkit.getServer().pluginManager.registerEvents(
            this,
            Bukkit.getPluginManager().getPlugin("CoreFunctionality")!!
        )

        wrapped = CraftMerchantCustom(toAdapt.name)
        wrapped?.recipes = toNMSRecipes()
    }

    override fun openFor(p: Player?) {
        p!!.openMerchant(wrapped, true)
        val event = VillagerInventoryOpenEvent(toAdapt, p)
        Bukkit.getPluginManager().callEvent(event)
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        if (event.player.uniqueId == toAdapt.forWho!!.uniqueId) {
            val closeEvent = VillagerInventoryCloseEvent(
                toAdapt,
                (event.player as Player)
            )
            Bukkit.getPluginManager().callEvent(closeEvent)
            HandlerList.unregisterAll(this) // Kill this event listener
        }
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        if (event.whoClicked.uniqueId == toAdapt.forWho!!.uniqueId) {
            val modifyEvent = VillagerInventoryModifyEvent(
                toAdapt,
                (event.whoClicked as Player), event.currentItem!!
            )
            Bukkit.getPluginManager().callEvent(modifyEvent)
            if (event.rawSlot == -999) return
            if (event.rawSlot == 2 && event.currentItem!!.type != Material.AIR) {
                val itemOne = toAdapt.forWho!!.openInventory.topInventory.getItem(0)
                val itemTwo = toAdapt.forWho!!.openInventory.topInventory.getItem(1)
                val result = event.currentItem
                val completeEvent = VillagerTradeCompleteEvent(
                    toAdapt,
                    (event.whoClicked as Player), VillagerTrade(itemOne!!, itemTwo, result!!, -1)
                )
                Bukkit.getPluginManager().callEvent(completeEvent)
            }
        }
    }

    fun toNMSRecipes(): List<MerchantRecipe> {
        val result: MutableList<MerchantRecipe> = ArrayList()
        for (trd in toAdapt.trades) {
            val toAdd = MerchantRecipe(trd.result, trd.maxUses)
            toAdd.addIngredient(trd.itemOne)
            if (trd.requiresTwoItems()) toAdd.addIngredient(trd.itemTwo!!)
            result.add(toAdd)
        }
        return result
    }
}