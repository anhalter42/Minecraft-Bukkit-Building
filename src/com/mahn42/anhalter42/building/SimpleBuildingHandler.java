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
import org.bukkit.block.Sign;
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
        if (lSimple.description.name.equals("Building.Landmark")) {
            plugin.LandmarkDBs.getDB(aBuilding.world).landmarkBuildingInserted(lSimple);
        }
        if (lSimple.description.name.startsWith("Building.Portal")) {
            Sign lSign = (Sign)lSimple.getBlock("sign").position.getBlock(lSimple.world).getState();
            String[] lLines = lSign.getLines();
            lSimple.name = lLines[0] + " " + lLines[1];
        }
        return lSimple;
    }

    @Override
    public boolean remove(Building aBuilding) {
        if (aBuilding.description.name.equals("Building.Landmark")) {
            plugin.LandmarkDBs.getDB(aBuilding.world).landmarkBuildingRemoved(aBuilding);
        }
        return super.remove(aBuilding);
    }
    
    @Override
    public BuildingDB getDB(World aWorld) {
        return plugin.SimpleDBs.getDB(aWorld);
    }
}
