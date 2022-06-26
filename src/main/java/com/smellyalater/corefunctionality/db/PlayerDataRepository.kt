package com.smellyalater.corefunctionality.db

import com.smellyalater.corefunctionality.db.PlayerTable.dexterity
import com.smellyalater.corefunctionality.db.PlayerTable.endurance
import com.smellyalater.corefunctionality.db.PlayerTable.experience
import com.smellyalater.corefunctionality.db.PlayerTable.level
import com.smellyalater.corefunctionality.db.PlayerTable.skillPoints
import com.smellyalater.corefunctionality.db.PlayerTable.strength
import com.smellyalater.corefunctionality.db.PlayerTable.uuid
import com.smellyalater.corefunctionality.model.PlayerData
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PlayerDataRepository : IPlayerDataRepository{
    override fun create(player: PlayerData): PlayerData {
        val newPlayerId = PlayerTable.insertAndGetId {
            it[uuid] = player.id
            it[experience] = player.experience
            it[level] = player.level
            it[strength] = player.strength
            it[dexterity] = player.dexterity
            it[endurance] = player.endurance
            it[skillPoints] = player.skillPoints
        }
        return PlayerData(newPlayerId.value)
    }

    override fun createBaseUser(playerId: UUID): PlayerData {
        val newPlayerId = PlayerTable.insertAndGetId {
            it[uuid] = playerId
            it[experience] = 0.0
            it[level] = 0
            it[strength] = 0
            it[dexterity] = 0
            it[endurance] = 0
            it[skillPoints] = 0
        }
        return PlayerData(newPlayerId.value)
    }

    override fun selectAll(): List<PlayerData> {
        return PlayerTable.selectAll().map {
            PlayerData(it[uuid], it[experience], it[level], it[strength], it[dexterity], it[endurance], it[skillPoints])
        }
    }

    override fun selectById(id: UUID): PlayerData? {
        return PlayerTable.select { uuid eq id }.map {
            PlayerData(it[uuid], it[experience], it[level], it[strength], it[dexterity], it[endurance], it[skillPoints])
        }.firstOrNull()
    }

    override fun delete(id: UUID) {
        PlayerTable.deleteWhere { uuid eq id }
    }

    override fun update(playerData: PlayerData) {
        PlayerTable.update({ uuid eq playerData.id }) {
            it[uuid] = playerData.id
            it[experience] = playerData.experience
            it[level] = playerData.level
            it[strength] = playerData.strength
            it[dexterity] = playerData.dexterity
            it[endurance] = playerData.endurance
            it[skillPoints] = playerData.skillPoints
        }
    }

    override fun getPlayerDataOrEnsureCreateBaseUser(playerId: UUID): PlayerData {
        return transaction { // transaction is needed to ensure that the player is created if it doesn't exist
            var player = selectById(playerId)
            if (player == null) {
                player = createBaseUser(playerId)
            }
            player
        }
    }
}