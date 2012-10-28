/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.building;

import com.mahn42.framework.Building;
import com.mahn42.framework.BuildingDB;
import com.mahn42.framework.BuildingHandlerBase;
import java.util.logging.Logger;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

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
    public JavaPlugin getPlugin() {
        return plugin;
    }
    
    @Override
    public Building insert(Building aBuilding) {
        SimpleBuildingDB lDB = (SimpleBuildingDB) getDB(aBuilding.world);
        SimpleBuilding lSimple = new SimpleBuilding();
        lSimple.cloneFrom(aBuilding);
        lDB.addRecord(lSimple);
        Logger.getLogger("xxx").info("A0");
        if (lSimple.description.name.equals("Building.Landmark")) {
        Logger.getLogger("xxx").info("A");
            plugin.LandmarkDBs.getDB(aBuilding.world).landmarkBuildingInserted(lSimple);
        }
        return lSimple;
    }

    @Override
    public boolean remove(Building aBuilding) {
        Logger.getLogger("xxx").info("B0");
        if (aBuilding.description.name.equals("Building.Landmark")) {
        Logger.getLogger("xxx").info("B");
            plugin.LandmarkDBs.getDB(aBuilding.world).landmarkBuildingRemoved(aBuilding);
        }
        return super.remove(aBuilding);
    }
    
    @Override
    public BuildingDB getDB(World aWorld) {
        return plugin.SimpleDBs.getDB(aWorld);
    }
}
