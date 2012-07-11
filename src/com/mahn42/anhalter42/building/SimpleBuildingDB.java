/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.building;

import com.mahn42.framework.BuildingDB;
import java.io.File;
import org.bukkit.World;

/**
 *
 * @author andre
 */
public class SimpleBuildingDB extends BuildingDB<SimpleBuilding> {
    
    public SimpleBuildingDB() {
        super(SimpleBuilding.class);
    }

    public SimpleBuildingDB(World aWorld, File aFile) {
        super(SimpleBuilding.class, aWorld, aFile);
    }
    
}
