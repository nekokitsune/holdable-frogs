package dev.nekokitsune.holdablefrogs.render;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.FrogEntityModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class HandheldTemperateFrogRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private FrogEntityModel<FrogEntity> frogModel;
    private final Identifier frogTexture = Identifier.of("minecraft", "textures/entity/frog/temperate_frog.png");

    @Override
    public void render(ItemStack stack,
                       ModelTransformationMode mode,
                       MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers,
                       int light,
                       int overlay) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (frogModel == null) {
            if (client.getEntityModelLoader() == null) {
                return;
            }
            frogModel = new FrogEntityModel<>(client.getEntityModelLoader().getModelPart(EntityModelLayers.FROG));
        }

        matrices.push();

        float scale = 1f;

        switch (mode) {
            case GUI -> {
                matrices.translate(0.5, 1.7, 0);
                matrices.scale(scale, scale, scale);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30f));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(135f));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180f));
            }
            case FIRST_PERSON_RIGHT_HAND, FIRST_PERSON_LEFT_HAND -> {
                matrices.translate(1.5, 2.0, -0.5);
                matrices.scale(1.5f, 1.5f, 1.5f);
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180f));
            }
            case THIRD_PERSON_RIGHT_HAND, THIRD_PERSON_LEFT_HAND -> {
                matrices.translate(-0.5, 1.0, 0.5);
                matrices.scale(0.75f, 0.75f, 0.75f);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180f));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180f));
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(135f));

            }
            case GROUND -> {
                matrices.translate(0.5, 1.0, 0.5);
                matrices.scale(0.5f, 0.5f, 0.5f);
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180f));
            }
            case FIXED -> {
                matrices.translate(0.5, 1.75, 0.5);
                matrices.scale(scale, scale, scale);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180f));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));
            }
        }

        VertexConsumer vc = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(frogTexture));
        frogModel.render(matrices, vc, light, overlay);

        matrices.pop();
    }
}