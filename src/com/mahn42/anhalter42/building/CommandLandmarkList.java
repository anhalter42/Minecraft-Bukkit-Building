/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.building;

import java.util.ArrayList;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author andre
 */
public class CommandLandmarkList implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        World lWorld = null;
        if (aCommandSender instanceof Player) {
            lWorld = ((Player)aCommandSender).getWorld();
        }
        if (aStrings.length > 0) {
            lWorld = BuildingPlugin.plugin.getServer().getWorld(aStrings[0]);
            if (lWorld == null) {
                aCommandSender.sendMessage("World " + aStrings[0] + " unknown!");
                return true;
            }
        }
        ArrayList<LandmarkDB> lDBs;
        if (lWorld == null) {
            lDBs = BuildingPlugin.plugin.LandmarkDBs.getDBs();
        } else {
            lDBs = new ArrayList<LandmarkDB>();
            lDBs.add(BuildingPlugin.plugin.LandmarkDBs.getDB(lWorld));
        }
        if (lDBs.isEmpty()) {
            aCommandSender.sendMessage("no landmarks found");
        } else {
            for(LandmarkDB lDB : lDBs) {
                aCommandSender.sendMessage("World " + lDB.world.getName() + " landmarks:");
                if (lDB.size() == 0) {
                    aCommandSender.sendMessage("no landmarks found");
                } else {
                    for(Landmark lMark : lDB) {
                        aCommandSender.sendMessage(lMark.name + " with " + lMark.positions.size() + " positions" + " first at " + lMark.positions.get(0));
                    }
                }
            }
        }
        return true;
    }
    
}
