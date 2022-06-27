package com.smellyalater.corefunctionality.gui.tradegui.classes

import masecla.villager.api.MainLoader
import org.bukkit.entity.Player
import java.lang.reflect.InvocationTargetException


class VillagerInventory {
    var trades: List<VillagerTrade> = ArrayList()
    var name = "Sample text"
    var forWho: Player? = null

    constructor(trades: List<VillagerTrade>, forWho: Player?) : super() {
        this.trades = trades
        this.forWho = forWho
    }

    constructor() {}

    fun open() {
        try {
            MainLoader.getAdapter().getConstructor(VillagerInventory::class.java).newInstance(this).openFor(forWho)
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}