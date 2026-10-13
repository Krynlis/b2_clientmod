package de.b2clientmod;

import com.mojang.blaze3d.platform.InputConstants;
import java.io.DataInputStream;
import java.io.IOException;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.Identifier;

public final class B2ClientMod implements ClientModInitializer {

    public static final String MOD_ID = "b2_clientmod";

    private static final KeyMapping.Category CATEGORY =
            KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "main"));

    private final KeyMapping[] actions = new KeyMapping[3];
    private String version = "0";

    @Override
    public void onInitializeClient() {
        version = FabricLoader.getInstance().getModContainer(MOD_ID)
                .map(c -> c.getMetadata().getVersion().getFriendlyString()).orElse("0");

        PayloadTypeRegistry.serverboundPlay().register(B2Payload.TYPE, B2Payload.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(B2Payload.TYPE, B2Payload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(B2Payload.TYPE, (payload, context) -> receive(payload, context.client()));

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> ServerLink.sayHello(version));
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> ServerLink.reset());

        int[] defaults = {InputConstants.KEY_R, InputConstants.KEY_G, InputConstants.KEY_V};
        for (int i = 0; i < actions.length; i++) {
            actions[i] = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                    "key." + MOD_ID + ".action_" + (i + 1), defaults[i], CATEGORY));
        }
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                ServerLink.sayHello(version);
            }
            for (int i = 0; i < actions.length; i++) {
                while (actions[i].consumeClick()) {
                    if (ServerLink.configured()) {
                        ServerLink.send(Protocol.key(i + 1));
                    }
                }
            }
        });
    }

    private static void receive(B2Payload payload, Minecraft client) {
        try (DataInputStream in = Protocol.read(payload)) {
            int id = in.readUnsignedByte();
            if (id == Protocol.S_CONFIG) {
                in.readInt();
                ServerLink.configure(in.readInt());
            } else if (id == Protocol.S_OPEN_INVENTORY) {
                openVanillaInventory(client);
            }
        } catch (IOException ignored) {
        }
    }

    private static void openVanillaInventory(Minecraft client) {
        if (client.player == null || client.gameMode == null) {
            return;
        }
        ServerLink.allowVanillaInventory(true);
        try {
            client.gui.setScreen(new InventoryScreen(client.player));
        } finally {
            ServerLink.allowVanillaInventory(false);
        }
    }
}
