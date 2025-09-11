package dev.nekokitsune.holdablefrogs;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import dev.nekokitsune.holdablefrogs.datagen.HoldableFrogsRecipeProvider;

public class HoldableFrogsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(HoldableFrogsRecipeProvider::new);
	}
}
