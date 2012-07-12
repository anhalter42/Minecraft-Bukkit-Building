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
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author andre
 */
public class BuildingPlugin extends JavaPlugin {

    public static BuildingPlugin plugin;
    
    public WorldDBList<SimpleBuildingDB> DBs;

    public static void main(String[] args) {
    }

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new BuildingListener(), this);
        DBs = new WorldDBList<SimpleBuildingDB>(SimpleBuildingDB.class, this);
        Framework.plugin.registerSaver(DBs);
        
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
        BuildingDetector lDetector = Framework.plugin.getBuildingDetector();
        BuildingDescription lDesc;
        BuildingDescription.BlockDescription lBDesc;
        BuildingDescription.RelatedTo lRel;
        
        lDesc = lDetector.newDescription("Building.BuildingEntryDetector");
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
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        plugin = null;
    }

}
