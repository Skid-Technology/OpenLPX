package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.modifiers;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttribute;

import org.jspecify.annotations.NullMarked;

@NullMarked
public enum BooleanModifier implements AttributeModifier<Boolean, Boolean> {
    AND {

        public Boolean apply(Boolean matches, Boolean matches2) {
            return matches2 && matches;
        }
    },
    NAND {

        public Boolean apply(Boolean matches, Boolean matches2) {
            return !matches2 || !matches;
        }
    },
    OR {

        public Boolean apply(Boolean matches, Boolean matches2) {
            return matches2 || matches;
        }
    },
    NOR {

        public Boolean apply(Boolean matches, Boolean matches2) {
            return !matches2 && !matches;
        }
    },
    XOR {

        public Boolean apply(Boolean matches, Boolean matches2) {
            return matches2 ^ matches;
        }
    },
    XNOR {

        public Boolean apply(Boolean matches, Boolean matches2) {
            return matches2 == matches;
        }
    };

    BooleanModifier() {}

    @Override
    public NbtCodec<Boolean> argumentCodec(EnvironmentAttribute<Boolean> environmentAttribute) {
        return NbtCodecs.BOOLEAN;
    }
}
