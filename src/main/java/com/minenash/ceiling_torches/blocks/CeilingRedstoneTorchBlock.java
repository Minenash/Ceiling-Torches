package com.minenash.ceiling_torches.blocks;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class CeilingRedstoneTorchBlock extends CeilingTorchBlock {
    public static final BooleanProperty LIT;
    private static final Map<BlockView, List<CeilingRedstoneTorchBlock.BurnoutEntry>> BURNOUT_MAP;

    public CeilingRedstoneTorchBlock(int lightLevel) {
        super(lightLevel);
        this.setDefaultState((this.stateManager.getDefaultState()).with(LIT, true));
    }

    public int getTickRate(WorldView worldView) {
        return 2;
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean moved) {
        for (Direction direction : Direction.values())
            world.updateNeighborsAlways(pos.offset(direction), this);
    }

    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!moved)
            for (Direction direction : Direction.values())
                world.updateNeighborsAlways(pos.offset(direction), this);
    }

    public int getWeakRedstonePower(BlockState state, BlockView view, BlockPos pos, Direction facing) {
        return state.get(LIT) && facing != Direction.DOWN ? 15 : 0;
    }

    protected boolean shouldUnpower(World world, BlockPos pos, BlockState state) {
        return world.isEmittingRedstonePower(pos.up(), Direction.UP);
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        update(state, world, pos, random, this.shouldUnpower(world, pos, state));
    }

    public static void update(BlockState state, World world, BlockPos pos, Random random, boolean unpower) {
        List list = BURNOUT_MAP.get(world);

        while(list != null && !list.isEmpty() && world.getTime() - ((CeilingRedstoneTorchBlock.BurnoutEntry)list.get(0)).time > 60L)
            list.remove(0);

        if (state.get(LIT)) {
            if (unpower) {
                world.setBlockState(pos, state.with(LIT, false), 3);
                if (isBurnedOut(world, pos, true)) {
                    world.playLevelEvent(1502, pos, 0);
                    world.getBlockTickScheduler().schedule(pos, world.getBlockState(pos).getBlock(), 160);
                }
            }
        } else if (!unpower && !isBurnedOut(world, pos, false))
            world.setBlockState(pos, state.with(LIT, true), 3);

    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean moved) {
        if (state.get(LIT) == this.shouldUnpower(world, pos, state) && !world.getBlockTickScheduler().isTicking(pos, this))
            world.getBlockTickScheduler().schedule(pos, this, this.getTickRate(world));
    }

    public int getStrongRedstonePower(BlockState state, BlockView view, BlockPos pos, Direction facing) {
        return facing == Direction.UP ? state.getWeakRedstonePower(view, pos, facing) : 0;
    }

    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            double d = (double)pos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            double e = (double)pos.getY() + 0.3D + (random.nextDouble() - 0.5D) * 0.2D;
            double f = (double)pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            world.addParticle(DustParticleEffect.RED, d, e, f, 0.0D, 0.0D, 0.0D);
        }
    }

    public int getLuminance(BlockState state) {
        return state.get(LIT) ? super.getLuminance(state) : 0;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    private static boolean isBurnedOut(World world, BlockPos pos, boolean addNew) {
        List<CeilingRedstoneTorchBlock.BurnoutEntry> list = BURNOUT_MAP.computeIfAbsent(world, (blockView) -> Lists.newArrayList());
        if (addNew)
            list.add(new CeilingRedstoneTorchBlock.BurnoutEntry(pos.toImmutable(), world.getTime()));

        int i = 0;
        for (BurnoutEntry entry : list) {
            if (entry.pos.equals(pos)) {
                ++i;
                if (i >= 8)
                    return true;
            }
        }
        return false;
    }

    static {
        LIT = Properties.LIT;
        BURNOUT_MAP = new WeakHashMap<>();
    }

    public static class BurnoutEntry {
        private final BlockPos pos;
        private final long time;

        public BurnoutEntry(BlockPos pos, long time) {
            this.pos = pos;
            this.time = time;
        }
    }
}
