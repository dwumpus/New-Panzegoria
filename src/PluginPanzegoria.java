import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
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

    private void PerformCopy(Player player) {
        templateList = new Hashtable<>(1);
        player.sendMessage(String.format("Copying Blocks between Location A and Location B"));
        int minX;
        int minY;
        int minZ;
        int maxX;
        int maxY;
        int maxZ;

        if(locA.getX() > locB.getX()) {
            maxX = (int) locA.getX();
            minX = (int) locB.getX();
        }
        else {
            maxX = (int) locB.getX();
            minX = (int) locA.getX();
        }

        if(locA.getY() > locB.getY()) {
            maxY = (int) locA.getY();
            minY = (int) locB.getY();
        }
        else {
            maxY = (int) locB.getY();
            minY = (int) locA.getY();
        }

        if(locA.getZ() > locB.getZ()) {
            maxZ = (int) locA.getZ();
            minZ = (int) locB.getZ();
        }
        else {
            maxZ = (int) locB.getZ();
            minZ = (int) locA.getZ();
        }


        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                for (int z = minZ; z < maxZ; z++) {
                    Block block = player.getWorld().getBlockAt(x,y,z);
                    if(block.getType() != Material.AIR) {
                        //Location target = block.getLocation().subtract(minX,minY,minZ);
                        templateList.putIfAbsent(block.getLocation(), block.getType());
                    }
                }
            }
        }

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
