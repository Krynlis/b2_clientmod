package de.b2clientmod.neoforge;

import de.b2clientmod.B2Keys;
import de.b2clientmod.B2Payload;
import de.b2clientmod.ServerLink;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@Mod(value = B2Keys.MOD_ID, dist = Dist.CLIENT)
public final class B2ClientNeoForge {

    public B2ClientNeoForge(IEventBus modBus) {
        ServerLink.init(new ServerLink.Transport() {
            @Override
            public boolean canSend() {
                ClientPacketListener connection = Minecraft.getInstance().getConnection();
                return connection != null && connection.hasChannel(B2Payload.TYPE);
            }

            @Override
            public void send(B2Payload payload) {
                ClientPacketDistributor.sendToServer(payload);
            }
        });

        modBus.addListener(RegisterPayloadHandlersEvent.class, event -> event.registrar("1").optional()
                .playBidirectional(B2Payload.TYPE, B2Payload.CODEC,
                        (payload, context) -> ServerLink.receive(payload, Minecraft.getInstance())));
        modBus.addListener(RegisterKeyMappingsEvent.class, event -> {
            KeyMapping.Category category = new KeyMapping.Category(B2Keys.CATEGORY_ID);
            event.registerCategory(category);
            for (KeyMapping mapping : B2Keys.create(category)) {
                event.register(mapping);
            }
        });

        NeoForge.EVENT_BUS.addListener(ClientTickEvent.Post.class, event -> ServerLink.tick(Minecraft.getInstance()));
        NeoForge.EVENT_BUS.addListener(ScreenEvent.Opening.class, event -> {
            if (ServerLink.interceptScreen(event.getNewScreen())) {
                event.setCanceled(true);
            }
        });
        NeoForge.EVENT_BUS.addListener(ClientPlayerNetworkEvent.LoggingOut.class, event -> ServerLink.reset());
    }
}
