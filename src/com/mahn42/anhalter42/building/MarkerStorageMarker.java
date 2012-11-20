/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.building;

import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.IMarker;

/**
 *
 * @author andre
 */
public class MarkerStorageMarker implements IMarker {
    
    public Landmark landmark;

    public MarkerStorageMarker(Landmark aLandmark) {
        landmark = aLandmark;
    }

    @Override
    public String getName() {
        return landmark.name;
    }

    @Override
    public BlockPosition getPosition() {
        return landmark.getPositions().get(0); // first position
    }
}
