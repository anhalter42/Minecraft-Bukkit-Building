/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.building;

import com.mahn42.framework.BuildingDescription;
import com.mahn42.framework.BuildingDetector;
import com.mahn42.framework.Framework;
import com.mahn42.framework.WorldDBList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

/**
 *
 * @author andre
 */
public class BuildingPlugin extends JavaPlugin {

    public static BuildingPlugin plugin;
    
    public WorldDBList<SimpleBuildingDB> SimpleDBs;
    public WorldDBList<SendReceiveDB> SendReceiveDBs;

    public static void main(String[] args) {
    }

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new BuildingListener(), this);
        SimpleDBs = new WorldDBList<SimpleBuildingDB>(SimpleBuildingDB.class, this);
        SendReceiveDBs = new WorldDBList<SendReceiveDB>(SendReceiveDB.class, "SendReceive", this);
        Framework.plugin.registerSaver(SimpleDBs);
        Framework.plugin.registerSaver(SendReceiveDBs);
        
        ItemStack lItemStack = new ItemStack(Material.SMOOTH_BRICK, 4, (short)0, (byte)3);
        ShapedRecipe lShapeRecipe = new ShapedRecipe(lItemStack);
        lShapeRecipe.shape("AA", "AA");
        lShapeRecipe.setIngredient('A', Material.SMOOTH_BRICK);
        getServer().addRecipe(lShapeRecipe);

        lItemStack = new ItemStack(Material.SMOOTH_BRICK, 4, (short)0, (byte)0);
        ShapedRecipe lChiseledStoneBrick = new ShapedRecipe(lItemStack);
        lChiseledStoneBrick.shape("AA", "AA");
        lChiseledStoneBrick.setIngredient('A', new MaterialData(Material.SMOOTH_BRICK, (byte)3));
        getServer().addRecipe(lChiseledStoneBrick);
        
        SimpleBuildingHandler lHandler = new SimpleBuildingHandler(this);
        SendReceiveHandler lSRHandler = new SendReceiveHandler(this);
        BuildingDetector lDetector = Framework.plugin.getBuildingDetector();
        BuildingDescription lDesc;
        BuildingDescription.BlockDescription lBDesc;
        BuildingDescription.RelatedTo lRel;
        
        lDesc = lDetector.newDescription("Building.BuildingEntryDetector");
        lDesc.typeName = "Building Enter/Leave Detector";
        lDesc.handler = lHandler;
        lBDesc = lDesc.newBlockDescription("base");
        lBDesc.materials.add(Material.SMOOTH_BRICK, (byte)3);
        lBDesc.detectSensible = true;
        lRel = lBDesc.newRelatedTo("lever", BuildingDescription.RelatedPosition.Nearby, 1);
        lRel = lBDesc.newRelatedTo("sign", BuildingDescription.RelatedPosition.Nearby, 1);
        lBDesc = lDesc.newBlockDescription("lever");
        lBDesc.materials.add(Material.LEVER);
        lBDesc = lDesc.newBlockDescription("sign");
        lBDesc.materials.add(Material.SIGN);
        lBDesc.materials.add(Material.SIGN_POST);
        lBDesc.materials.add(Material.WALL_SIGN);
        lDesc.activate();
   
        lDesc = lDetector.newDescription("Building.RedStoneReceiver");
        lDesc.typeName = "Building for receiving redstone signals";
        lDesc.handler = lSRHandler;
        lBDesc = lDesc.newBlockDescription("base");
        lBDesc.materials.add(Material.SMOOTH_BRICK, (byte)3);
        lBDesc.detectSensible = true;
        lRel = lBDesc.newRelatedTo(new Vector(0, 1, 0), "antenabase");
        lRel = lBDesc.newRelatedTo("lever", BuildingDescription.RelatedPosition.Nearby, 1);
        lRel = lBDesc.newRelatedTo("sign", BuildingDescription.RelatedPosition.Nearby, 1);
        lBDesc = lDesc.newBlockDescription("lever");
        lBDesc.materials.add(Material.LEVER);
        lBDesc = lDesc.newBlockDescription("sign");
        lBDesc.materials.add(Material.SIGN);
        lBDesc.materials.add(Material.SIGN_POST);
        lBDesc.materials.add(Material.WALL_SIGN);
        lBDesc = lDesc.newBlockDescription("antenabase");
        lBDesc.materials.add(Material.FENCE);
        lRel = lBDesc.newRelatedTo(new Vector(0, 10, 0), "antenatop");
        lRel.materials.add(Material.FENCE);
        lRel.minDistance = 1;
        lBDesc = lDesc.newBlockDescription("antenatop");
        lBDesc.materials.add(Material.FENCE);
        lDesc.activate();
   
        lDesc = lDetector.newDescription("Building.RedStoneSender");
        lDesc.typeName = "Building for sending redstone signals";
        lDesc.handler = lSRHandler;
        lBDesc = lDesc.newBlockDescription("base");
        lBDesc.materials.add(Material.SMOOTH_BRICK, (byte)3);
        lBDesc.detectSensible = true;
        lBDesc.redstoneSensible = true;
        lRel = lBDesc.newRelatedTo(new Vector(0, 1, 0), "antenabase");
        lRel = lBDesc.newRelatedTo("sign", BuildingDescription.RelatedPosition.Nearby, 1);
        lBDesc = lDesc.newBlockDescription("sign");
        lBDesc.materials.add(Material.SIGN);
        lBDesc.materials.add(Material.SIGN_POST);
        lBDesc.materials.add(Material.WALL_SIGN);
        lBDesc = lDesc.newBlockDescription("antenabase");
        lBDesc.materials.add(Material.FENCE);
        lRel = lBDesc.newRelatedTo(new Vector(0, 10, 0), "antenatop");
        lRel.materials.add(Material.FENCE);
        lRel.minDistance = 1;
        lBDesc = lDesc.newBlockDescription("antenatop");
        lBDesc.materials.add(Material.FENCE);
        lDesc.activate();
   
        lDesc = lDetector.newDescription("Building.Pyramid.Sandstone");
        lDesc.handler = lHandler;
        lDesc.typeName = "Pyramid";
        lBDesc = lDesc.newBlockDescription("top");
        lBDesc.materials.add(Material.SANDSTONE);
        lBDesc.detectSensible = true;
        lRel = lBDesc.newRelatedTo(new Vector( 80,-80, 80), "ground1");
        lRel.materials.add(Material.SANDSTONE);
        lRel.minDistance = 1;
        lRel = lBDesc.newRelatedTo(new Vector( 80,-80,-80), "ground2");
        lRel.materials.add(Material.SANDSTONE);
        lRel.minDistance = 1;
        lRel = lBDesc.newRelatedTo(new Vector(-80,-80, 80), "ground3");
        lRel.materials.add(Material.SANDSTONE);
        lRel.minDistance = 1;
        lRel = lBDesc.newRelatedTo(new Vector(-80,-80,-80), "ground4");
        lRel.materials.add(Material.SANDSTONE);
        lRel.minDistance = 1;
        lBDesc = lDesc.newBlockDescription("ground1");
        lBDesc.materials.add(Material.SANDSTONE);
        lBDesc = lDesc.newBlockDescription("ground2");
        lBDesc.materials.add(Material.SANDSTONE);
        lBDesc = lDesc.newBlockDescription("ground3");
        lBDesc.materials.add(Material.SANDSTONE);
        lBDesc = lDesc.newBlockDescription("ground4");
        lBDesc.materials.add(Material.SANDSTONE);
        lDesc.activate();
        
        lDesc = lDetector.newDescription("Building.BoatRailStation");
        lDesc.handler = lHandler;
        lDesc.typeName = "BoatRailStation";
        lBDesc = lDesc.newBlockDescription("railblock");
        lBDesc.materials.add(Material.COBBLESTONE_STAIRS);
        lBDesc.materials.add(Material.SMOOTH_STAIRS);
        lBDesc.materials.add(Material.WOOD_STAIRS);
        lBDesc.materials.add(Material.BRICK_STAIRS);
        lBDesc.detectSensible = true;
        lRel = lBDesc.newRelatedTo(new Vector(0, 0, 1), "rail_water");
        lRel = lBDesc.newRelatedTo(new Vector(-3, 0, 0), "wall_left_ground");
        lRel = lBDesc.newRelatedTo(new Vector(3, 0, 0), "wall_right_ground");
        lBDesc = lDesc.newBlockDescription("rail_water");
        lBDesc.materials.add(Material.RAILS);
        lRel = lBDesc.newRelatedTo(new Vector(0, 0, 3), "rail_land");
        lRel.materials.add(Material.RAILS);
        lBDesc = lDesc.newBlockDescription("rail_land");
        lBDesc.materials.add(Material.RAILS);
        lBDesc = lDesc.newBlockDescription("wall_left_ground");
        lBDesc.materials.add(Material.BRICK);
        lRel = lBDesc.newRelatedTo(new Vector(0, 3, 0), "wall_left_top");
        lRel.materials.add(Material.BRICK);
        lRel = lBDesc.newRelatedTo(new Vector(0,-1,-1), "runway_left");
        lBDesc = lDesc.newBlockDescription("wall_right_ground");
        lBDesc.materials.add(Material.BRICK);
        lRel = lBDesc.newRelatedTo(new Vector(0, 3, 0), "wall_right_top");
        lRel.materials.add(Material.BRICK);
        lRel = lBDesc.newRelatedTo(new Vector(0,-1,-1), "runway_right");
        lBDesc = lDesc.newBlockDescription("wall_left_top");
        lBDesc.materials.add(Material.BRICK);
        lRel = lBDesc.newRelatedTo(new Vector(0, 0, 3), "wall_left_top_land");
        lRel.materials.add(Material.BRICK);
        lBDesc = lDesc.newBlockDescription("wall_right_top");
        lBDesc.materials.add(Material.BRICK);
        lRel = lBDesc.newRelatedTo(new Vector(0, 0, 3), "wall_right_top_land");
        lRel.materials.add(Material.BRICK);
        lBDesc = lDesc.newBlockDescription("wall_left_top_land");
        lBDesc.materials.add(Material.BRICK);
        lBDesc = lDesc.newBlockDescription("wall_right_top_land");
        lBDesc.materials.add(Material.BRICK);
        lRel = lBDesc.newRelatedTo(new Vector(0, 1, 0), "wall_right_roof");
        lBDesc = lDesc.newBlockDescription("wall_right_roof");
        lBDesc.materials.add(Material.WOOD_STEP);
        lBDesc = lDesc.newBlockDescription("runway_left");
        lBDesc.materials.add(Material.WOOD);
        lRel = lBDesc.newRelatedTo(new Vector(0, 0,-4), "runway_left_see");
        lRel.materials.add(Material.WOOD);
        lBDesc = lDesc.newBlockDescription("runway_right");
        lBDesc.materials.add(Material.WOOD);
        lRel = lBDesc.newRelatedTo(new Vector(0, 0,-4), "runway_right_see");
        lRel.materials.add(Material.WOOD);
        lBDesc = lDesc.newBlockDescription("runway_left_see");
        lBDesc.materials.add(Material.WOOD);
        lRel = lBDesc.newRelatedTo(new Vector(0, 1, 0), "runway_left_bar");
        lBDesc = lDesc.newBlockDescription("runway_right_see");
        lBDesc.materials.add(Material.WOOD);
        lRel = lBDesc.newRelatedTo(new Vector(0, 1, 0), "runway_right_bar");
        lBDesc = lDesc.newBlockDescription("runway_left_bar");
        lBDesc.materials.add(Material.FENCE);
        lRel = lBDesc.newRelatedTo(new Vector(0, 3, 0), "runway_left_bar_top");
        lRel.materials.add(Material.FENCE);
        lBDesc = lDesc.newBlockDescription("runway_right_bar");
        lBDesc.materials.add(Material.FENCE);
        lRel = lBDesc.newRelatedTo(new Vector(0, 3, 0), "runway_right_bar_top");
        lRel.materials.add(Material.FENCE);
        lBDesc = lDesc.newBlockDescription("runway_left_bar_top");
        lBDesc.materials.add(Material.FENCE);
        lRel = lBDesc.newRelatedTo(new Vector(0, 1, 0), "runway_left_bar_roof");
        lBDesc = lDesc.newBlockDescription("runway_left_bar_roof");
        lBDesc.materials.add(Material.WOOD_STEP);
        lRel = lBDesc.newRelatedTo(new Vector(6, 0, 8), "wall_right_roof", BuildingDescription.RelatedPosition.AreaYX);
        lRel.materials.add(Material.WOOD_STEP);
        lBDesc = lDesc.newBlockDescription("runway_right_bar_top");
        lBDesc.materials.add(Material.FENCE);
        lDesc.createAndActivateXZ(true);
        
        /*lDesc = lDetector.newDescription("Building.Bedroom");
        BuildingDescription.BlockMaterialArray lMats = lDesc.newBlockMaterialArray();
        lMats.add(Material.SMOOTH_BRICK);
        lMats.add(Material.BRICK);
        lMats.add(Material.WOOD);
        lMats.add(Material.WOOL);
        lDesc.handler = lHandler;
        lDesc.typeName = "Bedroom";
        lBDesc = lDesc.newBlockDescription("ground_e1");
        lBDesc.materials.add(lMats);
        lBDesc.detectSensible = true;
        lRel = lBDesc.newRelatedTo(new Vector(20,0,20), "ground_e3", BuildingDescription.RelatedPosition.AreaXZ);
        lRel.materials.add(lMats);
        lRel = lBDesc.newRelatedTo(new Vector(20, 0, 0), "ground_e2");
        lRel = lBDesc.newRelatedTo(new Vector(0, 0, 20), "ground_e4");
        lBDesc = lDesc.newBlockDescription("ground_e2");
        lBDesc.materials.add(lMats);
        lBDesc = lDesc.newBlockDescription("ground_e3");
        lBDesc.materials.add(lMats);
        lBDesc = lDesc.newBlockDescription("ground_e4");
        lBDesc.materials.add(lMats);
        */
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        plugin = null;
    }

}
