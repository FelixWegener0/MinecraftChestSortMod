package org.felixWegener.chestsort;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import static org.felixWegener.chestsort.CommandHandler.chestSortCommand;

public class Chestsort implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            chestSortCommand(dispatcher);
        });
    }

}
