package com.smellyalater.corefunctionality.util

import org.bukkit.Bukkit
import java.util.regex.Pattern

class HexColorFormat {

    companion object{

        private val pattern by lazy { Pattern.compile("#[a-fA-F\\d]{6}") }
        fun format(msg: String): String {
            var msg = msg
            if (Bukkit.getVersion().contains("1.18")) {
                var match = pattern.matcher(msg)
                while (match.find()) {
                    val color = msg.substring(match.start(), match.end())
                    color.replace("&", "")
                    msg = msg.replace(color, net.md_5.bungee.api.ChatColor.of(color).toString() + "")
                    match = pattern.matcher(msg)
                }
            }
            return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', msg)
        }
    }
}