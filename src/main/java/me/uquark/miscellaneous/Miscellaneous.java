package me.uquark.miscellaneous;

import me.uquark.miscellaneous.item.Items;
import me.uquark.quarkcore.base.AbstractMod;
import net.fabricmc.api.ModInitializer;

public class Miscellaneous extends AbstractMod implements ModInitializer {
    public Miscellaneous() {
        super("miscellaneous");
    }

    @Override
    public void onInitialize() {
        Items.WRENCH_ITEM.register();
    }
}
