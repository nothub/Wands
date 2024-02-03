package lol.hub.wands;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

public final class Plugin extends JavaPlugin {

    @Override
    public void onEnable() {

        getCommand("wand").setExecutor((sender, command, label, args) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("You are not a player, sorry ;(");
                return true;
            }

            if (args.length < 1) {
                return false;
            }

            String name = args[0];

            Wand wand = Wand.byName(name);
            if (wand == null) {
                sender.sendMessage(String.format("Unknown wand type: %s", name));
                return true;
            }

            player.getInventory().addItem(wand.newItem());
            return true;
        });

        getCommand("wand").setTabCompleter((sender, command, label, args) ->
            switch (args.length) {
            case 1 ->
                    Arrays.stream(Wand.values())
                            .map(wand -> wand.name().toLowerCase(Locale.ROOT))
                            .toList();
            default -> Collections.emptyList();
        });


        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler(ignoreCancelled = true)
            public void onPlayerJoin(PlayerJoinEvent ev) {
                var url = String.format(
                        "https://github.com/nothub/Wands/releases/download/v%s/wands-%s+%s-respack.jar",
                        getPluginMeta().getVersion(),
                        getPluginMeta().getVersion(),
                        getServer().getMinecraftVersion()
                );
                ev.getPlayer().setResourcePack(url);
            }
            @EventHandler(ignoreCancelled = true)
            public void onPlayerInteract(PlayerInteractEvent ev) {
                // ev.getItem();
            }
        }, this);

    }

}
