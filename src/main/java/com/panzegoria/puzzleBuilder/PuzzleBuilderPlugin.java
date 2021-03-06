package com.panzegoria.puzzleBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.panzegoria.puzzleBuilder.Listeners.MarkRegionListener;
import com.panzegoria.puzzleBuilder.Listeners.SigningBookListener;
import com.panzegoria.puzzleBuilder.Services.Capabilities.PuzzlePersistence;
import com.panzegoria.puzzleBuilder.Services.PuzzleRepositoryClient;
import com.panzegoria.puzzleBuilder.Services.Capabilities.Stateful;
import com.panzegoria.puzzleBuilder.Services.Capabilities.ObjectWrappers;
import com.panzegoria.puzzleBuilder.Services.PlayerStateService;
import com.panzegoria.puzzleBuilder.Services.SpigotWrappers;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created by RogerB on 1/2/2016.
 */
public final class PuzzleBuilderPlugin extends JavaPlugin {

    private final static String PLUGIN_NAME = PuzzleBuilderPlugin.class.getCanonicalName();
    public static PuzzleBuilderPlugin INSTANCE;
    private static ItemStack REGION_SELECTOR;
    private Stateful playerState;
    public static Logger logger = Logger.getLogger(PLUGIN_NAME);
    private FileConfiguration config = getConfig();
    public static Gson gson = new GsonBuilder().create(); //setPrettyPrinting().create();
    public static ObjectWrappers wrappers;
    public static PuzzlePersistence persistence;

    @Override
    public void onEnable() {
        logger.info(String.format("%s plugin starting up...", PLUGIN_NAME));
        this.INSTANCE = this;
        this.playerState = new PlayerStateService();
        readConfig();
        wrappers = new SpigotWrappers();
        persistence = new PuzzleRepositoryClient( "http://localhost:8080/PuzzleRepositoryRest-0.0.1-SNAPSHOT/rest/puzzle");

        getServer().getPluginManager().registerEvents(
                new MarkRegionListener(playerState, REGION_SELECTOR), this);

        getServer().getPluginManager().registerEvents(
                new SigningBookListener(playerState), this);

        logger.info(String.format("%s plugin enabled!", PLUGIN_NAME));
    }

    private void readConfig() {
        REGION_SELECTOR = (ItemStack) config.get("Tools.Drafting");

        if(REGION_SELECTOR == null) {
            config.addDefault("Tools.Drafting", new ItemStack(Material.STICK));
            config.options().copyDefaults(true);
            saveConfig();
            REGION_SELECTOR = (ItemStack)config.get("Tools.Drafting");
        }
    }

