package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.plain;

import it.ytnoos.lpx.lib.kyori.adventure.text.KeybindComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslatableComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.flattener.ComponentFlattener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

@Deprecated
final class PlainComponentSerializerImpl {

    @Deprecated static final PlainComponentSerializer INSTANCE = new PlainComponentSerializer();

    private PlainComponentSerializerImpl() {}

    @Deprecated
    static PlainTextComponentSerializer createRealSerializerFromLegacyFunctions(
            @Nullable Function<KeybindComponent, String> function,
            @Nullable Function<TranslatableComponent, String> function2) {
        if (function == null && function2 == null) {
            return PlainTextComponentSerializer.plainText();
        }
        ComponentFlattener.Builder builder2 = ComponentFlattener.basic().toBuilder();
        if (function != null) {
            builder2.mapper(KeybindComponent.class, function);
        }
        if (function2 != null) {
            builder2.mapper(TranslatableComponent.class, function2);
        }
        return PlainTextComponentSerializer.builder().flattener(builder2.build()).build();
    }

    @Deprecated
    static final class BuilderImpl implements PlainComponentSerializer.Builder {

        private final PlainTextComponentSerializer.Builder builder =
                PlainTextComponentSerializer.builder();

        @Deprecated
        BuilderImpl() {}

        @Deprecated
        BuilderImpl(PlainComponentSerializer plainComponentSerializer) {
            this.builder.flattener(
                    ((PlainTextComponentSerializerImpl) plainComponentSerializer.serializer)
                            .flattener);
        }

        @Override
        public PlainComponentSerializer.@NotNull Builder flattener(
                @NotNull ComponentFlattener componentFlattener) {
            this.builder.flattener(componentFlattener);
            return this;
        }

        @NotNull
        public PlainComponentSerializer build() {
            return new PlainComponentSerializer(this.builder.build());
        }
    }
}
