package dev.nekokitsune.holdablefrogs.item.custom;

import dev.nekokitsune.holdablefrogs.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.passive.FrogVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class FrogHolderItem extends Item {
    public FrogHolderItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        World world = user.getWorld();
        ItemStack handheld = new ItemStack(ModItems.HANDHELD_FROG);

        if (!(entity instanceof FrogEntity frog)) return ActionResult.PASS;

        if (frog.getVariant() == FrogVariant.TEMPERATE) {
            handheld = new ItemStack(ModItems.HANDHELD_TEMPERATE_FROG);
        } else if (frog.getVariant() == FrogVariant.COLD) {
            handheld = new ItemStack(ModItems.HANDHELD_COLD_FROG);
        } else if (frog.getVariant() == FrogVariant.WARM) {
            handheld = new ItemStack(ModItems.HANDHELD_WARM_FROG);
        }

        if (!world.isClient) {
            world.playSound(
                    null,
                    frog.getBlockPos(),
                    SoundEvents.ITEM_BUCKET_FILL_FISH,
                    SoundCategory.PLAYERS,
                    1.0f,
                    1.0f);
            frog.discard();

            user.setStackInHand(hand, handheld);
        }

        return ActionResult.SUCCESS;
    }
}
