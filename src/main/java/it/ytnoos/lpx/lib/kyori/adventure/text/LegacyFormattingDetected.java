package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.util.Nag;

final class LegacyFormattingDetected extends Nag {

    private static final long serialVersionUID = -7457509390481355432L;

    LegacyFormattingDetected(Component component) {
        super(
                "Legacy formatting codes have been detected in a component - this is unsupported behaviour. Please refer to the Adventure documentation (https://docs.papermc.io/adventure/) for more information. Component: "
                        + component);
    }
}
