package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;

abstract class AbstractTag implements Tag, Examinable {

    @Override
    public final String toString() {
        return Internals.toString(this);
    }
}
