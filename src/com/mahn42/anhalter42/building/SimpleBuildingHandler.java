/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.building;

import com.mahn42.framework.Building;
import com.mahn42.framework.BuildingHandlerBase;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *
 * @author andre
 */
public class SimpleBuildingHandler extends BuildingHandlerBase {

    public BuildingPlugin plugin;
    
    public SimpleBuildingHandler(BuildingPlugin aPlugin) {
        plugin = aPlugin;
    }
            
    @Override
    public boolean breakBlock(BlockBreakEvent aEvent, Building aBuilding) {
        World lWorld = aEvent.getBlock().getWorld();
        SimpleBuilding lGate = (SimpleBuilding)aBuilding;
        SimpleBuildingDB lDB = plugin.DBs.getDB(lWorld);
        lDB.remove(lGate);
        return true;
    }

    @Override
    public boolean playerInteract(PlayerInteractEvent aEvent, Building aBuilding) {
        Player lPlayer = aEvent.getPlayer();
        World lWorld = lPlayer.getWorld();
        boolean lFound = false;
        SimpleBuildingDB lDB = plugin.DBs.getDB(lWorld);
        if (lDB.getBuildings(aBuilding.edge1).isEmpty()
                && lDB.getBuildings(aBuilding.edge2).isEmpty()) {
            SimpleBuilding lSimple = new SimpleBuilding();
            lSimple.cloneFrom(aBuilding);
            lDB.addRecord(lSimple);
            lPlayer.sendMessage("Building " + lSimple.getName() + " found.");
            lFound = true;
        }
        return lFound;
    }
}
