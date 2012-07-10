/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.building;

import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author andre
 */
public class BuildingPlugin extends JavaPlugin {

    public static void main(String[] args) {
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
    }

}
