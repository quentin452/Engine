package nullengine.registry.game;

import nullengine.block.Block;
import nullengine.item.BlockItem;
import nullengine.item.Item;
import nullengine.registry.Registry;

import javax.annotation.Nullable;

public interface ItemRegistry extends Registry<Item> {

    @Nullable
    BlockItem getItemBlock(Block block);

    boolean hasItemBlock(Block block);
}
