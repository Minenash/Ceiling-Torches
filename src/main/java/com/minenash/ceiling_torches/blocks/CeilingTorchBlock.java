package com.minenash.ceiling_torches.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class CeilingTorchBlock extends Block {
    protected static final VoxelShape BOUNDING_SHAPE = Block.createCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    public CeilingTorchBlock(int lightLevel) {
        super(FabricBlockSettings.of(Material.SUPPORTED).noCollision().breakInstantly().lightLevel(lightLevel).sounds(BlockSoundGroup.WOOD));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return BOUNDING_SHAPE;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
        return facing == Direction.UP && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return sideCoversSmallSquare(world, pos.up(), Direction.DOWN);
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        double d = (double)pos.getX() + 0.5D;
        double e = (double)pos.getY() + 0.3D;
        double f = (double)pos.getZ() + 0.5D;
        world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, d, e, f, 0.0D, 0.0D, 0.0D);
    }
}
