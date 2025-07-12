package org.felixWegener.chestsort.client;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;

import java.util.*;

public class ChestScanner {

    public static List<BlockPos> scanForChests(ClientWorld clientWorld, PlayerEntity player) {
        ChunkPos chunkPos = new ChunkPos(player.getBlockPos());
        WorldChunk worldChunk = clientWorld.getChunk(chunkPos.x, chunkPos.z);

        List<BlockPos> blockPosList = new ArrayList<>();

        if (worldChunk == null) {
            System.out.println("chuck is null");
            return Collections.emptyList();
        }

        for (BlockEntity blockEntity : worldChunk.getBlockEntities().values()) {
            if (blockEntity instanceof ChestBlockEntity) {
                blockPosList.add(((ChestBlockEntity) blockEntity).getPos());
            }
        }
        return blockPosList;
    }

    public static Map<BlockPos, List<ItemStack>> getNearbyChestsWithItems(PlayerEntity player, ClientWorld world) {
        Map<BlockPos, List<ItemStack>> result = new HashMap<>();

        BlockPos center = player.getBlockPos();
        ChunkPos centerChunk = new ChunkPos(center);

        WorldChunk chunk = world.getChunkManager().getWorldChunk(centerChunk.x, centerChunk.z);

        if (chunk == null) {
            return result;
        }

        for (BlockEntity blockEntity : chunk.getBlockEntities().values()) {
            if (!(blockEntity instanceof ChestBlockEntity chest)) continue;

            BlockPos chestPos = chest.getPos();

            List<ItemStack> items = new ArrayList<>();
            for (int i = 0; i < chest.size(); i++) {
                items.add(chest.getStack(i).copy());
            }
            System.out.println(items.size());
            System.out.println(items);
            result.put(chestPos, items);

        }

        return result;
    }
}
