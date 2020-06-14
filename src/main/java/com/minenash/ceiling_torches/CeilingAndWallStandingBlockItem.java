package com.minenash.ceiling_torches;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;

import java.util.Map;

public class CeilingAndWallStandingBlockItem extends BlockItem {
    protected final Block wallBlock;
    protected final Block ceilingBlock;

    public CeilingAndWallStandingBlockItem(Block standingBlock, Block wallBlock, Block ceilingBlock, Item.Settings settings) {
        super(standingBlock, settings);
        this.wallBlock = wallBlock;
        this.ceilingBlock = ceilingBlock;
    }

    protected BlockState getPlacementState(ItemPlacementContext context) {
        BlockState state = null;
        WorldView world = context.getWorld();
        BlockPos pos = context.getBlockPos();

        for (Direction dir : context.getPlacementDirections()) {
            System.out.println(dir);
            BlockState temp = dir == Direction.UP ? this.ceilingBlock.getPlacementState(context) :
                              dir == Direction.DOWN ? this.getBlock().getPlacementState(context) :
                              this.wallBlock.getPlacementState(context);
            if (temp != null && temp.canPlaceAt(world, pos)) {
                state = temp;
                break;
            }
        }

        return state != null && world.canPlace(state, pos, EntityContext.absent()) ? state : null;
    }

    public void appendBlocks(Map<Block, Item> map, Item item) {
        super.appendBlocks(map, item);
        map.put(this.wallBlock, item);
        map.put(this.ceilingBlock, item);
    }
}
