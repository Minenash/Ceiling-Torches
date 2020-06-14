package com.minenash.ceiling_torches;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class ClientEntryPoint implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(CeilingTorches.CEILING_TORCH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CeilingTorches.SOUL_CEILING_TORCH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CeilingTorches.REDSTONE_CEILING_TORCH, RenderLayer.getCutout());
    }
}
