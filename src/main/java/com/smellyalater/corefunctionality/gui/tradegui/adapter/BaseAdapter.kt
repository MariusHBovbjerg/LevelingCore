package com.smellyalater.corefunctionality.gui.tradegui.adapter

import com.smellyalater.corefunctionality.gui.tradegui.classes.VillagerInventory
import org.bukkit.entity.Player


abstract class BaseAdapter(var toAdapt: VillagerInventory) {
    abstract fun openFor(p: Player?)
}