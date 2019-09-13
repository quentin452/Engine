package nullengine.event.item;

import nullengine.event.item.cause.ItemInteractCause;
import nullengine.item.ItemStack;

public abstract class ItemInteractEvent extends ItemEvent {
    private final ItemInteractCause cause;

    public ItemInteractEvent(ItemStack itemStack, ItemInteractCause cause) {
        super(itemStack);
        this.cause = cause;
    }

    public ItemInteractCause getCause() {
        return cause;
    }

    public static final class Click extends ItemInteractEvent {

        public Click(ItemStack itemStack, ItemInteractCause cause) {
            super(itemStack, cause);
        }
    }

    public static final class Activate extends ItemInteractEvent {

        public Activate(ItemStack itemStack, ItemInteractCause cause) {
            super(itemStack, cause);
        }
    }
}
