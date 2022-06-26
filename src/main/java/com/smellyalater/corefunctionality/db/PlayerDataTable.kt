package com.smellyalater.corefunctionality.db

import org.jetbrains.exposed.dao.id.UUIDTable

object PlayerTable : UUIDTable(name = "players") {
    var uuid = uuid("UUID")
    var experience = double("experience")
    var level = integer("level")
    var strength = integer("strength")
    var dexterity = integer("dexterity")
    var endurance = integer("endurance")
    var skillPoints = integer("skill_points")
}