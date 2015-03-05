package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class GravePopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	public static final int LAYER_MIN = 2;
	public static final int LAYER_MAX = 6;
	public static final float ROOM_CHANCE = .005f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		final int x = args.getChunkX();
		final int z = args.getChunkZ();
        int graveX = x + rand.nextInt(6 - 2) + 1 + 2; // +2 because the grave is 3 long (so you also need to put the random on 4)
        int graveY = args.getFloorY() + 1;
        int graveZ = z + rand.nextInt(6) + 1;

        // The grave
        c.getBlock(graveX, graveY, graveZ).setType(Material.DOUBLE_STEP);
        c.getBlock(graveX - 1, graveY, graveZ).setType(Material.STEP);
        c.getBlock(graveX - 2, graveY, graveZ).setType(Material.STEP);

        // Put a sign on a grave and write some text on it
        c.getBlock(graveX, graveY + 1, graveZ).setType(Material.SIGN_POST);
        c.getBlock(graveX, graveY + 1, graveZ).setData((byte) 4);

        // Update the text on the sign
        Block b = c.getBlock(graveX, graveY + 1, graveZ);
        if (b != null) {
            BlockState bState = b.getState();
            if (bState instanceof Sign) {
                Sign s = (Sign) bState;

                // Add text to the grave sign
                addGraveTextToSign(s, rand);
            }
        }
	}
	
	// Code to add text to a sign
	public void addGraveTextToSign(Sign sign, Random random) {
		if (random.nextInt(100) < 5) {
			changeSignLine(sign, 1, "-404-");
			changeSignLine(sign, 2, "");
			changeSignLine(sign, 3, "NO BODY");
			changeSignLine(sign, 4, "FOUND");
			sign.update();
		} else {
			String[] graveUsernames = {
					"timvisee",
					"Metonymia",
					"Notch",
					"Jeb"
			};
			String[][] graveText = {
					{"Rest In","Pieces"},
					{"Ate raw", "porkchop"},
					{"Tried the","Nether"},
					{"Took a bath in","a lake of lava"},
					{"CREEPER!",""},
					{"Shot an arrow","straight up"},
					{"Found a hole","in the bedrock"},
					{"Suffocated","in gravel"},
					{"Tried the","/kill command"},
					{"Flew with a pig","of a mountain"},
					{"Lava ended","his life"},
					{"Used TNT the","wrong way"},
					{"Found a","flooded room"},
					{"Found a","creeper spawner"},
					{"Found a","Boss Room"},
					{"Joined the","bad side!"}
			};
			
			String graveUsername = "Notch";
			String[] selectedText = new String[]{"", ""};
			int selectedGraveTextIndex = random.nextInt(graveText.length);
			selectedText[0] = graveText[selectedGraveTextIndex][0];
			selectedText[1] = graveText[selectedGraveTextIndex][1];
			
			if(!isAnyPlayerOnline())
				graveUsername = graveUsernames[random.nextInt(graveUsernames.length)];
			else {
				List<Player> onlinePlayers = new ArrayList<Player>(Bukkit.getOnlinePlayers());
				graveUsername = onlinePlayers.get(random.nextInt(onlinePlayers.size())).getName();
			}
			
			changeSignLine(sign, 1, graveUsername);
			changeSignLine(sign, 2, "");
			changeSignLine(sign, 3, selectedText[0]);
			changeSignLine(sign, 4, selectedText[1]);
			sign.update();
		}	
	}
	
	// Code to change a line on a sign
	public void changeSignLine(Sign sign, int line, String text) {
		if(sign == null)
			return;
		
		if(line < 1 || line > 4)
			return;
		
		if(text == null)
			return;
		
		sign.setLine(line - 1, text);
	}

    @Override
    public float getRoomChance() {
        return ROOM_CHANCE;
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

	/**
	 * Check whether there's any player online.
	 *
	 * @return True if there's any player online, false otherwise.
	 */
	public boolean isAnyPlayerOnline() {
		return (Bukkit.getOnlinePlayers().size() > 0);
	}
}