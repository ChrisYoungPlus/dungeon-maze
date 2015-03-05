package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class NetherrackPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 2;
	public static final int ROOM_ITERATIONS = 15;
	public static final float ROOM_ITERATIONS_CHANCE = .05f;

    /** Populator constants. */
	public static final float BURNING_CHANCE = .2f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		final int x = args.getChunkX();
		final int y = args.getChunkY();
		final int z = args.getChunkZ();

        Block b = c.getBlock(x + rand.nextInt(8), rand.nextInt(2)+ y, z + rand.nextInt(8));
        if (b.getType() == Material.COBBLESTONE) {
            b.setType(Material.NETHERRACK);

            // Decide if the netherrack should be burning
            if(rand.nextFloat() < BURNING_CHANCE) {
                Block burnBlock = c.getBlock(b.getX(), b.getY() + 1, b.getZ());
                if (burnBlock.getType() == Material.AIR)
                    burnBlock.setType(Material.FIRE);
            }
        }
	}

    @Override
    public int getRoomIterations() {
        return ROOM_ITERATIONS;
    }

    @Override
    public float getRoomIterationsChance() {
        return ROOM_ITERATIONS_CHANCE;
    }
	
	/**
	 * Get the minimum layer
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return LAYER_MIN;
	}
	
	/**
	 * Get the maximum layer
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return LAYER_MAX;
	}
}