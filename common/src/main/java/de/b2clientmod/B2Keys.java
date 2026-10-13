package de.b2clientmod;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public final class B2Keys {

    public static final String MOD_ID = "b2_clientmod";

    public static final Identifier CATEGORY_ID = Identifier.fromNamespaceAndPath(MOD_ID, "main");

    public static KeyMapping[] ACTIONS = new KeyMapping[0];

    private B2Keys() {
    }

    public static KeyMapping[] create(KeyMapping.Category category) {
        int[] keys = {InputConstants.KEY_R, InputConstants.KEY_G, InputConstants.KEY_V};
        KeyMapping[] mappings = new KeyMapping[keys.length];
        for (int i = 0; i < keys.length; i++) {
            mappings[i] = new KeyMapping("key." + MOD_ID + ".action_" + (i + 1), keys[i], category);
        }
        ACTIONS = mappings;
        return mappings;
    }
}
