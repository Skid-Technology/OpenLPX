package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.standard;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class PrideTag extends GradientTag {

    static final TagResolver RESOLVER = TagResolver.resolver("pride", PrideTag::create);

    private static final Map<String, List<TextColor>> FLAGS;

    private final String flag;

    static Tag create(ArgumentQueue argumentQueue, Context context) {
        double value2 = 0.0;
        String text = "pride";
        if (argumentQueue.hasNext()) {
            String text2 = argumentQueue.pop().value().toLowerCase(Locale.ROOT);
            if (FLAGS.containsKey(text2)) {
                text = text2;
            } else if (!text2.isEmpty()) {
                try {
                    value2 = Double.parseDouble(text2);
                } catch (NumberFormatException numberFormatException) {
                    throw context.newException("Expected phase, got " + text2);
                }
                if (value2 < -1.0 || value2 > 1.0) {
                    throw context.newException(
                            String.format(
                                    "Gradient phase is out of range (%s). Must be in the range [-1.0, 1.0] (inclusive).",
                                    value2),
                            argumentQueue);
                }
            }
        }
        return new PrideTag(value2, FLAGS.get(text), text, context);
    }

    PrideTag(double value, @NotNull List<TextColor> values, @NotNull String flag, Context context) {
        super(value, values, context);
        this.flag = flag;
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("flag", this.flag),
                ExaminableProperty.of("phase", this.phase));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.flag, this.phase);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            PrideTag prideTag = (PrideTag) result;
            return this.phase == prideTag.phase && this.flag.equals(prideTag.flag);
        } else {
            return false;
        }
    }

    @NotNull
    private static List<TextColor> colors(int @NotNull ... value) {
        return Arrays.stream(value).mapToObj(TextColor::color).collect(Collectors.toList());
    }

    static {
        HashMap entries;
        HashMap entries2 = entries = new HashMap();
        int[] values = new int[6];
        values[0] = 15007744;
        values[1] = 16747776;
        values[2] = 16772608;
        values[3] = 164129;
        values[4] = 19711;
        values[5] = 7798920;
        entries2.put("pride", colors(values));
        values = new int[11];
        values[0] = 16777215;
        values[1] = 16756679;
        values[2] = 7591918;
        values[3] = 6371605;
        values[4] = 0;
        values[5] = 15007744;
        values[6] = 16747776;
        values[7] = 16772608;
        values[8] = 164129;
        values[9] = 19711;
        values[10] = 7798920;
        entries.put("progress", colors(values));
        entries.put("trans", colors(6017019, 16100281, 16777215, 16100281, 6017019));
        entries.put("bi", colors(14025328, 10178454, 14504));
        entries.put("pan", colors(16718989, 16766720, 1750015));
        entries.put("nb", colors(16577585, 16579836, 10312146, 2631720));
        entries.put("lesbian", colors(14034944, 16751446, 16777215, 13918886, 10748002));
        entries.put("ace", colors(0, 10790052, 16777215, 8454273));
        values = new int[7];
        values[0] = 0;
        values[1] = 12237498;
        values[2] = 16777215;
        values[3] = 12252292;
        values[4] = 16777215;
        values[5] = 12237498;
        values[6] = 0;
        entries.put("agender", colors(values));
        entries.put("demisexual", colors(0, 16777215, 7209073, 13882323));
        entries.put("genderqueer", colors(11894749, 16777215, 4817438));
        entries.put("genderfluid", colors(16676514, 16777215, 12522199, 0, 3161278));
        entries.put("intersex", colors(16766976, 7930538, 16766976));
        entries.put("aro", colors(3909440, 11064442, 16777215, 11250603, 0));
        values = new int[9];
        values[0] = 13461247;
        values[1] = 16737689;
        values[2] = 16646144;
        values[3] = 16685312;
        values[4] = 16776961;
        values[5] = 39168;
        values[6] = 39371;
        values[7] = 3473561;
        values[8] = 10027161;
        entries.put("baker", colors(values));
        values = new int[8];
        values[0] = 0;
        values[1] = 7884567;
        values[2] = 16646144;
        values[3] = 16616448;
        values[4] = 16770304;
        values[5] = 1154827;
        values[6] = 410803;
        values[7] = 12725980;
        entries.put("philly", colors(values));
        values = new int[9];
        values[0] = 0;
        values[1] = 10148330;
        values[2] = 41960;
        values[3] = 11920669;
        values[4] = 16777215;
        values[5] = 16763149;
        values[6] = 16541287;
        values[7] = 16690889;
        values[8] = 0;
        entries.put("queer", colors(values));
        values = new int[7];
        values[0] = 495216;
        values[1] = 2543274;
        values[2] = 10021057;
        values[3] = 16777215;
        values[4] = 8105442;
        values[5] = 5261771;
        values[6] = 4004472;
        entries.put("gay", colors(values));
        values = new int[7];
        values[0] = 12876192;
        values[1] = 15509195;
        values[2] = 14010344;
        values[3] = 16777215;
        values[4] = 14010344;
        values[5] = 10143720;
        values[6] = 7111631;
        entries.put("bigender", colors(values));
        values = new int[7];
        values[0] = 8355711;
        values[1] = 12829635;
        values[2] = 16514932;
        values[3] = 16777215;
        values[4] = 16514932;
        values[5] = 12829635;
        values[6] = 8355711;
        entries.put("demigender", colors(values));
        FLAGS = Collections.unmodifiableMap(entries);
    }
}
