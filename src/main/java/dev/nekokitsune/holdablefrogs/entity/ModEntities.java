package dev.nekokitsune.holdablefrogs.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import dev.nekokitsune.holdablefrogs.HoldableFrogs;

public class ModEntities {
    public static final EntityType<FrogTongueEntity> FROG_TONGUE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(HoldableFrogs.MOD_ID, "frog_tongue"),
            EntityType.Builder.<FrogTongueEntity>create(FrogTongueEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f)
                    .maxTrackingRange(64).trackingTickInterval(10)
                    .build("frog_tongue")
    );

    public static void registerEntities() {

    }
}