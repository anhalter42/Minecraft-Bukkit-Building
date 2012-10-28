/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.building;

import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.Framework;
import java.util.ArrayList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;
import org.dynmap.markers.CircleMarker;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.PolyLineMarker;

/**
 *
 * @author andre
 */
public class DynMapLandmarkRenderer implements Runnable {

    protected boolean fInRun = false;
    protected Plugin fDynmap;
    
    @Override
    public void run() {
        if (!fInRun) {
            fInRun = true;
            try {
                PluginManager lPM = Framework.plugin.getServer().getPluginManager();
                fDynmap = lPM.getPlugin("dynmap");
                if(fDynmap != null && fDynmap.isEnabled()) {
                    execute();
                }
            } finally {
                fInRun = false;
            }
        }
    }

    public MarkerAPI getMarkerAPI() {
        if (fDynmap == null) {
            return null;
        }
        DynmapAPI lDynmapAPI = (DynmapAPI)fDynmap; /* Get API */
        return lDynmapAPI.getMarkerAPI();
    }
    
    private void execute() {
        MarkerAPI lMarkerAPI = getMarkerAPI();
        MarkerSet lMarkerSet = lMarkerAPI.getMarkerSet("building.landmarks");
        if (lMarkerSet != null) {
            lMarkerSet.deleteMarkerSet();
        }
        lMarkerSet = lMarkerAPI.createMarkerSet("building.landmarks", "Landmarks", null, false);
        if (lMarkerSet == null) {
            return;
        }
        ArrayList<LandmarkDB> lDBs = BuildingPlugin.plugin.LandmarkDBs.getDBs();
        for(LandmarkDB lDB : lDBs) {
            for(Landmark lMark : lDB) {
                ArrayList<BlockPosition> lPoss = lMark.getPositions();
                Landmark.Kind lKind = lMark.kind;
                if (lKind == Landmark.Kind.Detect) {
                    if (lMark.positions.size() == 1) {
                        lKind = Landmark.Kind.Circle;
                    } else if (lMark.positions.size() == 2) {
                        lKind = Landmark.Kind.Line;
                    } else {
                        lKind = Landmark.Kind.Area;
                    }
                }
                double x[] = new double[lMark.positions.size()];
                double y[] = new double[lMark.positions.size()];
                double z[] = new double[lMark.positions.size()];
                int lIndex = 0;
                for(BlockPosition lPos : lMark.positions) {
                    x[lIndex] = lPos.x;
                    y[lIndex] = lPos.y;
                    z[lIndex] = lPos.z;
                    lIndex++;
                }
                switch(lKind) {
                    case Icon:
                        MarkerIcon lIcon = lMarkerAPI.getMarkerIcon(lMark.iconName);
                        if (lIcon == null) {
                            lIcon = lMarkerAPI.getMarkerIcon("default");
                        }
                        Marker lIconMark = lMarkerSet.createMarker(lMark.key, lMark.name, lDB.world.getName(), x[0], y[0], z[0], lIcon, false);
                        lIconMark.setDescription(lMark.name);
                        break;
                    case Circle:
                        CircleMarker lCircle = lMarkerSet.createCircleMarker(lMark.key, lMark.name, true, lDB.world.getName(), lPoss.get(0).x, lPoss.get(0).y, lPoss.get(0).z, 5, 5, false);
                        if (lCircle != null) {
                            lCircle.setDescription(lMark.name);
                            lCircle.setFillStyle(0.0, 0);
                            lCircle.setLabel(lMark.name);
                            lCircle.setLineStyle(1, 1.0, 0x80A000); // TODO Colors
                        }
                        break;
                    case Area:
                        AreaMarker lArea = lMarkerSet.createAreaMarker(lMark.key, lMark.name, false, lDB.world.getName(), x, z, false);
                        if (lArea != null) {
                            lArea.setDescription(lMark.name);
                            lArea.setFillStyle(0.0, 0);
                            lArea.setLineStyle(1, 1.0, 0x80A00);
                        }
                        break;
                    case Line:
                        PolyLineMarker lLine = lMarkerSet.createPolyLineMarker(lMark.key, lMark.name, false, lDB.world.getName(), x, y, z, false);
                        if (lLine != null) {
                            lLine.setDescription(lMark.name);
                            lLine.setLineStyle(1, 1.0, 0x80A00);
                        }
                        break;
                }
                //lMarkerAPI.getMarkerIcon(null)
                //lMarkerSet.createMarker(null, null, null, d, d1, d2, null, fInRun)
                /*
                PolyLineMarker lRect = lMarkerSet.createPolyLineMarker(lBuilding.key, lBuilding.getName(), true, lDB.world.getName(), lXs, lYs, lZs, false);
                if (lRect != null) {
                    lRect.setLineStyle(3, 0.75, 0xF0A0A0);
                }
                */
            }
        }
    }    
}
