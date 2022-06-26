package com.smellyalater.corefunctionality.gui

import org.bukkit.Material

class Components {

    companion object{

        fun GUI.border(height: Int){
            fill(0,0,8,0){
                item = item(Material.GRAY_STAINED_GLASS_PANE){
                    name = ""
                }
            }
            fill(0,1,0,height-1){
                item = item(Material.GRAY_STAINED_GLASS_PANE){
                    name = ""
                }
            }
            fill(8,1,8,height-1){
                item = item(Material.GRAY_STAINED_GLASS_PANE){
                    name = ""
                }
            }
            fill(1,height-1,7,height-1){
                item = item(Material.GRAY_STAINED_GLASS_PANE){
                    name = ""
                }
            }
        }
    }
}