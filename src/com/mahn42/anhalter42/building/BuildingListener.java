/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.building;

import com.mahn42.framework.Building;
import com.mahn42.framework.BuildingEvent;
import java.util.logging.Logger;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 *
 * @author andre
 */
public class BuildingListener implements Listener {
    
    @EventHandler
    public void buildingChanged(BuildingEvent aEvent) {
        Logger.getLogger("BuildingListener").info("building " + aEvent.getBuilding().getName() + " has " + aEvent.getAction());
        Building lEventBuilding = aEvent.getBuilding();
        if (lEventBuilding.name != null && !lEventBuilding.name.isEmpty()) {
            if (aEvent.getAction() == BuildingEvent.BuildingAction.PlayerEnter) {
                SimpleBuildingDB lDB = BuildingPlugin.plugin.DBs.getDB(lEventBuilding.world);
                for(SimpleBuilding lBuilding : lDB) {
                    if (lBuilding.description.name.equals("Building.BuildingEntryDetector")) {
                        Block lBlock = lBuilding.getBlock("sign").position.getBlock(lDB.world);
                        Sign lSign = (Sign)lBlock.getState();
                        String lName = lSign.getLine(0);
                        if (lName.equals(aEvent.getBuilding().name)) {
                            lBlock = lBuilding.getBlock("lever").position.getBlock(lDB.world);
                            lBlock.setData((byte)(lBlock.getData() ^ 8), true);
                            Logger.getLogger("BuildingListener").info("sig found");
                        }
                    }
                }
            }
            if (aEvent.getAction() == BuildingEvent.BuildingAction.PlayerLeave) {
                SimpleBuildingDB lDB = BuildingPlugin.plugin.DBs.getDB(lEventBuilding.world);
                for(SimpleBuilding lBuilding : lDB) {
                    if (lBuilding.description.name.equals("Building.BuildingLeaveDetector")) {
                        Block lBlock = lBuilding.getBlock("sign").position.getBlock(lDB.world);
                        Sign lSign = (Sign)lBlock.getState();
                        String lName = lSign.getLine(0);
                        if (lName.equals(aEvent.getBuilding().name)) {
                            lBlock = lBuilding.getBlock("lever").position.getBlock(lDB.world);
                            lBlock.setData((byte)(lBlock.getData() ^ 8), true);
                            Logger.getLogger("BuildingListener").info("sig found");
                        }
                    }
                }
            }
        }
    }
}
