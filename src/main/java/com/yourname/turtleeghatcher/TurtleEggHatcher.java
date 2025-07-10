package com.yourname.turtleeghatcher;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Turtle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TurtleEggHatcher extends JavaPlugin {

    private int scanRadius;
    private int scanIntervalTicks;
    private boolean dynamicScanInterval;
    private boolean useChunkSnapshots;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();
        startHatchingTask();
        getLogger().info("TurtleEggHatcher v2 Enabled with radius " + scanRadius + " and interval " + scanIntervalTicks + " ticks.");
    }

    private void loadConfig() {
        scanRadius = getConfig().getInt("scan-radius", 32);
        scanIntervalTicks = getConfig().getInt("scan-interval-ticks", 6000);
        dynamicScanInterval = getConfig().getBoolean("dynamic-scan-interval", true);
        useChunkSnapshots = getConfig().getBoolean("use-chunk-snapshots", true);
    }

    private void startHatchingTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().isEmpty()) return;

                long start = System.nanoTime();

                for (Player player : Bukkit.getOnlinePlayers()) {
                    World world = player.getWorld();
                    if (world.getEnvironment() != World.Environment.NORMAL) continue;

                    int centerX = player.getLocation().getBlockX();
                    int centerY = player.getLocation().getBlockY();
                    int centerZ = player.getLocation().getBlockZ();

                    for (Chunk chunk : world.getLoadedChunks()) {
                        if (useChunkSnapshots) {
                            var snapshot = chunk.getChunkSnapshot(true, true, false);
                            Bukkit.getScheduler().runTaskAsynchronously(TurtleEggHatcher.this, () -> {
                                scanChunkSnapshot(snapshot, chunk, world);
                            });
                        } else {
                            scanChunkDirectly(chunk, world);
                        }
                    }
                }

                long elapsed = System.nanoTime() - start;
                getLogger().info("Hatch scan took " + (elapsed / 1_000_000) + " ms");
            }
        }.runTaskTimer(this, 0L, dynamicScanInterval ? Math.max(scanIntervalTicks, 1000 * Bukkit.getOnlinePlayers().size()) : scanIntervalTicks);
    }

    private void scanChunkSnapshot(ChunkSnapshot snapshot, Chunk chunk, World world) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < world.getMaxHeight(); y++) {
                    if (snapshot.getBlockType(x, y, z) == Material.TURTLE_EGG) {
                        int finalX = chunk.getX() * 16 + x;
                        int finalY = y;
                        int finalZ = chunk.getZ() * 16 + z;
                        Bukkit.getScheduler().runTask(this, () -> hatchEgg(world, finalX, finalY, finalZ));
                    }
                }
            }
        }
    }

    private void scanChunkDirectly(Chunk chunk, World world) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = world.getMinHeight(); y < world.getMaxHeight(); y++) {
                    Block block = chunk.getBlock(x, y, z);
                    if (block.getType() == Material.TURTLE_EGG) {
                        Bukkit.getScheduler().runTask(this, () -> hatchEgg(world, block.getX(), block.getY(), block.getZ()));
                    }
                }
            }
        }
    }

    private void hatchEgg(World world, int x, int y, int z) {
        Block block = world.getBlockAt(x, y, z);
        block.setType(Material.AIR);
        world.spawn(block.getLocation().add(0.5, 1, 0.5), Turtle.class, turtle -> {
            turtle.setBaby();
        });
    }

    @Override
    public void onDisable() {
        getLogger().info("TurtleEggHatcher v2 Disabled.");
    }
}
