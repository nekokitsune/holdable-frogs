package dev.nekokitsune.holdablefrogs.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class FrogTongueUtil {
    public static Vec3d getHandPosition(PlayerEntity player, Hand hand, float tickDelta) {
        double x = MathHelper.lerp(tickDelta, player.prevX, player.getX());
        double y = MathHelper.lerp(tickDelta, player.prevY, player.getY()) + player.getStandingEyeHeight() - 0.2;
        double z = MathHelper.lerp(tickDelta, player.prevZ, player.getZ());

        boolean rightHand = (hand == Hand.MAIN_HAND) == (player.getMainArm() == Arm.RIGHT);
        float angle = (float) Math.toRadians(player.getYaw(tickDelta));
        double sideOffset = rightHand ? 0.35 : -0.35;

        double hx = x - Math.cos(angle) * sideOffset;
        double hz = z - Math.sin(angle) * sideOffset;

        return new Vec3d(hx, y, hz);
    }
}