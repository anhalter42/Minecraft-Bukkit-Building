/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.building;

import com.mahn42.framework.BuildingEvent;
import java.util.logging.Logger;
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
    }    
}