    /**
     * private void CreateTemplate(Player player) {
     * <p>
     * try {
     * player.sendMessage(String.format("Beginning the creation of template."));
     * templateList = new Hashtable<>();
     * <p>
     * World world = player.getWorld();
     * <p>
     * Vector min = CalculateMinimums();
     * Vector max = CalculateMaximums();
     * Vector offset = CalculateOffset();
     * player.sendMessage(String.format("Offsets are X: %s Y: %s Z: %s", offset.getX(), offset.getY(), offset.getZ()));
     * <p>
     * int templateX;
     * int templateY;
     * int templateZ;
     * player.sendMessage(String.format("Minimums are X: %s Y: %s Z: %s", min.getX(), min.getY(), min.getZ()));
     * //getLogger().info(String.format("X: %s Y: %s Z: %s", min.getX(), min.getY(), min.getZ()));
     * int minX = (int) min.getX();
     * int minY = (int) min.getY();
     * int minZ = (int) min.getZ();
     * <p>
     * int maxX = (int) max.getX();
     * int maxY = (int) max.getY();
     * int maxZ = (int) max.getZ();
     * player.sendMessage(String.format("Maximums are X: %s Y: %s Z: %s", maxX, maxY, maxZ));
     * <p>
     * Location originalLocation;
     * Location targetLocation;
     * Block blockOriginal;
     * <p>
     * for (int x = minX; x < maxX; x++) {
     * //player.sendMessage(String.format("Current block X: %s ", x));
     * for (int y = minY; y < maxY; y++) {
     * //player.sendMessage(String.format("Current block Y: %s ", y));
     * for (int z = minZ; z < maxZ; z++) {
     * //player.sendMessage(String.format("Current block Z: %s ", z));
     * templateX = x + (int) offset.getX();
     * templateY = y + (int) offset.getY();
     * templateZ = z + (int) offset.getZ();
     * //player.sendMessage(String.format("Creating block at X: %s Y: %s Z: %s", templateX, templateY, templateZ));
     * originalLocation = new Location(world, x, y, z);
     * blockOriginal = originalLocation.getBlock();
     * if (blockOriginal.getType() != Material.AIR) {
     * targetLocation = new Location(player.getWorld(), templateX,templateY,templateZ);
     * templateList.put(targetLocation,blockOriginal);
     * }
     * }
     * }
     * }
     * } catch (Exception ex)
     * {
     * player.sendRawMessage(ex.getMessage());
     * }
     * }
     * <p>
     * <p>
     * private Material GetMaterialAtLocation(World w, int x, int y, int z) {
     * Location loc = new Location(w,x,y,z);
     * return loc.getBlock().getType();
     * }
     * <p>
     * private Vector CalculateMinimums() {
     * int minX;
     * int minY;
     * int minZ;
     * <p>
     * if(locA.getX() > locB.getX()) {
     * minX = (int) locB.getX();
     * }
     * else {
     * minX = (int) locA.getX();
     * }
     * <p>
     * if(locA.getY() > locB.getY()) {
     * minY = (int) locB.getY();
     * }
     * else {
     * minY = (int) locA.getY();
     * }
     * <p>
     * if(locA.getZ() > locB.getZ()) {
     * minZ = (int) locB.getZ();
     * }
     * else {
     * minZ = (int) locA.getZ();
     * }
     * <p>
     * Vector vectorOut = new Vector(minX, minY, minZ);
     * <p>
     * return vectorOut;
     * }
     * <p>
     * private Vector CalculateMaximums() {
     * int maxX;
     * int maxY;
     * int maxZ;
     * <p>
     * if(locA.getX() > locB.getX()) {
     * maxX = (int) locA.getX();
     * }
     * else {
     * maxX = (int) locB.getX();
     * }
     * <p>
     * if(locA.getY() > locB.getY()) {
     * maxY = (int) locA.getY();
     * }
     * else {
     * maxY = (int) locB.getY();
     * }
     * <p>
     * if(locA.getZ() > locB.getZ()) {
     * maxZ = (int) locA.getZ();
     * }
     * else {
     * maxZ = (int) locB.getZ();
     * }
     * <p>
     * Vector vectorOut = new Vector(maxX, maxY, maxZ);
     * <p>
     * return vectorOut;
     * }
     * <p>
     * private Vector CalculateOffset(){
     * <p>
     * Vector minVector = CalculateMinimums();
     * int offsetX = (int) minVector.getX();
     * int offsetY = (int) minVector.getY();
     * int offsetZ = (int) minVector.getZ();
     * <p>
     * offsetX *= -1;
     * offsetY *= -1;
     * offsetZ *= -1;
     * <p>
     * Vector vectorOut = new Vector(offsetX,offsetY,offsetZ);
     * <p>
     * return vectorOut;
     * }
     * <p>
     * private void PerformCopy(Player player) {
     * player.sendMessage(String.format("Copying Blocks between Location A and Location B"));
     * <p>
     * CreateTemplate(player);
     * <p>
     * player.sendMessage(String.format("Found %s blocks", templateList.size()));
     * }
     *
     * @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
     * <p>
     * Player player = (Player)sender;
     * switch (label.toLowerCase()) {
     * case "hint":
     * {
     * try {
     * blockList = new ArrayList<>();
     * <p>
     * for (Location loc: templateList.keySet()
     * ) {
     * <p>
     * Location target = new Location(player.getWorld(), loc.getX() + buildLoc.getX(), loc.getY()+  buildLoc.getY(), loc.getZ() +  buildLoc.getZ());
     * player.sendMessage(String.format("Calculating block at X: %s Y: %s Z: %s", target.getX(), target.getY(), target.getZ()));
     * <p>
     * Block block = target.getBlock();
     * Block originalBlock = templateList.get(loc);
     * <p>
     * block.setType(originalBlock.getType());
     * block.setData(originalBlock.getData());
     * block.getState().update();
     * blockList.add(block);
     * /*
     * <p>
     * player.sendMessage(String.format("Block Type is %s", material.toString()));
     * <p>
     * if(block.getType() == Material.AIR) {
     * block.setType(material);
     * <p>
     * player.sendMessage(String.format("Adding block to destroy list"));
     * blockList.put(target, material);
     * player.sendMessage(String.format("Added"));
     * }
     * <p>
     * }
     * <p>
     * //Location target = player.getLocation().add(player.getLocation().getDirection().add(new Vector(2,1,0)));
     * this.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
     * try {
     * if (blockList == null) throw new Exception("trying to destroy and blockList was null");
     * <p>
     * for (Block block : blockList
     * ) {
     * block.setType(Material.AIR);
     * }
     * <p>
     * blockList = null;
     * } catch (Exception ex) {
     * player.sendMessage(ex.toString());
     * }
     * }, 160);
     * <p>
     * } catch (Exception ex) {
     * player.sendMessage(ex.toString());
     * }
     * break;
     * }
     * case "copy":
     * {
     * PerformCopy(player);
     * <p>
     * break;
     * }
     * case "marka":
     * {
     * locA=player.getLocation();
     * player.sendMessage(String.format("Location A Set"));
     * break;
     * }
     * case "markb":
     * {
     * locB=player.getLocation();
     * player.sendMessage(String.format("Location B Set"));
     * break;
     * }
     * case "markc":
     * {
     * buildLoc=player.getLocation();
     * player.sendMessage(String.format("Build Location Set"));
     * break;
     * }
     * <p>
     * }
     * <p>
     * return true;
     * }
     **/
    @Override
    public void onDisable() {
        logger.info(String.format("%s plugin disabled!", PLUGIN_NAME));
    }

    @Override
    public void onLoad() {
        logger.info(String.format("%s plugin loaded!", PLUGIN_NAME));
    }

}
