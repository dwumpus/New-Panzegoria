/**
 * Created by roger.boone on 6/5/2017.
 */
import Entities.Selection;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

/**
 * Created by roger.boone on 6/5/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Player.class, PlayerInteractEvent.class})
public class TestBlueprintListener {

    PlayerInteractEvent event;
    Player player;

    public TestBlueprintListener() {
        event = PowerMockito.mock(PlayerInteractEvent.class);
        player = PowerMockito.mock(Player.class);
    }

    @Test
    public void testLeftClick() {
        //arrange


        //act

        //assert
    }

    @Test
    public void testRightClick() {
        //arrange

        //act

        //assert
    }
}
