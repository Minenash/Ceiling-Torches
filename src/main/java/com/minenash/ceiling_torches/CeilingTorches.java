package com.minenash.ceiling_torches;

import net.fabricmc.api.ModInitializer;
import com.minenash.ceiling_torches.blocks.RedstoneCeilingTorchBlock;
import com.minenash.ceiling_torches.blocks.CeilingTorchBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CeilingTorches implements ModInitializer {

	public static final Block CEILING_TORCH = new CeilingTorchBlock(14, ParticleTypes.FLAME);
	public static final Block SOUL_CEILING_TORCH = new CeilingTorchBlock(10, ParticleTypes.SOUL_FIRE_FLAME);
	public static final Block REDSTONE_CEILING_TORCH = new RedstoneCeilingTorchBlock(7, DustParticleEffect.RED);

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, new Identifier("ceiling_torch"), CEILING_TORCH);
		Registry.register(Registry.BLOCK, new Identifier("redstone_ceiling_torch"), REDSTONE_CEILING_TORCH);
		Registry.register(Registry.BLOCK, new Identifier("soul_ceiling_torch"), SOUL_CEILING_TORCH);

		Registry.ITEM.set(Registry.ITEM.getRawId(Items.TORCH), Registry.ITEM.getKey(Items.TORCH).get(), new CeilingAndWallStandingBlockItem(Blocks.TORCH, Blocks.WALL_TORCH, CEILING_TORCH, new Item.Settings().group(ItemGroup.DECORATIONS)));
		Registry.ITEM.set(Registry.ITEM.getRawId(Items.REDSTONE_TORCH), Registry.ITEM.getKey(Items.REDSTONE_TORCH).get(), new CeilingAndWallStandingBlockItem(Blocks.REDSTONE_TORCH, Blocks.REDSTONE_WALL_TORCH, REDSTONE_CEILING_TORCH, new Item.Settings().group(ItemGroup.DECORATIONS)));
		Registry.ITEM.set(Registry.ITEM.getRawId(Items.SOUL_TORCH), Registry.ITEM.getKey(Items.SOUL_TORCH).get(), new CeilingAndWallStandingBlockItem(Blocks.SOUL_TORCH, Blocks.SOUL_WALL_TORCH, SOUL_CEILING_TORCH, new Item.Settings().group(ItemGroup.DECORATIONS)));
	}
}
