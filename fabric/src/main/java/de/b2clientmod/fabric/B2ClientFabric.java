package de.b2clientmod.fabric;

import de.b2clientmod.B2Keys;
import de.b2clientmod.B2Payload;
import de.b2clientmod.ServerLink;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.KeyMapping;

public final class B2ClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ServerLink.init(new ServerLink.Transport() {
            @Override
            public boolean canSend() {
                return ClientPlayNetworking.canSend(B2Payload.TYPE);
            }

            @Override
            public void send(B2Payload payload) {
                ClientPlayNetworking.send(payload);
            }
        });

        PayloadTypeRegistry.serverboundPlay().register(B2Payload.TYPE, B2Payload.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(B2Payload.TYPE, B2Payload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(B2Payload.TYPE,
                (payload, context) -> ServerLink.receive(payload, context.client()));
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> ServerLink.reset());

        for (KeyMapping mapping : B2Keys.create(KeyMapping.Category.register(B2Keys.CATEGORY_ID))) {
            KeyMappingHelper.registerKeyMapping(mapping);
        }
        ClientTickEvents.END_CLIENT_TICK.register(ServerLink::tick);
    }
}
