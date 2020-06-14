package com.minenash.ceiling_torches;

import net.fabricmc.api.ModInitializer;
import com.minenash.ceiling_torches.blocks.CeilingRedstoneTorchBlock;
import com.minenash.ceiling_torches.blocks.CeilingTorchBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CeilingTorches implements ModInitializer {

	public static final Block CEILING_TORCH = new CeilingTorchBlock(14);
	public static final Block CEILING_REDSTONE_TORCH = new CeilingRedstoneTorchBlock(7);

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, new Identifier("ceiling_torch"), CEILING_TORCH);
		Registry.register(Registry.BLOCK, new Identifier("ceiling_redstone_torch"), CEILING_REDSTONE_TORCH);
		Registry.ITEM.set(146, new Identifier("torch"), new CeilingAndWallStandingBlockItem(Blocks.TORCH, Blocks.WALL_TORCH, CEILING_TORCH, new Item.Settings().group(ItemGroup.DECORATIONS)));
		Registry.ITEM.set(173, new Identifier("redstone_torch"), new CeilingAndWallStandingBlockItem(Blocks.REDSTONE_TORCH, Blocks.REDSTONE_WALL_TORCH, CEILING_REDSTONE_TORCH, new Item.Settings().group(ItemGroup.DECORATIONS)));
	}
}
