package com.smellyalater.corefunctionality.gui.tradegui.classes

import com.smellyalater.corefunctionality.gui.tradegui.adapter.MerchantAdapter_v1_18_2_R0
import org.bukkit.entity.Player
import java.lang.reflect.InvocationTargetException


class VillagerInventory (var trades: ArrayList<VillagerTrade> = ArrayList(), var forWho: Player? = null, var name: String = "Sample Text") {

    fun open() {
        try {
            MerchantAdapter_v1_18_2_R0(this).openFor(forWho)
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