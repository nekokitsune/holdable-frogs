package dev.nekokitsune.holdablefrogs.render;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import dev.nekokitsune.holdablefrogs.entity.FrogTongueEntity;
import dev.nekokitsune.holdablefrogs.util.FrogTongueUtil;

public class FrogTongueRenderer extends EntityRenderer<FrogTongueEntity> {
    private static final float TONGUE_THICKNESS = 0.05f;
    private static final Identifier TEXTURE = Identifier.of("textures/entity/fishing_hook.png");

    public FrogTongueRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(FrogTongueEntity tongue, float yaw, float tickDelta,
                       MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        PlayerEntity player = (PlayerEntity) tongue.getOwner();
        if (player == null) return;

        matrices.push();

        Vec3d cameraPos = this.dispatcher.camera.getPos();
        Vec3d handPos = FrogTongueUtil.getHandPosition(player, Hand.MAIN_HAND, tickDelta);
        double handX = handPos.x - cameraPos.x;
        double handY = handPos.y - cameraPos.y;
        double handZ = handPos.z - cameraPos.z;

        Vec3d tonguePos = tongue.getLerpedPos(tickDelta);
        double tongueX = tonguePos.x - cameraPos.x;
        double tongueY = tonguePos.y - cameraPos.y + 0.25;
        double tongueZ = tonguePos.z - cameraPos.z;

        float dx = (float)(handX - tongueX);
        float dy = (float)(handY - tongueY);
        float dz = (float)(handZ - tongueZ);

        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getLeash());
        MatrixStack.Entry entry = matrices.peek();

        int segments = 16;
        for (int i = 0; i < segments; i++) {
            renderTongueSegment(dx, dy, dz, buffer, entry,
                    percentage(i, segments), percentage(i + 1, segments),
                    light);
        }

        matrices.pop();
        super.render(tongue, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    private static float percentage(int value, int max) {
        return (float) value / (float) max;
    }

    private static void renderTongueSegment(float dx, float dy, float dz,
                                            VertexConsumer buffer, MatrixStack.Entry entry,
                                            float t1, float t2,
                                            int light) {

        float x1 = dx * t1;
        float y1 = dy * (t1 * t1 + t1) * 0.5F + 0.25F;
        float z1 = dz * t1;

        float x2 = dx * t2;
        float y2 = dy * (t2 * t2 + t2) * 0.5F + 0.25F;
        float z2 = dz * t2;

        Vec3d start = new Vec3d(x1, y1, z1);
        Vec3d end   = new Vec3d(x2, y2, z2);

        Vec3d dir = end.subtract(start).normalize();
        if (dir.lengthSquared() == 0) dir = new Vec3d(0, 1, 0);

        Vec3d side = new Vec3d(0, 1, 0).crossProduct(dir).normalize().multiply(TONGUE_THICKNESS * 0.5);
        if (side.lengthSquared() == 0) side = new Vec3d(0.05, 0, 0);

        Vec3d sL = start.add(side);
        Vec3d sR = start.subtract(side);
        Vec3d eL = end.add(side);
        Vec3d eR = end.subtract(side);

        addVertex(buffer, entry, sL, light);
        addVertex(buffer, entry, eL, light);
        addVertex(buffer, entry, eR, light);

        addVertex(buffer, entry, sL, light);
        addVertex(buffer, entry, eR, light);
        addVertex(buffer, entry, sR, light);
    }

    private static void addVertex(VertexConsumer buffer, MatrixStack.Entry entry, Vec3d pos, int light) {
        buffer.vertex(entry, (float) pos.x, (float) pos.y, (float) pos.z)
                .color(230, 20, 20, 255)
                .light(light)
                .normal(0, 1, 0);
    }


    @Override
    public Identifier getTexture(FrogTongueEntity entity) {
        return TEXTURE;
    }
}
