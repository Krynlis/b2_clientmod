package de.yvelgard.clientmod.fabric;

import de.yvelgard.clientmod.YKeys;
import de.yvelgard.clientmod.YPayload;
import de.yvelgard.clientmod.ServerLink;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.KeyMapping;

public final class YvelgardFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ServerLink.init(new ServerLink.Transport() {
            @Override
            public boolean canSend() {
                return ClientPlayNetworking.canSend(YPayload.TYPE);
            }

            @Override
            public void send(YPayload payload) {
                ClientPlayNetworking.send(payload);
            }
        });

        PayloadTypeRegistry.serverboundPlay().register(YPayload.TYPE, YPayload.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(YPayload.TYPE, YPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(YPayload.TYPE,
                (payload, context) -> ServerLink.receive(payload, context.client()));
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> ServerLink.reset());

        for (KeyMapping mapping : YKeys.create(KeyMapping.Category.register(YKeys.CATEGORY_ID))) {
            KeyMappingHelper.registerKeyMapping(mapping);
        }
        ClientTickEvents.END_CLIENT_TICK.register(ServerLink::tick);
    }
}
