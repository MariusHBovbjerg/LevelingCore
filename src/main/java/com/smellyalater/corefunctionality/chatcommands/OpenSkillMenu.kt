package com.smellyalater.corefunctionality.chatcommands

import com.smellyalater.corefunctionality.db.PlayerDataRepository
import com.smellyalater.corefunctionality.db.PlayerTable
import com.smellyalater.corefunctionality.gui.*
import com.smellyalater.corefunctionality.gui.Components.Companion.border
import com.smellyalater.corefunctionality.model.PlayerData
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class OpenSkillMenu(private val plugin: JavaPlugin, private val playerDataRepository: PlayerDataRepository) :
    Listener {

    init {
        plugin.getCommand("skills").apply {
            plugin.logger.info("Registering command: ${javaClass.simpleName}")
        }?.setExecutor { sender, _, _, _ ->
            sender.openSkillMenu(playerDataRepository, plugin)
        }
    }

    private fun CommandSender.openSkillMenu(repo: PlayerDataRepository, plugin: JavaPlugin): Boolean {
        val player = this as? Player ?: return true
        player.open(skillGUI(player, repo, plugin))
        return true
    }

    private fun skillGUI(player: Player, repo: PlayerDataRepository, plugin: JavaPlugin): GUI {

        var selectedSkill = ""

        var playerData: PlayerData? = transaction { repo.selectById(player.uniqueId) }

        if (playerData == null) {
            playerData = repo.createBaseUser(player.uniqueId)
        }

        return gui("Skill Menu", 5, plugin) {
            border(5)

            slot(2, 1) {
                item = item(Material.IRON_SWORD) {
                    name = "Strength | lvl ${playerData.strength}"
                }
                onclick = { p ->
                    p.sendMessage(selectedSkill)
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
                        selectedSkill == "Dexterity")) {


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
                            PlayerTable.update({ PlayerTable.uuid eq playerData.id }) {
                                it[skillPoints] = playerData.skillPoints
                                it[strength] = playerData.strength
                                it[endurance] = playerData.endurance
                                it[dexterity] = playerData.dexterity
                            }
                        }
                    }
                    refresh()
                }
            }
        }
    }
}