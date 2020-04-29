package me.uquark.miscellaneous.item;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.item.AbstractItem;
import net.minecraft.item.ItemGroup;

public class NecromassItem extends AbstractItem {
    public NecromassItem() {
        super(Miscellaneous.modid, "necromass", new Settings().group(ItemGroup.BREWING));
    }
}
