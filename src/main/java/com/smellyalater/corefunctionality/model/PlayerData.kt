package com.smellyalater.corefunctionality.model

import java.util.*

class PlayerData {
    var id: UUID
    var experience = 0.0
    var level = 1
    var strength = 0
    var dexterity = 0
    var endurance = 0
    constructor(id: UUID) {
        this.id = id;
    }
    constructor(id: UUID, experience: Double, level: Int, strength: Int, dexterity: Int, endurance: Int) {
        this.id = id
        this.experience = experience
        this.level = level
        this.strength = strength
        this.dexterity = dexterity
        this.endurance = endurance
    }
}