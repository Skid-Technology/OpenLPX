package it.ytnoos.lpx.inventories.smartinvs;

import it.ytnoos.lpx.inventories.smartinvs.content.InventoryContents;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryProvider;
import it.ytnoos.lpx.inventories.smartinvs.content.Pagination;
import it.ytnoos.lpx.inventories.smartinvs.opener.InventoryOpener;
import it.ytnoos.lpx.utils.ColorUtils;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class SmartInventory {

    private final InventoryManager manager;

    private String id;

    private String title;

    private InventoryType type;

    private int rows;

    private int columns;

    private boolean closeable;

    private InventoryProvider provider;

    private SmartInventory parent;

    private List<InventoryListener<? extends Event>> listeners;

    private static final String TEXT = "No opener found for the inventory type ";

    private SmartInventory(InventoryManager inventoryManager) {
        this.manager = inventoryManager;
    }

    public static SmartInventory.Builder builder() {
        return new SmartInventory.Builder();
    }

    public Inventory createInventory(Player player) {
        Integer value = Integer.valueOf((byte) 0);
        return this.open(player, ((int) value));
    }

    public Inventory open(Player player, int value) {
        Optional<it.ytnoos.lpx.inventories.smartinvs.SmartInventory> inventory =
                this.manager.getInventory(player);
        inventory.ifPresent(
                result -> {
                    Stream<it.ytnoos.lpx.inventories.smartinvs.InventoryListener<? extends Event>>
                            stream2 =
                                    result.getListeners().stream()
                                            .filter(
                                                    result2 -> {
                                                        return result2.getType()
                                                                == InventoryCloseEvent.class;
                                                    });
                    stream2.forEach(
                            result3 -> {
                                InventoryCloseEvent inventoryCloseEvent =
                                        new InventoryCloseEvent(player.getOpenInventory());
                                InventoryCloseEvent result4 = inventoryCloseEvent;
                                InventoryCloseEvent inventoryCloseEvent2 = result4;
                                result3.accept(inventoryCloseEvent2);
                            });
                    SmartInventory smartInventory = (SmartInventory) (Object) null;
                    this.manager.setInventory(player, smartInventory);
                });
        SmartInventory smartInventory2 = this;
        InventoryContents.Impl impl = new InventoryContents.Impl(smartInventory2, player);
        Pagination pagination2 = impl.pagination();
        Integer value2 = ((Integer) value);
        pagination2.page(value2);
        this.manager.setContents(player, ((InventoryContents) impl));
        this.provider.init(player, ((InventoryContents) impl));
        InventoryType inventoryType = this.type;
        inventory = this.manager.findOpener(inventoryType);
        InventoryOpener inventoryOpener =
                (InventoryOpener)
                        inventory.orElseThrow(
                                () -> {
                                    String text = this.type.name();
                                    String text2 = TEXT;
                                    String result4 = text;
                                    return new IllegalStateException(text2 + result4);
                                });
        SmartInventory smartInventory3 = this;
        Inventory inventory2 = inventoryOpener.open(smartInventory3, player);
        SmartInventory smartInventory4 = this;
        this.manager.setInventory(player, smartInventory4);
        return inventory2;
    }

    public void close(Player player) {
        Stream<it.ytnoos.lpx.inventories.smartinvs.InventoryListener<? extends Event>> stream2 =
                this.listeners.stream()
                        .filter(
                                result -> {
                                    return result.getType() == InventoryCloseEvent.class;
                                });
        stream2.forEach(
                result2 -> {
                    InventoryCloseEvent inventoryCloseEvent =
                            new InventoryCloseEvent(player.getOpenInventory());
                    result2.accept(inventoryCloseEvent);
                });
        Player player2 = player;
        SmartInventory smartInventory = (SmartInventory) (Object) null;
        Player player3 = player2;
        this.manager.setInventory(player3, smartInventory);
        player.closeInventory();
        player2 = player;
        InventoryContents inventoryContents = (InventoryContents) (Object) null;
        Player player4 = player2;
        this.manager.setContents(player4, inventoryContents);
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public InventoryType getType() {
        return this.type;
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public boolean isCloseable() {
        return this.closeable;
    }

    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }

    public InventoryProvider getProvider() {
        return this.provider;
    }

    public Optional<it.ytnoos.lpx.inventories.smartinvs.SmartInventory> getParent() {
        return Optional.ofNullable(this.parent);
    }

    public InventoryManager getManager() {
        return this.manager;
    }

    List<it.ytnoos.lpx.inventories.smartinvs.InventoryListener<? extends Event>> getListeners() {
        return this.listeners;
    }

    public static final class Builder {

        private final List<InventoryListener<? extends Event>> listeners;

        private String id;

        private String title;

        private InventoryType type;

        private int rows;

        private int columns;

        private boolean closeable;

        private InventoryManager manager;

        private InventoryProvider provider;

        private SmartInventory parent;

        private Builder() {
            ArrayList values = new ArrayList();
            this.listeners = values;
            this.id = "unknown";
            this.title = "";
            this.type = InventoryType.CHEST;
            this.rows = 6;
            this.columns = 9;
            this.closeable = true;
        }

        public SmartInventory.Builder id(String id) {
            this.id = id;
            return this;
        }

        public SmartInventory.Builder title(String text) {
            this.title = ColorUtils.createText2(text);
            return this;
        }

        public SmartInventory.Builder type(InventoryType inventoryType) {
            this.type = inventoryType;
            return this;
        }

        public SmartInventory.Builder size(int rows, int columns) {
            this.rows = rows;
            this.columns = columns;
            return this;
        }

        public SmartInventory.Builder closeable(boolean closeable) {
            this.closeable = closeable;
            return this;
        }

        public SmartInventory.Builder provider(InventoryProvider inventoryProvider) {
            this.provider = inventoryProvider;
            return this;
        }

        public SmartInventory.Builder parent(SmartInventory smartInventory) {
            this.parent = smartInventory;
            return this;
        }

        public SmartInventory.Builder listener(Object result) {
            this.listeners.add((InventoryListener<? extends Event>) result);
            return this;
        }

        public SmartInventory.Builder manager(InventoryManager inventoryManager) {
            this.manager = inventoryManager;
            return this;
        }

        public SmartInventory build() {
            if (this.provider == null) {
                IllegalStateException illegalStateException =
                        new IllegalStateException(
                                "The provider of the SmartInventory.Builder must be set.");
                throw illegalStateException;
            } else {
                InventoryManager inventoryManager = this.manager;
                if (inventoryManager == null) {
                    IllegalStateException illegalStateException2 =
                            new IllegalStateException(
                                    "The manager of the SmartInventory.Builder must be set, or the SmartInvs should be loaded as a plugin.");
                    throw illegalStateException2;
                } else {
                    SmartInventory smartInventory = new SmartInventory(inventoryManager);
                    smartInventory.id = this.id;
                    smartInventory.title = this.title;
                    smartInventory.type = this.type;
                    smartInventory.rows = this.rows;
                    smartInventory.columns = this.columns;
                    smartInventory.closeable = this.closeable;
                    smartInventory.provider = this.provider;
                    smartInventory.parent = this.parent;
                    smartInventory.listeners = this.listeners;
                    return smartInventory;
                }
            }
        }
    }
}
