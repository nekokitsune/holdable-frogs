package dev.nekokitsune.holdablefrogs.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import dev.nekokitsune.holdablefrogs.HoldableFrogs;

public class ModEntities {
    public static final EntityType<FrogTongueEntity> FROG_TONGUE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(HoldableFrogs.MOD_ID, "frog_tongue"),
            FabricEntityTypeBuilder.<FrogTongueEntity>create(SpawnGroup.MISC, FrogTongueEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .trackRangeBlocks(64).trackedUpdateRate(10)
                    .build()
    );

    public static void registerEntities() {

    }
}