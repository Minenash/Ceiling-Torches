package com.minenash.ceiling_torches;

import net.fabricmc.api.ModInitializer;
import com.minenash.ceiling_torches.blocks.CeilingRedstoneTorchBlock;
import com.minenash.ceiling_torches.blocks.CeilingTorchBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CeilingTorches implements ModInitializer {

	public static final Block CEILING_TORCH = new CeilingTorchBlock(14);
	public static final Block CEILING_REDSTONE_TORCH = new CeilingRedstoneTorchBlock(7);

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, new Identifier("ceiling_torch"), CEILING_TORCH);
		Registry.register(Registry.BLOCK, new Identifier("ceiling_redstone_torch"), CEILING_REDSTONE_TORCH);
		Registry.ITEM.set(Registry.ITEM.getRawId(Items.TORCH), Registry.ITEM.getId(Items.TORCH), new CeilingAndWallStandingBlockItem(Blocks.TORCH, Blocks.WALL_TORCH, CEILING_TORCH, new Item.Settings().group(ItemGroup.DECORATIONS)));
		Registry.ITEM.set(Registry.ITEM.getRawId(Items.REDSTONE_TORCH), Registry.ITEM.getId(Items.REDSTONE_TORCH), new CeilingAndWallStandingBlockItem(Blocks.REDSTONE_TORCH, Blocks.REDSTONE_WALL_TORCH, CEILING_REDSTONE_TORCH, new Item.Settings().group(ItemGroup.DECORATIONS)));
	}
}
