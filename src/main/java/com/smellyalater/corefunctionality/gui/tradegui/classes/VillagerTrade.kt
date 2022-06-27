package com.smellyalater.corefunctionality.gui.tradegui.classes

import org.bukkit.inventory.ItemStack


class VillagerTrade(var itemOne: ItemStack, var itemTwo: ItemStack?, var result: ItemStack, var maxUses: Int) {

    constructor(itemOne: ItemStack, result: ItemStack, maxUses: Int) : this(itemOne, null, result, maxUses) {}

    fun requiresTwoItems(): Boolean {
        return itemTwo != null
    }
}