package org.felixWegener.chestsort;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

import static org.felixWegener.chestsort.Sortconentntofchest.moveItemStacksToChest;

public class CommandHandler {

    public static void chestSortCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("sortChest")
            .executes(context -> {
                ServerWorld serverWorld = context.getSource().getWorld();
                PlayerEntity player = context.getSource().getPlayer();

                doSomething(context.getSource(), serverWorld, player);
                return 1;
            }
        ));
    }

    private static void doSomething(ServerCommandSource source, ServerWorld world, PlayerEntity player) {
        List<ItemStack> movedItems = moveItemStacksToChest(player, world);
        if(!movedItems.isEmpty()) {
            source.sendFeedback(() -> Text.literal("Items wurden verschoben: ").formatted(Formatting.AQUA), false);
            movedItems.forEach(itemStack -> {
                source.sendFeedback(() -> Text.literal(itemStack.getItem().toString()), false);
            });
        } else {
            source.sendFeedback(() -> Text.literal("Es konnten keine Items verschoben werden"), false);
        }
    }

    private static void printAllChestData(List<ChestBlockEntity> chests) {
        for  (ChestBlockEntity chest : chests) {
            for (int i = 0; i < chest.size(); i++) {
                System.out.println(chest.getStack(i).getItem().getName());
            }
        }
    }

}
