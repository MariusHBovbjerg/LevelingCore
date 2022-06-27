package com.smellyalater.corefunctionality.gui.chestgui.components

import com.smellyalater.corefunctionality.db.PlayerDataRepository
import com.smellyalater.corefunctionality.gui.chestgui.GUI
import com.smellyalater.corefunctionality.gui.chestgui.components.GenericComponents.Companion.border
import com.smellyalater.corefunctionality.gui.chestgui.gui
import com.smellyalater.corefunctionality.gui.chestgui.item
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.transactions.transaction

class SkillGUI {

    companion object {

        fun Player.skillGUI(repo: PlayerDataRepository, plugin: JavaPlugin): GUI {

            var selectedSkill = ""

            val playerData = repo.getPlayerDataOrEnsureCreateBaseUser(this.uniqueId)

            return gui("Skill Menu", 5, plugin) {
                border(5)

                slot(2, 1) {
                    item = item(Material.IRON_SWORD) {
                        name = "Strength | lvl ${playerData.strength}"
                    }
                    onclick = {
                        selectedSkill = "Strength"
                        refresh()
                    }
                }

                slot(4, 1) {
                    item = item(Material.SHIELD) {
                        name = "Endurance | lvl ${playerData.endurance}"
                    }
                    onclick = {
                        selectedSkill = "Endurance"
                        refresh()
                    }
                }

                slot(6, 1) {
                    item = item(Material.BOW) {
                        name = "Dexterity | lvl ${playerData.dexterity}"
                    }
                    onclick = {
                        selectedSkill = "Dexterity"
                        refresh()
                    }
                }

                slot(4, 3) {
                    item = item(Material.ANVIL) {
                        name = "Upgrade $selectedSkill"
                        lore = listOf("${playerData.skillPoints} Unused Points")
                    }

                    onclick = {
                        if (playerData.skillPoints > 0 &&
                            (selectedSkill == "Strength" ||
                                    selectedSkill == "Endurance" ||
                                    selectedSkill == "Dexterity")
                        ) {


                            when (selectedSkill) {
                                "Strength" -> {
                                    playerData.strength++
                                }
                                "Endurance" -> {
                                    playerData.endurance++
                                }
                                "Dexterity" -> {
                                    playerData.dexterity++
                                }
                                else -> {
                                    refresh()
                                }
                            }

                            playerData.skillPoints--

                            transaction {
                                repo.update(playerData)
                            }
                        }
                        refresh()
                    }
                }
            }
        }
    }
}