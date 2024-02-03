package lol.hub.wands;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

import static net.kyori.adventure.text.Component.text;

public enum Wand {

    VOID(1), LIGHT(2), CHICKEN(99);

    public final int id;

    Wand(int id) {
        this.id = id;
    }

    public static @Nullable Wand byName(String name) {
        return Arrays.stream(Wand.values())
                .filter(wand -> wand.name().equalsIgnoreCase(name))
                .findAny().orElse(null);
    }

    public ItemStack newItem() {
        var item = new ItemStack(Material.STICK);
        item.editMeta(meta -> meta.displayName(text(String.format("%s Wand",
                StringUtils.capitalize(this.name().toLowerCase())))));
        item.editMeta(meta -> meta.setCustomModelData(this.id));
        return item;
    }

}
