import Entities.Selection;
import org.bukkit.Location;
import org.bukkit.World;
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
@PrepareForTest(World.class)
public class TestSelection {

    private Vector point1 = new Vector(1,10,-3);
    private Vector point2 = new Vector(10,5,22);
    private World world = PowerMockito.mock(World.class);

    @Test
    public void testConstructor() {
        //arrange

        //act
        Selection selection = new Selection(world,point1,point2);

        //assert
        assertTrue("Instanced Correctly",selection instanceof Selection);
    }

    @Test
    public void testGetMin (){
        //arrange
        Selection selection = new Selection(world,point1,point2);

        //act
        Location minLocation = selection.getMinlocation();
        Vector locationVector = minLocation.toVector();

        //assert
        Vector idealResult = new Vector(1,5,-3);
        assertEquals(idealResult, locationVector);
    }

    @Test
    public void testGetMax (){
        //arrange
        Selection selection = new Selection(world,point1,point2);

        //act
        Location maxLocation = selection.getMaxlocation();
        Vector locationVector = maxLocation.toVector();

        //assert
        Vector idealResult = new Vector(10,10,22);
        assertEquals(idealResult, locationVector);
    }
}
