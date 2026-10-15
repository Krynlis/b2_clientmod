package de.yvelgard.clientmod.neoforge;

import de.yvelgard.clientmod.YKeys;
import de.yvelgard.clientmod.YPayload;
import de.yvelgard.clientmod.ServerLink;
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

@Mod(value = YKeys.MOD_ID, dist = Dist.CLIENT)
public final class YvelgardNeoForge {

    public YvelgardNeoForge(IEventBus modBus) {
        ServerLink.init(new ServerLink.Transport() {
            @Override
            public boolean canSend() {
                ClientPacketListener connection = Minecraft.getInstance().getConnection();
                return connection != null && connection.hasChannel(YPayload.TYPE);
            }

            @Override
            public void send(YPayload payload) {
                ClientPacketDistributor.sendToServer(payload);
            }
        });

        modBus.addListener(RegisterPayloadHandlersEvent.class, event -> event.registrar("1").optional()
                .playBidirectional(YPayload.TYPE, YPayload.CODEC,
                        (payload, context) -> ServerLink.receive(payload, Minecraft.getInstance())));
        modBus.addListener(RegisterKeyMappingsEvent.class, event -> {
            KeyMapping.Category category = new KeyMapping.Category(YKeys.CATEGORY_ID);
            event.registerCategory(category);
            for (KeyMapping mapping : YKeys.create(category)) {
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
