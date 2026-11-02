package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.plain;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.flattener.ComponentFlattener;
import it.ytnoos.lpx.lib.kyori.adventure.util.Services;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

final class PlainTextComponentSerializerImpl implements PlainTextComponentSerializer {

    private static final ComponentFlattener DEFAULT_FLATTENER =
            ComponentFlattener.basic().toBuilder()
                    .unknownMapper(
                            result -> {
                                throw new UnsupportedOperationException(
                                        "Don't know how to turn "
                                                + result.getClass().getSimpleName()
                                                + " into a string");
                            })
                    .build();

    private static final Optional<PlainTextComponentSerializer.Provider> SERVICE =
            Services.service(PlainTextComponentSerializer.Provider.class);

    static final Consumer<PlainTextComponentSerializer.Builder> BUILDER =
            SERVICE.map(PlainTextComponentSerializer.Provider::plainText)
                    .orElseGet(() -> result -> {});

    final ComponentFlattener flattener;

    PlainTextComponentSerializerImpl(ComponentFlattener componentFlattener) {
        this.flattener = componentFlattener;
    }

    @Override
    public void serialize(@NotNull StringBuilder stringBuilder, @NotNull Component component) {
        ComponentFlattener componentFlattener = this.flattener;
        Component component2 = Objects.requireNonNull(component, "component");
        componentFlattener.flatten(component2, stringBuilder::append);
    }

    @NotNull
    public PlainTextComponentSerializer.Builder toBuilder() {
        return new PlainTextComponentSerializerImpl.BuilderImpl(this);
    }

    static final class BuilderImpl implements PlainTextComponentSerializer.Builder {

        private ComponentFlattener flattener = PlainTextComponentSerializerImpl.DEFAULT_FLATTENER;

        BuilderImpl() {
            PlainTextComponentSerializerImpl.BUILDER.accept(this);
        }

        BuilderImpl(PlainTextComponentSerializerImpl plainTextComponentSerializerImpl) {
            this();
            this.flattener = plainTextComponentSerializerImpl.flattener;
        }

        @Override
        public PlainTextComponentSerializer.@NotNull Builder flattener(
                @NotNull ComponentFlattener componentFlattener) {
            this.flattener = Objects.requireNonNull(componentFlattener, "flattener");
            return this;
        }

        @NotNull
        public PlainTextComponentSerializer build() {
            return new PlainTextComponentSerializerImpl(this.flattener);
        }
    }

    static final class Instances {

        static final PlainTextComponentSerializer INSTANCE =
                PlainTextComponentSerializerImpl.SERVICE
                        .map(PlainTextComponentSerializer.Provider::plainTextSimple)
                        .orElseGet(
                                () ->
                                        new PlainTextComponentSerializerImpl(
                                                PlainTextComponentSerializerImpl
                                                        .DEFAULT_FLATTENER));
    }
}
