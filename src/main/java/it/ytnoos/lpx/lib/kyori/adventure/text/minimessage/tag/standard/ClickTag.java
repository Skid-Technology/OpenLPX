package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.standard;

import it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.ParsingException;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.QuotingOverride;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.StyleClaim;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

final class ClickTag {

    static final TagResolver RESOLVER =
            SerializableResolver.claimingStyle(
                    "click",
                    ClickTag::create,
                    StyleClaim.claim(
                            "click",
                            Style::clickEvent,
                            (result, result2) ->
                                    result2.tag("click")
                                            .argument(ClickEvent.Action.NAMES.key(result.action()))
                                            .argument(result.value(), QuotingOverride.QUOTED)));

    private ClickTag() {}

    static Tag create(ArgumentQueue argumentQueue, Context context) throws ParsingException {
        String text =
                argumentQueue
                        .popOr(
                                () ->
                                        "A click tag requires an action of one of "
                                                + ClickEvent.Action.NAMES.keys())
                        .lowerValue();
        ClickEvent.Action action;
        if ((action = ClickEvent.Action.NAMES.value(text)) == null) {
            throw context.newException("Unknown click event action '" + text + "'", argumentQueue);
        }
        String text2 = argumentQueue.popOr("Click event actions require a value").value();
        return Tag.styling(ClickEvent.clickEvent(action, text2));
    }
}
