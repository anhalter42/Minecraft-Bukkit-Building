/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.building;

import com.mahn42.framework.Building;
import com.mahn42.framework.BuildingDB;
import com.mahn42.framework.BuildingHandlerBase;
import org.bukkit.World;

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
    public Building insert(Building aBuilding) {
        SimpleBuildingDB lDB = (SimpleBuildingDB) getDB(aBuilding.world);
        SimpleBuilding lSimple = new SimpleBuilding();
        lSimple.cloneFrom(aBuilding);
        lDB.addRecord(lSimple);
        return lSimple;
    }

    @Override
    public BuildingDB getDB(World aWorld) {
        return plugin.SimpleDBs.getDB(aWorld);
    }
}
