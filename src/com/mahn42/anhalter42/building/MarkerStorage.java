/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.building;

import com.mahn42.framework.BlockRect;
import com.mahn42.framework.IMarker;
import com.mahn42.framework.IMarkerStorage;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.World;

/**
 *
 * @author andre
 */
public class MarkerStorage implements IMarkerStorage{

    @Override
    public List<IMarker> findMarkers(World aWorld, String aName) {
        ArrayList<IMarker> lResult = new ArrayList<IMarker>();
        LandmarkDB lDB = BuildingPlugin.plugin.LandmarkDBs.getDB(aWorld);
        Landmark lLandmark = lDB.getLandmark(aName);
        if (lLandmark != null) {
            lResult.add(new MarkerStorageMarker(lLandmark));
        }
        return lResult;
    }

    @Override
    public List<IMarker> findMarkers(World aWorld, BlockRect aArea) {
        ArrayList<IMarker> lResult = new ArrayList<IMarker>();
        LandmarkDB lDB = BuildingPlugin.plugin.LandmarkDBs.getDB(aWorld);
        ArrayList<Landmark> lLandmarks = lDB.getLandmarks(aArea);
        for(Landmark lLandmark : lLandmarks) {
            lResult.add(new MarkerStorageMarker(lLandmark));
        }
        return lResult;
    }
    
}
