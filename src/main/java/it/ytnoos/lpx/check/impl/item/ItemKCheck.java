package it.ytnoos.lpx.check.impl.item;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ChargedProjectiles;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemAdventurePredicate;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemAdventurePredicate.BlockPredicate;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemContainerContents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemProfile;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemProfile.Property;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemTool;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.WritableBookContent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.WrittenBookContent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Filterable;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent.Action;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;
import it.ytnoos.lpx.lib.kyori.examination.Examiner;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;

public class ItemKCheck extends AbstractCheck<ItemStackDecodeEvent> {

    private int limit;

    private int limit2;

    private int limit3;

    private int limit4;

    private int limit5;

    private int limit6;

    private int limit7;

    private int limit8;

    private int limit9;

    private int limit10;

    public ItemKCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Integer value = Integer.valueOf((byte) 96);
        String text = (String) "max-container-items";
        String text2 = text;
        this.limit = this.calculateLimit(text2, ((int) value));
        Integer value2 = Integer.valueOf((short) 256);
        String text3 = "max-total-nested-items";
        text = text3;
        String text4 = text;
        this.limit2 = this.calculateLimit(text4, ((int) value2));
        Integer value3 = Integer.valueOf((byte) 16);
        String text5 = "max-profile-properties";
        text = text5;
        String text6 = text;
        this.limit3 = this.calculateLimit(text6, ((int) value3));
        Integer value4 = Integer.valueOf((short) 8192);
        String text7 = "max-profile-total-bytes";
        text = text7;
        String text8 = text;
        this.limit4 = this.calculateLimit(text8, ((int) value4));
        Integer value5 = Integer.valueOf((byte) 16);
        String text9 = "max-charged-projectiles";
        text = text9;
        String text10 = text;
        this.limit5 = this.calculateLimit(text10, ((int) value5));
        Integer value6 = Integer.valueOf((byte) 96);
        String text11 = "max-tool-rules";
        text = text11;
        String text12 = text;
        this.limit6 = this.calculateLimit(text12, ((int) value6));
        Integer value7 = Integer.valueOf((short) 192);
        String text13 = "max-can-break-predicates";
        text = text13;
        String text14 = text;
        this.limit7 = this.calculateLimit(text14, ((int) value7));
        Integer value8 = Integer.valueOf((short) 256);
        String text15 = "max-can-break-properties";
        text = text15;
        String text16 = text;
        this.limit8 = this.calculateLimit(text16, ((int) value8));
        Integer value9 = Integer.valueOf((byte) 100);
        String text17 = "max-book-pages";
        text = text17;
        String text18 = text;
        this.limit9 = this.calculateLimit(text18, ((int) value9));
        Integer value10 = Integer.valueOf((short) 3072);
        String text19 = "max-book-page-bytes";
        text = text19;
        String text20 = text;
        this.limit10 = this.calculateLimit(text20, ((int) value10));
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer, ItemStackDecodeEvent itemStackDecodeEvent) {
        if (!protocolPlayer.usesComponents()) {
            return CheckResult.ALLOW;
        }
        ItemStack itemStack = itemStackDecodeEvent.getItemStack();
        ItemKCheck.Counter counter = new ItemKCheck.Counter();
        ItemStack itemStack2 = itemStack;
        itemStack = itemStack2;
        ItemStack itemStack3 = itemStack;
        return this.check(itemStack3, counter, itemStackDecodeEvent);
    }

    private CheckResult check(
            ItemStack itemStack,
            ItemKCheck.Counter counter,
            ItemStackDecodeEvent itemStackDecodeEvent) {
        if (itemStack != null && !itemStack.isEmpty()) {
            ItemStack itemStack2 = itemStack;
            ItemStack itemStack3 = itemStack2;
            CheckResult checkResult = this.check(itemStack3, itemStackDecodeEvent);
            CheckResult checkResult2 = checkResult;
            if (checkResult == CheckResult.DENY) {
                return checkResult2;
            }
            itemStack2 = itemStack;
            ItemStack itemStack4 = itemStack2;
            CheckResult checkResult3 = this.check2(itemStack4, itemStackDecodeEvent);
            checkResult2 = checkResult3;
            if (checkResult3 == CheckResult.DENY) {
                return checkResult2;
            }
            itemStack2 = itemStack;
            ItemKCheck.Counter counter2 = counter;
            ItemKCheck.Counter counter3 = counter2;
            ItemStack itemStack5 = itemStack2;
            CheckResult checkResult4 = this.check2(itemStack5, counter3, itemStackDecodeEvent);
            checkResult2 = checkResult4;
            if (checkResult4 == CheckResult.DENY) {
                return checkResult2;
            }
            itemStack2 = itemStack;
            ItemStack itemStack6 = itemStack2;
            CheckResult checkResult5 = this.check3(itemStack6, itemStackDecodeEvent);
            checkResult2 = checkResult5;
            if (checkResult5 == CheckResult.DENY) {
                return checkResult2;
            }
            itemStack2 = itemStack;
            ItemStack itemStack7 = itemStack2;
            CheckResult checkResult6 = this.check4(itemStack7, itemStackDecodeEvent);
            checkResult2 = checkResult6;
            if (checkResult6 == CheckResult.DENY) {
                return checkResult2;
            }
            itemStack2 = itemStack;
            counter2 = counter;
            ItemKCheck.Counter counter4 = counter2;
            ItemStack itemStack8 = itemStack2;
            return this.check3(itemStack8, counter4, itemStackDecodeEvent);
        } else {
            return CheckResult.ALLOW;
        }
    }

    private CheckResult check(ItemStack itemStack, ItemStackDecodeEvent itemStackDecodeEvent) {
        WritableBookContent writableBookContent =
                (WritableBookContent)
                        itemStack.getComponentOr(ComponentTypes.WRITABLE_BOOK_CONTENT, null);
        if (writableBookContent != null) {
            List<Filterable<String>> pages = writableBookContent.getPages();
            if (pages.size() > this.limit9) {
                FlagInfo[] flagInfos =
                        new FlagInfo[] {
                            FlagInfo.of("identifier", "writable-book-pages"), null, null
                        };
                flagInfos[1] = FlagInfo.of("pages", pages.size());
                flagInfos[2] = FlagInfo.of("max-pages", this.limit9);
                this.flagViolation(itemStackDecodeEvent, flagInfos);
                return CheckResult.DENY;
            }
            for (int index = 0; index < pages.size(); index++) {
                Filterable filterable = (Filterable) pages.get(index);
                int value = index;
                Integer value2 = ((Integer) value);
                Filterable filterable2 = filterable;
                filterable = filterable2;
                int value3 = ((int) value2);
                Filterable filterable3 = filterable;
                CheckResult checkResult =
                        this.check(
                                (String) "writable-book-page",
                                filterable3,
                                value3,
                                itemStackDecodeEvent);
                if (checkResult == CheckResult.DENY) {
                    return checkResult;
                }
            }
        }
        WrittenBookContent writtenBookContent =
                (WrittenBookContent)
                        itemStack.getComponentOr(ComponentTypes.WRITTEN_BOOK_CONTENT, null);
        if (writtenBookContent == null) {
            return CheckResult.ALLOW;
        }
        List<Filterable<Component>> pages2 = writtenBookContent.getPages();
        if (pages2.size() > this.limit9) {
            FlagInfo[] flagInfos2 =
                    new FlagInfo[] {FlagInfo.of("identifier", "written-book-pages"), null, null};
            flagInfos2[1] = FlagInfo.of("pages", pages2.size());
            flagInfos2[2] = FlagInfo.of("max-pages", this.limit9);
            this.flagViolation(itemStackDecodeEvent, flagInfos2);
            return CheckResult.DENY;
        }
        for (int index2 = 0; index2 < pages2.size(); index2++) {
            Filterable filterable4 = (Filterable) pages2.get(index2);
            int value4 = index2;
            Integer value5 = ((Integer) value4);
            Filterable filterable5 = filterable4;
            filterable4 = filterable5;
            int value6 = ((int) value5);
            Filterable filterable6 = filterable4;
            CheckResult checkResult2 =
                    this.check2(
                            (String) "written-book-page",
                            filterable6,
                            value6,
                            itemStackDecodeEvent);
            if (checkResult2 == CheckResult.DENY) {
                return checkResult2;
            }
        }
        return CheckResult.ALLOW;
    }

    private CheckResult check2(ItemStack itemStack, ItemStackDecodeEvent itemStackDecodeEvent) {
        ItemProfile itemProfile =
                (ItemProfile) itemStack.getComponentOr(ComponentTypes.PROFILE, null);
        if (itemProfile == null) {
            return CheckResult.ALLOW;
        }
        List<Property> properties = itemProfile.getProperties();
        if (properties.size() > this.limit3) {
            FlagInfo[] flagInfos =
                    new FlagInfo[] {FlagInfo.of("identifier", "profile-properties"), null, null};
            flagInfos[1] = FlagInfo.of("properties", properties.size());
            flagInfos[2] = FlagInfo.of("max-properties", this.limit3);
            this.flagViolation(itemStackDecodeEvent, flagInfos);
            return CheckResult.DENY;
        }
        int value = 0;
        for (Property property : properties) {
            String name = property.getName();
            int value2 = value + this.calculateLimit(name);
            String value3 = property.getValue();
            value2 += this.calculateLimit(value3);
            String signature = property.getSignature();
            value = value2 + this.calculateLimit(signature);
        }
        if (value > this.limit4) {
            FlagInfo[] flagInfos2 =
                    new FlagInfo[] {FlagInfo.of("identifier", "profile-bytes"), null, null};
            flagInfos2[1] = FlagInfo.of("bytes", value);
            flagInfos2[2] = FlagInfo.of("max-bytes", this.limit4);
            this.flagViolation(itemStackDecodeEvent, flagInfos2);
            return CheckResult.DENY;
        } else {
            return CheckResult.ALLOW;
        }
    }

    private CheckResult check2(
            ItemStack itemStack,
            ItemKCheck.Counter counter,
            ItemStackDecodeEvent itemStackDecodeEvent) {
        ChargedProjectiles chargedProjectiles =
                (ChargedProjectiles)
                        itemStack.getComponentOr(ComponentTypes.CHARGED_PROJECTILES, null);
        if (chargedProjectiles == null) {
            return CheckResult.ALLOW;
        }
        List<ItemStack> items = chargedProjectiles.getItems();
        if (items.size() > this.limit5) {
            FlagInfo[] flagInfos =
                    new FlagInfo[] {FlagInfo.of("identifier", "charged-projectiles"), null, null};
            flagInfos[1] = FlagInfo.of("projectiles", items.size());
            flagInfos[2] = FlagInfo.of("max-projectiles", this.limit5);
            this.flagViolation(itemStackDecodeEvent, flagInfos);
            return CheckResult.DENY;
        }
        int size2 = items.size();
        Integer value = ((Integer) size2);
        int value2 = ((int) value);
        CheckResult checkResult =
                this.check(value2, (String) "charged-projectiles", counter, itemStackDecodeEvent);
        CheckResult checkResult2 = checkResult;
        if (checkResult == CheckResult.DENY) {
            return checkResult2;
        }
        for (ItemStack itemStack2 : items) {
            CheckResult checkResult3 = this.check(itemStack2, counter, itemStackDecodeEvent);
            checkResult2 = checkResult3;
            if (checkResult3 == CheckResult.DENY) {
                return checkResult2;
            }
        }
        return CheckResult.ALLOW;
    }

    private CheckResult check3(ItemStack itemStack, ItemStackDecodeEvent itemStackDecodeEvent) {
        ItemTool itemTool = (ItemTool) itemStack.getComponentOr(ComponentTypes.TOOL, null);
        if (itemTool == null) {
            return CheckResult.ALLOW;
        }
        int size2 = itemTool.getRules().size();
        if (size2 <= this.limit6) {
            return CheckResult.ALLOW;
        }
        FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("identifier", "tool-rules"), null, null};
        flagInfos[1] = FlagInfo.of("rules", size2);
        flagInfos[2] = FlagInfo.of("max-rules", this.limit6);
        this.flagViolation(itemStackDecodeEvent, flagInfos);
        return CheckResult.DENY;
    }

    private CheckResult check4(ItemStack itemStack, ItemStackDecodeEvent itemStackDecodeEvent) {
        ItemAdventurePredicate itemAdventurePredicate =
                (ItemAdventurePredicate) itemStack.getComponentOr(ComponentTypes.CAN_BREAK, null);
        if (itemAdventurePredicate == null) {
            return CheckResult.ALLOW;
        }
        List<BlockPredicate> predicates = itemAdventurePredicate.getPredicates();
        if (predicates.size() > this.limit7) {
            FlagInfo[] flagInfos =
                    new FlagInfo[] {FlagInfo.of("identifier", "can-break-predicates"), null, null};
            flagInfos[1] = FlagInfo.of("predicates", predicates.size());
            flagInfos[2] = FlagInfo.of("max-predicates", this.limit7);
            this.flagViolation(itemStackDecodeEvent, flagInfos);
            return CheckResult.DENY;
        }
        int value = 0;
        Iterator<BlockPredicate> iterator2 = predicates.iterator();
        while (iterator2.hasNext()) {
            List<ItemAdventurePredicate.PropertyMatcher> properties =
                    ((BlockPredicate) iterator2.next()).getProperties();
            if (properties != null) {
                value += properties.size();
            }
        }
        if (value <= this.limit8) {
            return CheckResult.ALLOW;
        }
        FlagInfo[] flagInfos2 =
                new FlagInfo[] {FlagInfo.of("identifier", "can-break-properties"), null, null};
        flagInfos2[1] = FlagInfo.of("properties", value);
        flagInfos2[2] = FlagInfo.of("max-properties", this.limit8);
        this.flagViolation(itemStackDecodeEvent, flagInfos2);
        return CheckResult.DENY;
    }

    private CheckResult check3(
            ItemStack itemStack,
            ItemKCheck.Counter counter,
            ItemStackDecodeEvent itemStackDecodeEvent) {
        ItemContainerContents itemContainerContents =
                (ItemContainerContents) itemStack.getComponentOr(ComponentTypes.CONTAINER, null);
        if (itemContainerContents == null) {
            return CheckResult.ALLOW;
        }
        List<ItemStack> items = itemContainerContents.getItems();
        if (items.size() > this.limit) {
            FlagInfo[] flagInfos =
                    new FlagInfo[] {FlagInfo.of("identifier", "container-items"), null, null};
            flagInfos[1] = FlagInfo.of("items", items.size());
            flagInfos[2] = FlagInfo.of("max-items", this.limit);
            this.flagViolation(itemStackDecodeEvent, flagInfos);
            return CheckResult.DENY;
        }
        int size2 = items.size();
        Integer value = ((Integer) size2);
        int value2 = ((int) value);
        CheckResult checkResult =
                this.check(value2, (String) "container-items", counter, itemStackDecodeEvent);
        CheckResult checkResult2 = checkResult;
        if (checkResult == CheckResult.DENY) {
            return checkResult2;
        }
        for (ItemStack itemStack2 : items) {
            CheckResult checkResult3 = this.check(itemStack2, counter, itemStackDecodeEvent);
            checkResult2 = checkResult3;
            if (checkResult3 == CheckResult.DENY) {
                return checkResult2;
            }
        }
        return CheckResult.ALLOW;
    }

    private CheckResult check(
            int value,
            Object result,
            ItemKCheck.Counter counter,
            ItemStackDecodeEvent itemStackDecodeEvent) {
        if (value <= 0) {
            return CheckResult.ALLOW;
        }
        counter.limit += value;
        if (counter.limit <= this.limit2) {
            return CheckResult.ALLOW;
        }
        FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("identifier", result), null, null};
        flagInfos[1] = FlagInfo.of("total-nested-items", counter.limit);
        flagInfos[2] = FlagInfo.of("max-total-nested-items", this.limit2);
        this.flagViolation(itemStackDecodeEvent, flagInfos);
        return CheckResult.DENY;
    }

    private CheckResult check(
            String text,
            Filterable filterable,
            int value,
            ItemStackDecodeEvent itemStackDecodeEvent) {
        Object raw = filterable.getRaw();
        String text2 = (String) raw;
        int value2 = this.calculateLimit(text2);
        if (value2 > this.limit10) {
            FlagInfo[] flagInfos = new FlagInfo[4];
            flagInfos[0] = FlagInfo.of("identifier", text);
            flagInfos[1] = FlagInfo.of("page-index", value);
            flagInfos[2] = FlagInfo.of("page-bytes", value2);
            flagInfos[3] = FlagInfo.of("max-page-bytes", this.limit10);
            this.flagViolation(itemStackDecodeEvent, flagInfos);
            return CheckResult.DENY;
        }
        String text3 = (String) filterable.getFiltered();
        if (text3 == null) {
            return CheckResult.ALLOW;
        }
        int value3 = this.calculateLimit(text3);
        if (value3 <= this.limit10) {
            return CheckResult.ALLOW;
        }
        FlagInfo[] flagInfos2 = new FlagInfo[4];
        flagInfos2[0] = FlagInfo.of("identifier", text + "-filtered");
        flagInfos2[1] = FlagInfo.of("page-index", value);
        flagInfos2[2] = FlagInfo.of("page-bytes", value3);
        flagInfos2[3] = FlagInfo.of("max-page-bytes", this.limit10);
        this.flagViolation(itemStackDecodeEvent, flagInfos2);
        return CheckResult.DENY;
    }

    private CheckResult check2(
            String text,
            Filterable filterable,
            int value,
            ItemStackDecodeEvent itemStackDecodeEvent) {
        Object raw = filterable.getRaw();
        Component component = (Component) raw;
        int value2 = this.calculateLimit(component);
        if (value2 > this.limit10) {
            FlagInfo[] flagInfos = new FlagInfo[4];
            flagInfos[0] = FlagInfo.of("identifier", text);
            flagInfos[1] = FlagInfo.of("page-index", value);
            flagInfos[2] = FlagInfo.of("page-bytes", value2);
            flagInfos[3] = FlagInfo.of("max-page-bytes", this.limit10);
            this.flagViolation(itemStackDecodeEvent, flagInfos);
            return CheckResult.DENY;
        }
        Component component2 = (Component) filterable.getFiltered();
        if (component2 == null) {
            return CheckResult.ALLOW;
        }
        int value3 = this.calculateLimit(component2);
        if (value3 <= this.limit10) {
            return CheckResult.ALLOW;
        }
        FlagInfo[] flagInfos2 = new FlagInfo[4];
        flagInfos2[0] = FlagInfo.of("identifier", text + "-filtered");
        flagInfos2[1] = FlagInfo.of("page-index", value);
        flagInfos2[2] = FlagInfo.of("page-bytes", value3);
        flagInfos2[3] = FlagInfo.of("max-page-bytes", this.limit10);
        this.flagViolation(itemStackDecodeEvent, flagInfos2);
        return CheckResult.DENY;
    }

    private int calculateLimit(Component component) {
        int value = this.limit10 + 1;
        Integer value2 = ((Integer) value);
        return this.calculateLimit(component, ((int) value2));
    }

    private int calculateLimit(Object result, int value2) {
        if (result == null || value2 <= 0) {
            return 0;
        } else if (result instanceof String text) {
            String text2 = text;
            return Math.min(value2, this.calculateLimit(text2));
        } else if (result instanceof Key key2) {
            String text3 = key2.asString();
            return Math.min(value2, this.calculateLimit(text3));
        } else if (result instanceof TextColor textColor) {
            String text4 = textColor.asHexString();
            return Math.min(value2, this.calculateLimit(text4));
        } else if (result instanceof ClickEvent clickEvent) {
            Action action2 = clickEvent.action();
            Integer value3 = ((Integer) value2);
            int value4 = this.calculateLimit(action2, ((int) value3));
            if (value4 >= value2) {
                return value2;
            }
            String text5 = clickEvent.value();
            int value5 = value2 - value4;
            Integer value6 = ((Integer) value5);
            return value4 + this.calculateLimit(text5, ((int) value6));
        } else if (result instanceof HoverEvent hoverEvent) {
            HoverEvent.Action action3 = hoverEvent.action();
            Integer value7 = ((Integer) value2);
            int value8 = this.calculateLimit(action3, ((int) value7));
            if (value8 >= value2) {
                return value2;
            }
            Object result2 = hoverEvent.value();
            int value9 = value2 - value8;
            Integer value10 = ((Integer) value9);
            return value8 + this.calculateLimit(result2, ((int) value10));
        } else if (result instanceof Optional optional) {
            Object result3 = optional.orElse(null);
            Integer value11 = ((Integer) value2);
            return this.calculateLimit(result3, ((int) value11));
        } else if (result instanceof Map entries) {
            int value12 = 0;
            for (Entry<Object, Object> entry :
                    ((Iterable<Entry<Object, Object>>) ((Iterable<?>) entries.entrySet()))) {
                Object key = entry.getKey();
                int value13 = value2 - value12;
                Integer value14 = ((Integer) value13);
                int value15 = value12 + this.calculateLimit(key, ((int) value14));
                value12 = value15;
                if (value15 >= value2) {
                    return value2;
                }
                Object value16 = entry.getValue();
                int value17 = value2 - value12;
                Integer value18 = ((Integer) value17);
                int value19 = value12 + this.calculateLimit(value16, ((int) value18));
                value12 = value19;
                if (value19 >= value2) {
                    return value2;
                }
            }
            return value12;
        } else if (result instanceof Iterable iterable) {
            int value20 = 0;
            for (Object result4 : iterable) {
                int value21 = value2 - value20;
                Integer value22 = ((Integer) value21);
                int value23 = value20 + this.calculateLimit(result4, ((int) value22));
                value20 = value23;
                if (value23 >= value2) {
                    return value2;
                }
            }
            return value20;
        } else if (result instanceof Examinable examinable) {
            int value24 = 0;
            for (ExaminableProperty examinableProperty :
                    examinable.examinableProperties().toList()) {
                String text6 = examinableProperty.name();
                int value25 = value2 - value24;
                Integer value26 = ((Integer) value25);
                int value27 = value24 + this.calculateLimit(text6, ((int) value26));
                value24 = value27;
                if (value27 >= value2) {
                    return value2;
                }
                int value28 =
                        value24
                                + Math.min(
                                        value2 - value24,
                                        (Integer)
                                                examinableProperty.examine(
                                                        ItemKCheck.ComponentSizeExaminer.INSTANCE));
                value24 = value28;
                if (value28 >= value2) {
                    return value2;
                }
            }
            return value24;
        } else if (result.getClass().isArray()) {
            int value29 = 0;
            int length = Array.getLength(result);
            for (int index = 0; index < length; index++) {
                Object result5 = Array.get(result, index);
                int value30 = value2 - value29;
                Integer value31 = ((Integer) value30);
                int value32 = value29 + this.calculateLimit(result5, ((int) value31));
                value29 = value32;
                if (value32 >= value2) {
                    return value2;
                }
            }
            return value29;
        } else if (result instanceof Enum enumValue) {
            String text7 = enumValue.name();
            return Math.min(value2, this.calculateLimit(text7));
        } else {
            if (!(result instanceof Number)
                    && !(result instanceof Boolean)
                    && !(result instanceof Character)) {
                return 0;
            }
            String text8 = String.valueOf(result);
            return Math.min(value2, this.calculateLimit(text8));
        }
    }

    private int calculateLimit(String text) {
        return text == null ? 0 : text.getBytes(StandardCharsets.UTF_8).length;
    }

    public boolean isPreVia() {
        return true;
    }

    static final class ComponentSizeExaminer implements Examiner<Integer> {

        private static final ItemKCheck.ComponentSizeExaminer INSTANCE;

        private ComponentSizeExaminer() {}

        public Integer examine(String text, Stream stream) {
            int value = calculateLimit(text);
            for (ExaminableProperty examinableProperty :
                    (Iterable<ExaminableProperty>) ((Iterable) stream.toList())) {
                String text2 = examinableProperty.name();
                value = value + calculateLimit(text2) + (Integer) examinableProperty.examine(this);
            }
            return value;
        }

        public Integer examine(Object result) {
            if (result == null) {
                return 0;
            } else if (result instanceof String text) {
                String text2 = text;
                return calculateLimit(text2);
            } else if (result instanceof Key) {
                String text3 = ((Key) result).asString();
                return calculateLimit(text3);
            } else if (result instanceof TextColor) {
                String text4 = ((TextColor) result).asHexString();
                return calculateLimit(text4);
            } else if (result instanceof Enum) {
                String text5 = ((Enum) result).name();
                return calculateLimit(text5);
            } else if (result instanceof Number
                    || result instanceof Boolean
                    || result instanceof Character) {
                String text6 = String.valueOf(result);
                return calculateLimit(text6);
            } else if (result instanceof ClickEvent clickEvent) {
                Action action2 = clickEvent.action();
                int value2 = this.examine(action2);
                String text7 = clickEvent.value();
                return value2 + this.examine(text7);
            } else if (result instanceof HoverEvent hoverEvent) {
                HoverEvent.Action action3 = hoverEvent.action();
                int value3 = this.examine(action3);
                Object result2 = hoverEvent.value();
                return value3 + this.examine(result2);
            } else if (result instanceof Optional optional) {
                Object result3 = optional.orElse(null);
                return this.examine(result3);
            } else if (result instanceof Examinable examinable) {
                return (Integer) this.examine(examinable);
            } else if (result instanceof Map entries) {
                int value4 = 0;
                for (Entry<Object, Object> entry :
                        ((Iterable<Entry<Object, Object>>) ((Iterable<?>) entries.entrySet()))) {
                    Object key = entry.getKey();
                    int value5 = value4 + this.examine(key);
                    Object value6 = entry.getValue();
                    value4 = value5 + this.examine(value6);
                }
                return value4;
            } else if (result instanceof Iterable iterable) {
                int value7 = 0;
                for (Object result4 : iterable) {
                    value7 += this.examine(result4);
                }
                return value7;
            } else {
                if (!result.getClass().isArray()) {
                    return 0;
                }
                int value8 = 0;
                int length = Array.getLength(result);
                for (int index = 0; index < length; index++) {
                    Object result5 = Array.get(result, index);
                    value8 += this.examine(result5);
                }
                return value8;
            }
        }

        public Integer examine(boolean matches) {
            String text = String.valueOf(matches);
            return calculateLimit(text);
        }

        public Integer examine(boolean[] matchess) {
            int value = matchess == null ? 0 : matchess.length;
            Integer value2 = ((Integer) value);
            return calculateLimit(((int) value2));
        }

        public Integer examine(byte value) {
            String text = String.valueOf(value);
            return calculateLimit(text);
        }

        public Integer examine(byte[] values) {
            int value = values == null ? 0 : values.length;
            Integer value2 = ((Integer) value);
            return calculateLimit(((int) value2));
        }

        public Integer examine(char value) {
            String text = String.valueOf((char) value);
            return calculateLimit(text);
        }

        public Integer examine(char[] values) {
            int value;
            if (values == null) {
                value = 0;
            } else {
                String text = new String(values);
                value = calculateLimit(text);
            }
            return value;
        }

        public Integer examine(double value) {
            String text = String.valueOf(value);
            return calculateLimit(text);
        }

        public Integer examine(double[] values) {
            int value = values == null ? 0 : values.length;
            Integer value2 = ((Integer) value);
            return calculateLimit(((int) value2));
        }

        public Integer examine(float value) {
            String text = String.valueOf(value);
            return calculateLimit(text);
        }

        public Integer examine(float[] values) {
            int value = values == null ? 0 : values.length;
            Integer value2 = ((Integer) value);
            return calculateLimit(((int) value2));
        }

        public Integer examine(int value) {
            String text = String.valueOf(value);
            return calculateLimit(text);
        }

        public Integer examine(int[] values) {
            int value = values == null ? 0 : values.length;
            Integer value2 = ((Integer) value);
            return calculateLimit(((int) value2));
        }

        public Integer examine(long value) {
            String text = String.valueOf(value);
            return calculateLimit(text);
        }

        public Integer examine(long[] values) {
            int value = values == null ? 0 : values.length;
            Integer value2 = ((Integer) value);
            return calculateLimit(((int) value2));
        }

        public Integer examine(short value) {
            String text = String.valueOf(value);
            return calculateLimit(text);
        }

        public Integer examine(short[] values) {
            int value = values == null ? 0 : values.length;
            Integer value2 = ((Integer) value);
            return calculateLimit(((int) value2));
        }

        public Integer examine(String text) {
            return calculateLimit(text);
        }

        private static int calculateLimit(int value) {
            String text = String.valueOf(value);
            return calculateLimit(text);
        }

        private static int calculateLimit(String text) {
            return text == null ? 0 : text.getBytes(StandardCharsets.UTF_8).length;
        }

        static {
            ItemKCheck.ComponentSizeExaminer componentSizeExaminer =
                    new ItemKCheck.ComponentSizeExaminer();
            INSTANCE = componentSizeExaminer;
        }
    }

    static class Counter {

        private int limit;

        private Counter() {}
    }
}
