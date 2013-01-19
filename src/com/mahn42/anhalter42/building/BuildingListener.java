/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.building;

import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.BlockRect;
import com.mahn42.framework.Building;
import com.mahn42.framework.BuildingEvent;
import com.mahn42.framework.Framework;
import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author andre
 */
public class BuildingListener implements Listener {

    protected HashMap<String, Long> fLastPortalAccess = new HashMap<String, Long>();

    @EventHandler
    public void buildingChanged(BuildingEvent aEvent) {
        //Logger.getLogger("BuildingListener").info("building " + aEvent.getBuilding().getName() + " has " + aEvent.getAction());
        Building lEventBuilding = aEvent.getBuilding();
        BuildingEvent.BuildingAction lAction = aEvent.getAction();
        if (lEventBuilding.name != null && !lEventBuilding.name.isEmpty()) {
            if (lAction == BuildingEvent.BuildingAction.PlayerEnter
                    || lAction == BuildingEvent.BuildingAction.PlayerLeave) {
                SimpleBuildingDB lDB = BuildingPlugin.plugin.SimpleDBs.getDB(lEventBuilding.world);
                for (SimpleBuilding lBuilding : lDB) {
                    if (lBuilding.description.name.equals("Building.BuildingEntryDetector")) {
                        Block lBlock = lBuilding.getBlock("sign").position.getBlock(lDB.world);
                        Sign lSign = (Sign) lBlock.getState();
                        String[] lLines = lSign.getLines();
                        String lName = lLines[0] + lLines[1];
                        String lOption = lSign.getLine(1);
                        if (lName.equals(aEvent.getBuilding().name)
                                && (lOption.isEmpty()
                                || (lOption.equalsIgnoreCase("enter") && lAction == BuildingEvent.BuildingAction.PlayerEnter)
                                || (lOption.equalsIgnoreCase("leave") && lAction == BuildingEvent.BuildingAction.PlayerLeave))) {
                            lBlock = lBuilding.getBlock("lever").position.getBlock(lDB.world);
                            lBlock.setData((byte) (lBlock.getData() ^ 8), true);
                            //Logger.getLogger("BuildingListener").info("sig found");
                        }
                    }
                }
            }
        }
        if (lAction == BuildingEvent.BuildingAction.PlayerEnter) {
            // Boat Rail Station Umsetzer
            if (lEventBuilding.description.name.startsWith("Building.BoatRailStation")) {
                if (aEvent.getPlayer().isInsideVehicle()) {
                    Entity lVehicle = aEvent.getPlayer().getVehicle();
                    if (lVehicle instanceof Minecart) {
                        ItemStack lItemInHand = aEvent.getPlayer().getItemInHand();
                        if (lItemInHand != null && lItemInHand.getType().equals(Material.BOAT)) {
                            BlockPosition lPos = lEventBuilding.getBlock("water2").position.clone();
                            lPos.add(0, 1, 0);
                            lVehicle.eject();
                            lVehicle.remove();
                            Entity lSpawnEntity = aEvent.getPlayer().getWorld().spawnEntity(lPos.getLocation(aEvent.getPlayer().getWorld()), EntityType.BOAT);
                            lSpawnEntity.setPassenger(aEvent.getPlayer());
                            aEvent.getPlayer().setItemInHand(new ItemStack(Material.MINECART));
                        }
                    } else if (lVehicle instanceof Boat) {
                        ItemStack lItemInHand = aEvent.getPlayer().getItemInHand();
                        if (lItemInHand != null && lItemInHand.getType().equals(Material.MINECART)) {
                            BlockPosition lPos = lEventBuilding.getBlock("rails").position.clone();
                            lVehicle.eject();
                            lVehicle.remove();
                            Entity lSpawnEntity = aEvent.getPlayer().getWorld().spawnEntity(lPos.getLocation(aEvent.getPlayer().getWorld()), EntityType.MINECART);
                            lSpawnEntity.setPassenger(aEvent.getPlayer());
                            aEvent.getPlayer().setItemInHand(new ItemStack(Material.BOAT));
                        }
                    }
                }
            } else if (lEventBuilding.description.name.startsWith("Building.Portal")) {
                Long lLast = fLastPortalAccess.get(aEvent.getPlayer().getName());
                if (lLast == null || lLast < (Framework.plugin.getSyncCallCount() - 60)) {
                    fLastPortalAccess.put(aEvent.getPlayer().getName(), Framework.plugin.getSyncCallCount());
                    BlockState lState = lEventBuilding.getBlock("sign").position.getBlock(lEventBuilding.world).getState();
                    if (lState instanceof Sign) {
                        String[] lLines = ((Sign) lState).getLines();
                        String lMark = lLines[2];
                        String lWorldName = lLines[3];
                        if (lWorldName != null && !lWorldName.isEmpty()) {
                            World lWorld = Framework.plugin.getServer().getWorld(lWorldName);
                            if (lWorld != null) {
                                aEvent.getPlayer().getWorld().playEffect(aEvent.getPlayer().getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
                                aEvent.getPlayer().getWorld().playEffect(aEvent.getPlayer().getLocation(), Effect.BLAZE_SHOOT, 10);
                                Framework.plugin.teleportPlayerToWorld(aEvent.getPlayer(), lWorld, lMark);
                            } else {
                                aEvent.getPlayer().sendMessage("unkown world '" + lWorldName + "'!");
                            }
                        } else {
                            aEvent.getPlayer().sendMessage("unkown world '" + lWorldName + "'!");
                        }
                    }
                } else {
                    Logger.getLogger("xx").info("portal not entered! " + lLast + " " + Framework.plugin.getSyncCallCount());
                }
            }
        }
    }

    @EventHandler
    public void creatorSpawn(CreatureSpawnEvent aEvent) {
        if (aEvent.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM
                && aEvent.getSpawnReason() != CreatureSpawnEvent.SpawnReason.EGG
                && aEvent.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER
                && aEvent.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
            Entity lEntity = aEvent.getEntity();
            SimpleBuildingDB lDB = BuildingPlugin.plugin.SimpleDBs.getDB(lEntity.getWorld());
            for (SimpleBuilding lBuilding : lDB) {
                if (lBuilding.description.name.startsWith("Building.Monument.")) {
                    EntityType lType = lEntity.getType();
                    String btype = lBuilding.description.name.substring(18);
                    String[] split = btype.split("\\.");
                    btype = split[0].toUpperCase();
                    if (btype.equals(lType.toString())) {
                        BlockPosition lPos = new BlockPosition(lEntity.getLocation());
                        BlockPosition lEdge1 = new BlockPosition(lBuilding.edge1);
                        BlockPosition lEdge2 = lEdge1.clone();
                        int lradius = lBuilding.influenceRadius;
                        lEdge1.add(-lradius, 0, -lradius);
                        lEdge1.y = 0;
                        lEdge2.add(lradius, 0, lradius);
                        lEdge2.y = lEntity.getWorld().getMaxHeight();
                        BlockRect lRect = new BlockRect(lEdge1, lEdge2);
                        if (lRect.isBetween(lPos)) {
                            Framework.plugin.log("building", "creature " + lType + " blocked!");
                            aEvent.setCancelled(true);
                            lEntity.getWorld().playEffect(lEntity.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
                        }
                    }
                }
            }
        }
    }
}
