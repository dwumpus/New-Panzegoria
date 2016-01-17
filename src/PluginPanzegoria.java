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

import java.util.Hashtable;

/**
 * Created by RogerB on 1/2/2016.
 */
public final class PluginPanzegoria extends JavaPlugin implements Listener {

    private Hashtable<Location, Material> blockList;
    private Hashtable<Location, Material> templateList;
    private Location locA;
    private Location locB;
    private Location buildLoc;

    @Override
    public void onEnable() {
        getLogger().info("Enabling...");
        getLogger().info("Enabled");
    }

    private void CreateTemplate(World world) {
        Vector min = CalculateMinimums();
        Vector max = CalculateMaximums();
        Vector offset = CalculateOffset();

        double templateX;
        double templateY;
        double templateZ;

        for (double x = min.getX(); x < max.getX(); x++) {
            for (double y = min.getY(); x < max.getY(); x++) {
                for (double z = min.getZ(); x < max.getZ(); x++) {
                    templateX = x + offset.getX();
                    templateY = y + offset.getY();
                    templateZ = z + offset.getZ();
                    templateList.putIfAbsent(new Location(world, templateX,templateY,templateZ), GetMaterialAtLocation(world,x,y,z));
                }
            }
        }
    }

    private Material GetMaterialAtLocation(World w, double x, double y, double z) {
        Location loc = new Location(w,x,y,z);
        return loc.getBlock().getType();
    }

    private Vector CalculateMinimums() {
        double minX;
        double minY;
        double minZ;

        if(locA.getX() > locB.getX()) {
            minX = locB.getX();
        }
        else {
            minX = locA.getX();
        }

        if(locA.getY() > locB.getY()) {
            minY = locB.getY();
        }
        else {
            minY = locA.getY();
        }

        if(locA.getZ() > locB.getZ()) {
            minZ = locB.getZ();
        }
        else {
            minZ = locA.getZ();
        }

        Vector vectorOut = new Vector(minX, minY, minZ);

        return vectorOut;
    }

    private Vector CalculateMaximums() {
        double maxX;
        double maxY;
        double maxZ;

        if(locA.getX() > locB.getX()) {
            maxX = locA.getX();
        }
        else {
            maxX = locB.getX();
        }

        if(locA.getY() > locB.getY()) {
            maxY = locA.getY();
        }
        else {
            maxY = locB.getY();
        }

        if(locA.getZ() > locB.getZ()) {
            maxZ = locA.getZ();
        }
        else {
            maxZ = locB.getZ();
        }

        Vector vectorOut = new Vector(maxX, maxY, maxZ);

        return vectorOut;
    }

    private Vector CalculateOffset(){

        Vector minVector = CalculateMinimums();
        double offsetX = minVector.getX();
        double offsetY = minVector.getY();
        double offsetZ = minVector.getZ();

        offsetX *= -1;
        offsetY *= -1;
        offsetZ *= -1;

        Vector vectorOut = new Vector(offsetX,offsetY,offsetZ);

        return vectorOut;
    }

    private void PerformCopy(Player player) {
        player.sendMessage(String.format("Copying Blocks between Location A and Location B"));




        player.sendMessage(String.format("Found %s blocks", templateList.size()));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player)sender;
        switch (label.toLowerCase()) {
            case "hint":
            {
                try {
                    if (blockList == null)
                        blockList = new Hashtable<>(1);

                    //Location target = player.getLocation().add(player.getLocation().getDirection().add(new Vector(2,1,0)));
                    /*
                    double offsetX = (double) (locA.getX() - buildLoc.getX());
                    double offsetY = (double) (locA.getY() - buildLoc.getY());
                    double offsetZ = (double) (locA.getZ() - buildLoc.getZ());
                    Location offset = new Location(player.getWorld(), offsetX, offsetY, offsetZ);
                    */
                    if(templateList == null) throw new Exception("templateList was null");
                    if(buildLoc == null) throw new Exception("buildLocation was null");
                    for (Location offsetLocation : templateList.keySet()
                            ) {

                        if(offsetLocation==null) throw new Exception("offsetLocation was null");
                        player.sendMessage(String.format("creating target..."));
                        Location target = buildLoc.subtract(offsetLocation);

                        if(target == null) throw new Exception("target was null");
                        player.sendMessage(String.format("target was created."));

                        player.sendMessage(String.format("getting material..."));
                        Material mat = templateList.get(offsetLocation);
                        if(mat == null) throw new Exception("Material was null");
                        player.sendMessage(String.format("material was retrieved."));

                        if(mat == null) throw new Exception("Target block material being set");
                        target.getBlock().setType(mat);
                        player.sendMessage(String.format("Material was set"));


                        blockList.putIfAbsent(target, mat);
                    }

                    //Location target = player.getLocation().add(player.getLocation().getDirection().add(new Vector(2,1,0)));
                    player.sendMessage(String.format("Trying to schedule block breaking"));
                    this.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
                        try {
                            if (blockList == null) throw new Exception("trying to destroy and blockList was null");

                            for (Location loc : blockList.keySet()
                                    ) {
                                loc.getBlock().breakNaturally(new ItemStack(Material.AIR));
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
