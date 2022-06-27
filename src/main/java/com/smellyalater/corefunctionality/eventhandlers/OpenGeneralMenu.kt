package com.smellyalater.corefunctionality.eventhandlers

import com.smellyalater.corefunctionality.db.PlayerDataRepository
import com.smellyalater.corefunctionality.gui.chestgui.components.GenericComponents.Companion.border
import com.smellyalater.corefunctionality.gui.chestgui.components.ShopGUI.Companion.shopGUI
import com.smellyalater.corefunctionality.gui.chestgui.components.SkillGUI.Companion.skillGUI
import com.smellyalater.corefunctionality.gui.chestgui.GUI
import com.smellyalater.corefunctionality.gui.chestgui.gui
import com.smellyalater.corefunctionality.gui.chestgui.item
import com.smellyalater.corefunctionality.gui.chestgui.open
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class OpenGeneralMenu(private val plugin: JavaPlugin, private val repository: PlayerDataRepository) : Listener{

    @EventHandler
    fun onSwapHand(event: org.bukkit.event.player.PlayerSwapHandItemsEvent){
        event.isCancelled = true

        val player = event.player

        player.open(player.generalMenuGUI(repository, plugin))
    }

    companion object {

        fun Player.generalMenuGUI(repository: PlayerDataRepository, plugin: JavaPlugin): GUI {

            return gui("Menu", 3, plugin) {
                border(3)

                slot(3, 1) {
                    item = item(Material.IRON_SWORD) {
                        name = "Shop"
                    }
                    onclick = {
                        p -> p.shopGUI()
                    }
                }

                slot(5, 1) {
                    item = item(Material.SHIELD) {
                        name = "Skills"
                    }
                    onclick = { p ->
                        p.open(skillGUI(repository, plugin))
                        refresh()
                    }
                }
            }
        }
    }
}