package lol.hub.wands;

import lol.hub.testutils.Area;
import lol.hub.testutils.Bot;
import lol.hub.testutils.Item;
import lol.hub.testutils.TestServer;
import lol.hub.testutils.TestServerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TestServerExtension.class)
class WandTest {

    @ParameterizedTest
    @CsvSource({"void, 1", "light, 2", "chicken, 99"})
    void wandCommandGivesTheWand(String type, int id, TestServer server, Area area) {
        Bot bot = area.bot();

        assertTrue(bot.run("wand " + type), "wand command");

        assertEquals(List.of(new Item("minecraft:stick", 1, 0, "wands:" + type)), List.copyOf(bot.inventory().slots().values()), "inventory");
        // The item query reports no custom data, so the wand type is checked by vanilla's item predicate.
        String stickWithType = "minecraft:stick[custom_data={PublicBukkitValues:{\"wands:type\":" + id + "}}]";
        assertEquals(1, server.check("if items entity " + bot.name() + " container.* " + stickWithType).count(), "sticks with wands:type " + id);
    }

    @Test
    void unknownWandGivesNothing(Area area) {
        Bot bot = area.bot();

        assertTrue(bot.run("wand nope"), "wand command");

        assertEquals(List.of(), List.copyOf(bot.inventory().slots().values()), "inventory");
    }
}
