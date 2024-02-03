package lol.hub.wands;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public enum Wand {

    VOID(1), TORCH(2), CHICKEN(99);

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
        ItemStack item = new ItemStack(Material.STICK);
        var success = item.editMeta(meta -> meta.setCustomModelData(this.id));
        if (!success) throw new IllegalStateException("DUMM");
        return item;
    }

}
