package com.yourname.turtleeghatcher;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Turtle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TurtleEggHatcher extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("TurtleEggHatcher Enabled! Eggs will hatch 24/7.");
        startHatchingTask();
    }

    private void startHatchingTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (World world : Bukkit.getWorlds()) {
                    for (var chunk : world.getLoadedChunks()) {
                        for (int x = 0; x < 16; x++) {
                            for (int y = world.getMinHeight(); y < world.getMaxHeight(); y++) {
                                for (int z = 0; z < 16; z++) {
                                    Block block = chunk.getBlock(x, y, z);
                                    if (block.getType() == Material.TURTLE_EGG) {
                                        // Force hatch logic: Replace egg with baby turtle
                                        block.setType(Material.AIR);
                                        world.spawn(block.getLocation().add(0.5, 1, 0.5), Turtle.class, turtle -> {
                                            turtle.setBaby();
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(this, 0L, 600L); // Every 30 seconds (600 ticks)
    }

    @Override
    public void onDisable() {
        getLogger().info("TurtleEggHatcher Disabled.");
    }
}
