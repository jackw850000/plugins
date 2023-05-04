package net.unethicalite.plugins.shrimper;

import com.google.inject.Provides;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.pathfinder.GlobalCollisionMap;
import net.unethicalite.api.plugins.LoopedPlugin;
import net.unethicalite.api.scene.Tiles;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.GameObject;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.Locale;

@Extension
@PluginDescriptor(
        name = "kappapride shrimp catcher",
        description = "catches shrimps in lumbridge",
        enabledByDefault = false
)
@Slf4j
public class ChopperPlugin extends LoopedPlugin
{
    @Inject
    private ChopperConfig config;

    @Inject
    private GlobalCollisionMap collisionMap;

    private WorldArea fireArea = null;
    private WorldPoint startLocation = null;

    @Override
  /*  protected void startUp()
    {
        var emptyTile = Tiles.getSurrounding(Players.getLocal().getWorldLocation(), 8)
                .stream()
                .filter(tile ->
                        new WorldArea(tile.getWorldLocation(), 5, 4)
                                .toWorldPointList()
                                .stream()
                                .allMatch(x -> TileObjects.getFirstAt(x, a -> a instanceof GameObject) == null
                                        && !collisionMap.fullBlock(x))
                )
                .min(Comparator.comparingDouble(x -> x.distanceTo(Players.getLocal().getWorldLocation())))
                .orElseThrow();

        startLocation = Players.getLocal().getWorldLocation();
        fireArea = new WorldArea(emptyTile.getWorldLocation(), 5, 4);
    }
*/











    @Override
    protected int loop()
    {
        var local = Players.getLocal();

        if (!Inventory.contains("Small fishing net")) {
            GameObjects.getNearest("Small fishing net").interact("Take");
        }

        if (!local.isAnimating) {
            if (!Inventory.isFull) {
                NPCs.getNearest("Fishing spot").Interact("Net");
            } else {
                Inventory.getFirst("Raw shrimp").interact("Drop")
            }
        }
        return 1000;
    }

    @Provides
    ChopperConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(ChopperConfig.class);
    }
}
