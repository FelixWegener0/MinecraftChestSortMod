package org.felixWegener.chestsort.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ChestsortClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {}

    private void initclientPart() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.getServer() != null) {
                ClientWorld clientWorld = client.world;
                World world = client.player.getEntityWorld();

                List<BlockPos> chests = ChestScanner.scanForChests(clientWorld, client.player);

                for (BlockPos pos : chests) {
                    BlockEntity blockEntity = world.getBlockEntity(pos);
                    if (blockEntity instanceof ChestBlockEntity chest) {
                        for (int i = 0; i < chest.size(); i++) {
                            System.out.println(chest.getStack(i).getItem());
                        }
                    }
                }
            }
        });
    }
}
