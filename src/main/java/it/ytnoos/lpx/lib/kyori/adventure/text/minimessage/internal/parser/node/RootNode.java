package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tree.Node;

import org.jetbrains.annotations.NotNull;

public final class RootNode extends ElementNode implements Node.Root {

    private final String beforePreprocessing;

    public RootNode(@NotNull String text, @NotNull String beforePreprocessing) {
        super(null, null, text);
        this.beforePreprocessing = beforePreprocessing;
    }

    @NotNull
    @Override
    public String input() {
        return this.beforePreprocessing;
    }
}
