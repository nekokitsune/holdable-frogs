package dev.nekokitsune.holdablefrogs;

import dev.nekokitsune.holdablefrogs.render.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import dev.nekokitsune.holdablefrogs.entity.ModEntities;
import dev.nekokitsune.holdablefrogs.item.ModItems;

public class HoldableFrogsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BuiltinItemRendererRegistry.INSTANCE.register(
		 		ModItems.HANDHELD_FROG,
				new HandheldFrogRenderer()
		);

		BuiltinItemRendererRegistry.INSTANCE.register(
				ModItems.HANDHELD_TEMPERATE_FROG,
				new HandheldTemperateFrogRenderer()
		);

		BuiltinItemRendererRegistry.INSTANCE.register(
				ModItems.HANDHELD_COLD_FROG,
				new HandheldColdFrogRenderer()
		);

		BuiltinItemRendererRegistry.INSTANCE.register(
				ModItems.HANDHELD_WARM_FROG,
				new HandheldWarmFrogRenderer()
		);

		EntityRendererRegistry.register(
				ModEntities.FROG_TONGUE,
				FrogTongueRenderer::new
		);
	}
}