/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.building;

import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.DBRecordWorld;
import java.util.ArrayList;

/**
 *
 * @author andre
 */
public class Landmark extends DBRecordWorld {
    public enum Kind {
        Detect,
        Circle,
        Line,
        Area,
        Icon
    }
    
    public String name;
    public Kind kind = Kind.Detect;
    public String iconName = "default";
    public int color = -2;
    public int lineWidth = 2;
    public ArrayList<BlockPosition> positions = new ArrayList<BlockPosition>();
    
    @Override
    protected void toCSVInternal(ArrayList aCols) {
        super.toCSVInternal(aCols);
        aCols.add(name);
        String lStr = "";
        for(BlockPosition lPos : positions) {
            if (lStr.isEmpty()) {
                lStr = lPos.toCSV(",");
            } else {
                lStr += "|" + lPos.toCSV(",");
            }
        }
        aCols.add(lStr);
        aCols.add(kind);
        aCols.add(iconName);
        aCols.add(color);
        aCols.add(lineWidth);
    }

    @Override
    protected void fromCSVInternal(DBRecordCSVArray aCols) {
        super.fromCSVInternal(aCols);
        name = aCols.pop();
        String lStr = aCols.pop();
        positions.clear();
        String[] lParts = lStr.split("\\|");
        for(String lPart : lParts) {
            BlockPosition lPos = new BlockPosition();
            lPos.fromCSV(lPart, "\\,");
            positions.add(lPos);
        }
        kind = Kind.valueOf(aCols.pop());
        iconName = aCols.pop();
        color = aCols.popInt();
        lineWidth = aCols.popInt();
    }

    public ArrayList<BlockPosition> getPositions() {
        ArrayList<BlockPosition> lResult = new ArrayList<BlockPosition>();
        for(BlockPosition lPos : positions) {
            lResult.add(lPos);
        }
        return lResult;
    }
}
