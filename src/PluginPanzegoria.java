import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by RogerB on 1/2/2016.
 */
public final class PluginPanzegoria extends JavaPlugin implements Listener {

    private ArrayList<Block> blockList;
    private Hashtable<Location, Block> templateList;
    private Location locA;
    private Location locB;
    private Location buildLoc;

    @Override
    public void onEnable() {
        getLogger().info("Enabling...");
        getLogger().info("Enabled");
    }

    private void CreateTemplate(Player player) {

        try {
            player.sendMessage(String.format("Beginning the creation of template."));
            templateList = new Hashtable<>();

            World world = player.getWorld();

            Vector min = CalculateMinimums();
            Vector max = CalculateMaximums();
            Vector offset = CalculateOffset();
            player.sendMessage(String.format("Offsets are X: %s Y: %s Z: %s", offset.getX(), offset.getY(), offset.getZ()));

            int templateX;
            int templateY;
            int templateZ;
            player.sendMessage(String.format("Minimums are X: %s Y: %s Z: %s", min.getX(), min.getY(), min.getZ()));
            //getLogger().info(String.format("X: %s Y: %s Z: %s", min.getX(), min.getY(), min.getZ()));
            int minX = (int) min.getX();
            int minY = (int) min.getY();
            int minZ = (int) min.getZ();

            int maxX = (int) max.getX();
            int maxY = (int) max.getY();
            int maxZ = (int) max.getZ();
            player.sendMessage(String.format("Maximums are X: %s Y: %s Z: %s", maxX, maxY, maxZ));

            Location originalLocation;
            Location targetLocation;
            Block blockOriginal;

            for (int x = minX; x < maxX; x++) {
                //player.sendMessage(String.format("Current block X: %s ", x));
                for (int y = minY; y < maxY; y++) {
                   //player.sendMessage(String.format("Current block Y: %s ", y));
                    for (int z = minZ; z < maxZ; z++) {
                        //player.sendMessage(String.format("Current block Z: %s ", z));
                        templateX = x + (int) offset.getX();
                        templateY = y + (int) offset.getY();
                        templateZ = z + (int) offset.getZ();
                        //player.sendMessage(String.format("Creating block at X: %s Y: %s Z: %s", templateX, templateY, templateZ));
                        originalLocation = new Location(world, x, y, z);
                        blockOriginal = originalLocation.getBlock();
                        if (blockOriginal.getType() != Material.AIR) {
                            targetLocation = new Location(player.getWorld(), templateX,templateY,templateZ);
                            templateList.put(targetLocation,blockOriginal);
                        }
                    }
                }
            }
        } catch (Exception ex)
        {
            player.sendRawMessage(ex.getMessage());
        }
    }


    private Material GetMaterialAtLocation(World w, int x, int y, int z) {
        Location loc = new Location(w,x,y,z);
        return loc.getBlock().getType();
    }

    private Vector CalculateMinimums() {
        int minX;
        int minY;
        int minZ;

        if(locA.getX() > locB.getX()) {
            minX = (int) locB.getX();
        }
        else {
            minX = (int) locA.getX();
        }

        if(locA.getY() > locB.getY()) {
            minY = (int) locB.getY();
        }
        else {
            minY = (int) locA.getY();
        }

        if(locA.getZ() > locB.getZ()) {
            minZ = (int) locB.getZ();
        }
        else {
            minZ = (int) locA.getZ();
        }

        Vector vectorOut = new Vector(minX, minY, minZ);

        return vectorOut;
    }

    private Vector CalculateMaximums() {
        int maxX;
        int maxY;
        int maxZ;

        if(locA.getX() > locB.getX()) {
            maxX = (int) locA.getX();
        }
        else {
            maxX = (int) locB.getX();
        }

        if(locA.getY() > locB.getY()) {
            maxY = (int) locA.getY();
        }
        else {
            maxY = (int) locB.getY();
        }

        if(locA.getZ() > locB.getZ()) {
            maxZ = (int) locA.getZ();
        }
        else {
            maxZ = (int) locB.getZ();
        }

        Vector vectorOut = new Vector(maxX, maxY, maxZ);

        return vectorOut;
    }

    private Vector CalculateOffset(){

        Vector minVector = CalculateMinimums();
        int offsetX = (int) minVector.getX();
        int offsetY = (int) minVector.getY();
        int offsetZ = (int) minVector.getZ();

        offsetX *= -1;
        offsetY *= -1;
        offsetZ *= -1;

        Vector vectorOut = new Vector(offsetX,offsetY,offsetZ);

        return vectorOut;
    }

    private void PerformCopy(Player player) {
        player.sendMessage(String.format("Copying Blocks between Location A and Location B"));

        CreateTemplate(player);

        player.sendMessage(String.format("Found %s blocks", templateList.size()));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player)sender;
        switch (label.toLowerCase()) {
            case "hint":
            {
                try {
                    blockList = new ArrayList<>();

                    for (Location loc: templateList.keySet()
                            ) {

                        Location target = new Location(player.getWorld(), loc.getX() + buildLoc.getX(), loc.getY()+  buildLoc.getY(), loc.getZ() +  buildLoc.getZ());
                        player.sendMessage(String.format("Calculating block at X: %s Y: %s Z: %s", target.getX(), target.getY(), target.getZ()));

                        Block block = target.getBlock();
                        Block originalBlock = templateList.get(loc);

                        block.setType(originalBlock.getType());
                        block.setData(originalBlock.getData());
                        block.getState().update();
                        blockList.add(block);
/*

                        player.sendMessage(String.format("Block Type is %s", material.toString()));

                        if(block.getType() == Material.AIR) {
                            block.setType(material);

                            player.sendMessage(String.format("Adding block to destroy list"));
                            blockList.put(target, material);
                            player.sendMessage(String.format("Added"));
                        }
  */
                    }

                    //Location target = player.getLocation().add(player.getLocation().getDirection().add(new Vector(2,1,0)));
                    this.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
                        try {
                            if (blockList == null) throw new Exception("trying to destroy and blockList was null");

                            for (Block block : blockList
                                    ) {
                                block.setType(Material.AIR);
                            }

                            blockList = null;
                        } catch (Exception ex) {
                            player.sendMessage(ex.toString());
                        }
                    }, 160);

                } catch (Exception ex) {
                    player.sendMessage(ex.toString());
                }
                break;
            }
            case "copy":
            {
                PerformCopy(player);

                break;
            }
            case "marka":
            {
                locA=player.getLocation();
                player.sendMessage(String.format("Location A Set"));
                break;
            }
            case "markb":
            {
                locB=player.getLocation();
                player.sendMessage(String.format("Location B Set"));
                break;
            }
            case "markc":
            {
                buildLoc=player.getLocation();
                player.sendMessage(String.format("Build Location Set"));
                break;
            }

        }

        return true;
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling...");
        getLogger().info("Disabled.");
    }

    @Override
    public void onLoad() {
        getLogger().info("Loading...");
        getLogger().info("Loaded.");
    }
}
