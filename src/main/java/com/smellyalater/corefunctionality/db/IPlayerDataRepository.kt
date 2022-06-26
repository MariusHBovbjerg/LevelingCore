package com.smellyalater.corefunctionality.db

import com.smellyalater.corefunctionality.model.PlayerData
import java.util.*

interface IPlayerDataRepository {
    fun create(player: PlayerData): PlayerData
    fun createBaseUser(playerId: UUID): PlayerData
    fun selectAll(): List<PlayerData>
    fun selectById(id: UUID): PlayerData?
    fun delete(id: UUID)
    fun update(playerData: PlayerData)
}