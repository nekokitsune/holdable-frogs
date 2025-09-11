package dev.nekokitsune.holdablefrogs;

import net.fabricmc.api.ModInitializer;

import dev.nekokitsune.holdablefrogs.entity.ModEntities;
import dev.nekokitsune.holdablefrogs.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HoldableFrogs implements ModInitializer {
	public static final String MOD_ID = "holdable-frogs";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModItems.registerModItems();
		ModEntities.registerEntities();

		LOGGER.info("Holdable Frogs initialized!");
	}
}