package com.flower.mitayclient.GUI.Screen;

import com.flower.mitayclient.GUI.Button.PlaceListButton.Small.SmallButtonWidget;
import com.flower.mitayclient.util.ModIdentifier;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.StatsListener;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.StatType;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class MyStatScreen extends Screen implements StatsListener
{
    static final Identifier SLOT_TEXTURE = new Identifier("container/slot");
    static final Identifier HEADER_TEXTURE = new Identifier("statistics/header");
    static final Identifier SORT_UP_TEXTURE = new Identifier("statistics/sort_up");
    static final Identifier SORT_DOWN_TEXTURE = new Identifier("statistics/sort_down");
    private static final Text DOWNLOADING_STATS_TEXT = Text.translatable("multiplayer.downloadingStats");
    static final Text NONE_TEXT = Text.translatable("stats.none");
    protected final Screen parent;
    private GeneralStatsListWidget generalStats;
    ItemStatsListWidget itemStats;
    private EntityStatsListWidget mobStats;
    final StatHandler statHandler;
    @Nullable
    private AlwaysSelectedEntryListWidget<?> selectedList;
    private boolean downloadingStats = true;


    public MyStatScreen(Screen parent, StatHandler statHandler)
    {
        super(Text.translatable("gui.stats"));
        this.parent = parent;
        this.statHandler = statHandler;
    }

    @Override
    protected void init()
    {
        this.downloadingStats = true;
        this.client.getNetworkHandler().sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.REQUEST_STATS));
    }

    public void createLists()
    {
        this.generalStats = new GeneralStatsListWidget(this.client);
        this.itemStats = new ItemStatsListWidget(this.client);
        this.mobStats = new EntityStatsListWidget(this.client);
    }

    public void createButtons()
    {
        this.addDrawableChild(
                SmallButtonWidget.builder(Text.translatable("stat.generalButton"), button -> this.selectStatList(this.generalStats))
                        .dimensions(230, 50, 100, 22).icon("general")
                        .build()
        );
        SmallButtonWidget buttonWidget = this.addDrawableChild(
                SmallButtonWidget.builder(Text.translatable("stat.itemsButton"), button -> this.selectStatList(this.itemStats))
                        .dimensions(230, 90, 100, 22).icon("items")
                        .build()
        );
        SmallButtonWidget buttonWidget2 = this.addDrawableChild(
                SmallButtonWidget.builder(Text.translatable("stat.mobsButton"), button -> this.selectStatList(this.mobStats))
                        .dimensions(230, 130, 100, 22).icon("mobs")
                        .build()
        );
        this.addDrawableChild(
                SmallButtonWidget.builder(ScreenTexts.DONE, button -> this.client.setScreen(this.parent)).dimensions(230, 170, 100, 22).icon(null).build()
        );
        if (this.itemStats.children().isEmpty()) {
            buttonWidget.active = false;
        }

        if (this.mobStats.children().isEmpty()) {
            buttonWidget2.active = false;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.downloadingStats) {
            this.renderBackground(context, mouseX, mouseY, delta);
            context.drawCenteredTextWithShadow(this.textRenderer, DOWNLOADING_STATS_TEXT, this.width / 2, this.height / 2, 16777215);
            context.drawCenteredTextWithShadow(
                    this.textRenderer,
                    PROGRESS_BAR_STAGES[(int)(Util.getMeasuringTimeMs() / 150L % (long)PROGRESS_BAR_STAGES.length)],
                    this.width / 2,
                    this.height / 2 + 9 * 2,
                    16777215
            );
        } else {
            super.render(context, mouseX, mouseY, delta);
            context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 16777215);
        }
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta)
    {
        this.renderBackgroundTexture(context);
    }

    @Override
    public void onStatsReady()
    {
        if (this.downloadingStats) {
            this.createLists();
            this.createButtons();
            this.selectStatList(this.generalStats);
            this.downloadingStats = false;
        }
    }


    @Override
    public boolean shouldPause() {
        return !this.downloadingStats;
    }

    public void selectStatList(@Nullable AlwaysSelectedEntryListWidget<?> list)
    {
        if (this.selectedList != null) {
            this.remove(this.selectedList);
        }

        if (list != null) {
            this.addDrawableChild(list);
            this.selectedList = list;
        }
    }

    static String getStatTranslationKey(Stat<Identifier> stat) {
        return "stat." + stat.getValue().toString().replace(':', '.');
    }

    int getColumnX(int index) {
        return 115 + 40 * index;
    }

    void renderStatItem(DrawContext context, int x, int y, Item item) {
//        this.renderIcon(context, x + 1, y + 1, SLOT_TEXTURE);
        context.drawItemWithoutEntity(item.getDefaultStack(), x + 2, y + 2);
    }

    void renderIcon(DrawContext context, int x, int y, Identifier texture) {
        context.drawGuiTexture(texture, x, y, 0, 18, 18);
    }


    @Environment(EnvType.CLIENT)
    class EntityStatsListWidget extends AlwaysSelectedEntryListWidget<EntityStatsListWidget.Entry> {
        public EntityStatsListWidget(MinecraftClient client) {
            super(client, MyStatScreen.this.width, MyStatScreen.this.height - 96, 32, 9 * 4);

            for (EntityType<?> entityType : Registries.ENTITY_TYPE) {
                if (statHandler.getStat(Stats.KILLED.getOrCreateStat(entityType)) > 0
                        || statHandler.getStat(Stats.KILLED_BY.getOrCreateStat(entityType)) > 0) {
                    addEntry(new EntityStatsListWidget.Entry(entityType));
                }
            }
        }

        @Environment(EnvType.CLIENT)
        class Entry extends AlwaysSelectedEntryListWidget.Entry<EntityStatsListWidget.Entry> {
            private final Text entityTypeName;
            private final Text killedText;
            private final boolean killedAny;
            private final Text killedByText;
            private final boolean killedByAny;

            public Entry(EntityType<?> entityType) {
                this.entityTypeName = entityType.getName();
                int i = statHandler.getStat(Stats.KILLED.getOrCreateStat(entityType));
                if (i == 0) {
                    this.killedText = Text.translatable("stat_type.minecraft.killed.none", this.entityTypeName);
                    this.killedAny = false;
                } else {
                    this.killedText = Text.translatable("stat_type.minecraft.killed", i, this.entityTypeName);
                    this.killedAny = true;
                }

                int j = statHandler.getStat(Stats.KILLED_BY.getOrCreateStat(entityType));
                if (j == 0) {
                    this.killedByText = Text.translatable("stat_type.minecraft.killed_by.none", this.entityTypeName);
                    this.killedByAny = false;
                } else {
                    this.killedByText = Text.translatable("stat_type.minecraft.killed_by", this.entityTypeName, j);
                    this.killedByAny = true;
                }
            }

            @Override
            public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                context.drawTextWithShadow(textRenderer, this.entityTypeName, x + 2, y + 1, 16777215);
                context.drawTextWithShadow(textRenderer, this.killedText, x + 2 + 10, y + 1 + 9, this.killedAny ? 9474192 : 6316128);
                context.drawTextWithShadow(textRenderer, this.killedByText, x + 2 + 10, y + 1 + 9 * 2, this.killedByAny ? 9474192 : 6316128);
            }

            @Override
            public Text getNarration() {
                return Text.translatable("narrator.select", ScreenTexts.joinSentences(this.killedText, this.killedByText));
            }
        }
    }


    @Environment(EnvType.CLIENT)
    class GeneralStatsListWidget extends AlwaysSelectedEntryListWidget<GeneralStatsListWidget.Entry> {
        public GeneralStatsListWidget(MinecraftClient client) {
            super(client, MyStatScreen.this.width, MyStatScreen.this.height - 96, 32, 10);
            ObjectArrayList<Stat<Identifier>> objectArrayList = new ObjectArrayList<>(Stats.CUSTOM.iterator());
            objectArrayList.sort(Comparator.comparing(statx -> I18n.translate(getStatTranslationKey(statx))));

            for (Stat<Identifier> stat : objectArrayList) {
                addEntry(new GeneralStatsListWidget.Entry(stat));
            }
        }

        @Environment(EnvType.CLIENT)
        class Entry extends AlwaysSelectedEntryListWidget.Entry<GeneralStatsListWidget.Entry> {
            private final Stat<Identifier> stat;
            private final Text displayName;

            Entry(Stat<Identifier> stat) {
                this.stat = stat;
                this.displayName = Text.translatable(getStatTranslationKey(stat));
            }

            private String getFormatted() {
                return this.stat.format(statHandler.getStat(this.stat));
            }

            @Override
            public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                context.drawTextWithShadow(textRenderer, this.displayName, x + 2, y + 1, index % 2 == 0 ? 16777215 : 9474192);
                String string = this.getFormatted();
                context.drawTextWithShadow(
                        textRenderer, string, x + 2 + 213 - textRenderer.getWidth(string), y + 1, index % 2 == 0 ? 16777215 : 9474192
                );
            }

            @Override
            public Text getNarration() {
                return Text.translatable("narrator.select", Text.empty().append(this.displayName).append(ScreenTexts.SPACE).append(this.getFormatted()));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    class ItemStatsListWidget extends AlwaysSelectedEntryListWidget<ItemStatsListWidget.Entry>
    {
        protected final List<StatType<Block>> blockStatTypes;
        protected final List<StatType<Item>> itemStatTypes;
        private final Identifier[] headerIconTextures = new Identifier[]{
                new Identifier("statistics/block_mined"),
                new Identifier("statistics/item_broken"),
                new Identifier("statistics/item_crafted"),
                new Identifier("statistics/item_used"),
                new Identifier("statistics/item_picked_up"),
                new Identifier("statistics/item_dropped")
        };
        protected int selectedHeaderColumn = -1;
        protected final Comparator<ItemStatsListWidget.Entry> comparator = new ItemStatsListWidget.ItemComparator();
        @Nullable
        protected StatType<?> selectedStatType;
        protected int listOrder;

        public ItemStatsListWidget(MinecraftClient client)
        {
            super(client, MyStatScreen.this.width, MyStatScreen.this.height - 96, 32, 20);
            this.blockStatTypes = Lists.<StatType<Block>>newArrayList();
            this.blockStatTypes.add(Stats.MINED);
            this.itemStatTypes = Lists.<StatType<Item>>newArrayList(Stats.BROKEN, Stats.CRAFTED, Stats.USED, Stats.PICKED_UP, Stats.DROPPED);
            this.setRenderHeader(true, 20);
            Set<Item> set = Sets.newIdentityHashSet();

            for (Item item : Registries.ITEM)
            {
                boolean bl = false;

                for (StatType<Item> statType : this.itemStatTypes)
                {
                    if (statType.hasStat(item) && statHandler.getStat(statType.getOrCreateStat(item)) > 0)
                    {
                        bl = true;
                    }
                }

                if (bl)
                {
                    set.add(item);
                }
            }

            for (Block block : Registries.BLOCK)
            {
                boolean bl = false;

                for (StatType<Block> statTypex : this.blockStatTypes)
                {
                    if (statTypex.hasStat(block) && statHandler.getStat(statTypex.getOrCreateStat(block)) > 0)
                    {
                        bl = true;
                    }
                }

                if (bl)
                {
                    set.add(block.asItem());
                }
            }

            set.remove(Items.AIR);

            for (Item item : set)
            {
                this.addEntry(new ItemStatsListWidget.Entry(item));
            }
        }

        @Override
        protected void renderHeader(DrawContext context, int x, int y) {
            if (!this.client.mouse.wasLeftButtonClicked()) {
                this.selectedHeaderColumn = -1;
            }

            for (int i = 0; i < this.headerIconTextures.length; i++) {
//                Identifier identifier = this.selectedHeaderColumn == i ? SLOT_TEXTURE : HEADER_TEXTURE;
//                renderIcon(context, x + getColumnX(i) - 18, y + 1, identifier);
            }

            if (selectedStatType != null) {
                int i = getColumnX(this.getHeaderIndex(this.selectedStatType)) - 36;
                Identifier identifier = this.listOrder == 1 ? SORT_UP_TEXTURE : SORT_DOWN_TEXTURE;
                renderIcon(context, x + i, y + 1, identifier);
            }

            for (int i = 0; i < headerIconTextures.length; i++) {
                int j = selectedHeaderColumn == i ? 1 : 0;
                renderIcon(context, x + getColumnX(i) - 18 + j, y + 1 + j, headerIconTextures[i]);
            }
        }

        @Override
        public int getRowWidth() {
            return 375;
        }

        @Override
        protected int getScrollbarPositionX() {
            return this.width / 2 + 140;
        }

        @Override
        protected boolean clickedHeader(int x, int y) {
            this.selectedHeaderColumn = -1;

            for (int i = 0; i < this.headerIconTextures.length; i++) {
                int j = x - getColumnX(i);
                if (j >= -36 && j <= 0) {
                    this.selectedHeaderColumn = i;
                    break;
                }
            }

            if (this.selectedHeaderColumn >= 0) {
                this.selectStatType(this.getStatType(this.selectedHeaderColumn));
                this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            } else {
                return super.clickedHeader(x, y);
            }
        }

        private StatType<?> getStatType(int headerColumn) {
            return headerColumn < this.blockStatTypes.size()
                    ? (StatType)this.blockStatTypes.get(headerColumn)
                    : (StatType)this.itemStatTypes.get(headerColumn - this.blockStatTypes.size());
        }

        private int getHeaderIndex(StatType<?> statType) {
            int i = this.blockStatTypes.indexOf(statType);
            if (i >= 0) {
                return i;
            } else {
                int j = this.itemStatTypes.indexOf(statType);
                return j >= 0 ? j + this.blockStatTypes.size() : -1;
            }
        }

        @Override
        protected void renderDecorations(DrawContext context, int mouseX, int mouseY) {
            if (mouseY >= this.getY() && mouseY <= this.getBottom()) {
                ItemStatsListWidget.Entry entry = this.getHoveredEntry();
                int i = (this.width - this.getRowWidth()) / 2;
                if (entry != null) {
                    if (mouseX < i + 40 || mouseX > i + 40 + 20) {
                        return;
                    }

                    Item item = entry.getItem();
                    context.drawTooltip(textRenderer, this.getText(item), mouseX, mouseY);
                } else {
                    Text text = null;
                    int j = mouseX - i;

                    for (int k = 0; k < this.headerIconTextures.length; k++) {
                        int l = getColumnX(k);
                        if (j >= l - 18 && j <= l) {
                            text = this.getStatType(k).getName();
                            break;
                        }
                    }

                    if (text != null) {
                        context.drawTooltip(textRenderer, text, mouseX, mouseY);
                    }
                }
            }
        }

        protected Text getText(Item item) {
            return item.getName();
        }

        protected void selectStatType(StatType<?> statType) {
            if (statType != this.selectedStatType) {
                this.selectedStatType = statType;
                this.listOrder = -1;
            } else if (this.listOrder == -1) {
                this.listOrder = 1;
            } else {
                this.selectedStatType = null;
                this.listOrder = 0;
            }

            this.children().sort(this.comparator);
        }

        @Environment(EnvType.CLIENT)
        class Entry extends AlwaysSelectedEntryListWidget.Entry<ItemStatsListWidget.Entry> {
            private final Item item;

            Entry(Item item) {
                this.item = item;
            }

            public Item getItem() {
                return this.item;
            }

            @Override
            public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                renderStatItem(context, x + 40, y, this.item);

                for (int i = 0; i < itemStats.blockStatTypes.size(); i++) {
                    Stat<Block> stat;
                    if (this.item instanceof BlockItem) {
                        stat = ((StatType)itemStats.blockStatTypes.get(i)).getOrCreateStat(((BlockItem)this.item).getBlock());
                    } else {
                        stat = null;
                    }

                    this.render(context, stat, x + getColumnX(i), y, index % 2 == 0);
                }

                for (int i = 0; i < itemStats.itemStatTypes.size(); i++) {
                    this.render(
                            context,
                            ((StatType)itemStats.itemStatTypes.get(i)).getOrCreateStat(this.item),
                            x + getColumnX(i + itemStats.blockStatTypes.size()),
                            y,
                            index % 2 == 0
                    );
                }
            }

            protected void render(DrawContext context, @Nullable Stat<?> stat, int x, int y, boolean white) {
                Text text = (Text)(stat == null ? NONE_TEXT : Text.literal(stat.format(statHandler.getStat(stat))));
                context.drawTextWithShadow(textRenderer, text, x - textRenderer.getWidth(text), y + 5, white ? 16777215 : 9474192);
            }

            @Override
            public Text getNarration() {
                return Text.translatable("narrator.select", this.item.getName());
            }
        }

        @Environment(EnvType.CLIENT)
        class ItemComparator implements Comparator<ItemStatsListWidget.Entry> {
            public int compare(ItemStatsListWidget.Entry entry, ItemStatsListWidget.Entry entry2) {
                Item item = entry.getItem();
                Item item2 = entry2.getItem();
                int i;
                int j;
                if (ItemStatsListWidget.this.selectedStatType == null) {
                    i = 0;
                    j = 0;
                } else if (ItemStatsListWidget.this.blockStatTypes.contains(ItemStatsListWidget.this.selectedStatType)) {
                    StatType<Block> statType = (StatType<Block>) ItemStatsListWidget.this.selectedStatType;
                    i = item instanceof BlockItem ? statHandler.getStat(statType, ((BlockItem)item).getBlock()) : -1;
                    j = item2 instanceof BlockItem ? statHandler.getStat(statType, ((BlockItem)item2).getBlock()) : -1;
                } else {
                    StatType<Item> statType = (StatType<Item>) ItemStatsListWidget.this.selectedStatType;
                    i = statHandler.getStat(statType, item);
                    j = statHandler.getStat(statType, item2);
                }

                return i == j
                        ? ItemStatsListWidget.this.listOrder * Integer.compare(Item.getRawId(item), Item.getRawId(item2))
                        : ItemStatsListWidget.this.listOrder * Integer.compare(i, j);
            }
        }
    }
}
