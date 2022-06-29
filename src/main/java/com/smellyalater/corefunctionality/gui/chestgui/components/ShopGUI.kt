package com.smellyalater.corefunctionality.gui.chestgui.components

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.util.Location
import com.sk89q.worldguard.LocalPlayer
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import com.smellyalater.corefunctionality.gui.tradegui.classes.VillagerInventory
import com.smellyalater.corefunctionality.gui.tradegui.classes.VillagerTrade
import com.smellyalater.corefunctionality.util.HexColorFormat.Companion.format
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack


class ShopGUI {
    companion object {

        fun Player.shopGUI() {

            val loc: Location = BukkitAdapter.adapt(this.location)
            val container = WorldGuard.getInstance().platform.regionContainer
            val query = container.createQuery()
            val set = query.getApplicableRegions(loc)

            for (region in set.regions){
                this.sendMessage(region.id)
            }



            val trades: ArrayList<VillagerTrade> = ArrayList()
            trades.add(
                VillagerTrade(
                ItemStack(Material.DIRT), ItemStack(Material.DIRT),
                ItemStack(Material.GLASS), 10)
            )

            val inventory = VillagerInventory(trades, this)
            inventory.name = format("#123123Weed shop based?????")
            inventory.open()

        }
    }
}