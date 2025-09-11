package dev.nekokitsune.holdablefrogs.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import dev.nekokitsune.holdablefrogs.HoldableFrogs;
import dev.nekokitsune.holdablefrogs.item.custom.FrogHolderItem;
import dev.nekokitsune.holdablefrogs.item.custom.HandheldFrogItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item FROG_HOLDER = register(
            "frog_holder",
            new FrogHolderItem(new Item.Settings().maxCount(1)));

    public static final Item HANDHELD_FROG = register(
            "handheld_frog",
            new HandheldFrogItem(new Item.Settings().maxCount(1)));

    public static final Item HANDHELD_TEMPERATE_FROG = register(
            "handheld_temperate_frog",
            new HandheldFrogItem(new Item.Settings().maxCount(1))
    );

    public static final Item HANDHELD_COLD_FROG = register(
            "handheld_cold_frog",
            new HandheldFrogItem(new Item.Settings().maxCount(1))
    );

    public static final Item HANDHELD_WARM_FROG = register(
            "handheld_warm_frog",
            new HandheldFrogItem(new Item.Settings().maxCount(1))
    );

    private static Item register(String name, Item item) {
        Identifier id = Identifier.of(HoldableFrogs.MOD_ID, name);
        return Registry.register(Registries.ITEM, id, item);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup) -> itemGroup.add(ModItems.FROG_HOLDER));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup) -> itemGroup.add(ModItems.HANDHELD_FROG));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup) -> itemGroup.add(ModItems.HANDHELD_TEMPERATE_FROG));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup) -> itemGroup.add(ModItems.HANDHELD_COLD_FROG));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup) -> itemGroup.add(ModItems.HANDHELD_WARM_FROG));
    }
}
