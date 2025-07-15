package org.felixWegener.chestsort;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.chunk.WorldChunk;

import java.util.ArrayList;
import java.util.List;

public class Sortconentntofchest {

    private static List<BlockEntity> getAllBlocks(PlayerEntity player, ServerWorld serverWorld) {
        WorldChunk worldChunk = serverWorld.getWorldChunk(player.getBlockPos());

        return new ArrayList<>(worldChunk.getBlockEntities().values());
    }

    private static List<ChestBlockEntity> getAllChestInChunk(List<BlockEntity> blocks) {
        List<ChestBlockEntity> chestsInChunk = new ArrayList<ChestBlockEntity>();

        for (BlockEntity blockEntity : blocks) {
            if (blockEntity instanceof ChestBlockEntity chest) {
                chestsInChunk.add(chest);
            }
        }

        return chestsInChunk;
    }

    private static boolean canCombine(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem();
    }

    private static boolean isEnderChestAvailable(List<BlockEntity> blocks) {
        for (BlockEntity blockEntity : blocks) {
            if (blockEntity instanceof EnderChestBlockEntity) {
                return true;
            }
        }
        return false;
    }

    public static List<ItemStack> moveItemStacksToChest(PlayerEntity player, ServerWorld serverworld) {
        Inventory inventory = player.getInventory();
        EnderChestInventory enderChest = player.getEnderChestInventory();

        List<BlockEntity> blocks = getAllBlocks(player, serverworld);
        List<ChestBlockEntity> chests = getAllChestInChunk(blocks);

        List<ItemStack> movedItems = new ArrayList<>();

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack playerStack = inventory.getStack(i);

            if (playerStack.isEmpty()) continue;

            if (isEnderChestAvailable(blocks)) {
                for (int j = 0; j < enderChest.size(); j++) {
                    ItemStack enderChestStack = enderChest.getStack(j);

                    if (!enderChestStack.isEmpty() && canCombine(playerStack, enderChestStack)) {
                        int transferable = Math.min(
                                playerStack.getCount(),
                                enderChestStack.getMaxCount() - enderChestStack.getCount()
                        );

                        if (transferable > 0) {
                            enderChestStack.increment(transferable);
                            playerStack.decrement(transferable);
                            movedItems.add(enderChestStack);
                        }
                    }
                }
            }

            for (ChestBlockEntity chest : chests) {

                for (int j = 0; j < chest.size(); j++) {
                    ItemStack chestStack = chest.getStack(j);

                    if (!chestStack.isEmpty() && canCombine(playerStack, chestStack)) {
                        int transferable = Math.min(
                                playerStack.getCount(),
                                chestStack.getMaxCount() - chestStack.getCount()
                        );

                        if (transferable > 0) {
                            chestStack.increment(transferable);
                            playerStack.decrement(transferable);
                            movedItems.add(chestStack);
                        }
                    }

                    if (playerStack.isEmpty()) {
                        break;
                    }
                }

                if (playerStack.isEmpty()) {
                    break;
                }
            }

            inventory.setStack(i, playerStack);
        }

        return movedItems;
    }
    
}





























