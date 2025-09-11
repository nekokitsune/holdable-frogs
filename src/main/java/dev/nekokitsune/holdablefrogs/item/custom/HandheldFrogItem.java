package dev.nekokitsune.holdablefrogs.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import dev.nekokitsune.holdablefrogs.entity.FrogTongueEntity;
import dev.nekokitsune.holdablefrogs.entity.ModEntities;

public class HandheldFrogItem extends Item {
    public HandheldFrogItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            world.playSound(null,
                    user.getBlockPos(),
                    SoundEvents.ENTITY_FROG_TONGUE,
                    SoundCategory.PLAYERS,
                    1.0f,
                    1.0f);

            FrogTongueEntity tongue = new FrogTongueEntity(ModEntities.FROG_TONGUE, world);
            tongue.setOwner(user);
            tongue.refreshPositionAndAngles(
                    user.getX(),
                    user.getEyeY(),
                    user.getZ(),
                    user.getYaw(),
                    user.getPitch()
            );

            Vec3d direction = user.getRotationVec(1.0F).normalize().multiply(2.0);
            tongue.setVelocity(direction);

            world.spawnEntity(tongue);
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
