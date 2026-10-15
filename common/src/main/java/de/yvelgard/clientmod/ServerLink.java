package de.yvelgard.clientmod;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;

public final class ServerLink {

    public interface Transport {
        boolean canSend();

        void send(YPayload payload);
    }

    private static Transport transport;
    private static String modVersion = "0";
    private static boolean helloSent;
    private static boolean configured;
    private static int flags;
    private static boolean vanillaInventoryAllowed;

    private ServerLink() {
    }

    public static void init(Transport newTransport) {
        transport = newTransport;
        modVersion = readVersion();
    }

    private static String readVersion() {
        try (InputStream in = ServerLink.class.getResourceAsStream("/yvelgard_clientmod.properties")) {
            Properties props = new Properties();
            if (in != null) {
                props.load(in);
            }
            return props.getProperty("version", "0");
        } catch (IOException e) {
            return "0";
        }
    }

    public static void tick(Minecraft client) {
        if (client.player == null) {
            return;
        }
        if (!helloSent && transport.canSend()) {
            helloSent = true;
            transport.send(Protocol.hello(modVersion));
        }
        for (int i = 0; i < YKeys.ACTIONS.length; i++) {
            while (YKeys.ACTIONS[i].consumeClick()) {
                if (configured) {
                    send(Protocol.key(i + 1));
                }
            }
        }
    }

    public static boolean interceptScreen(Screen screen) {
        if (!(screen instanceof InventoryScreen) || !configured) {
            return false;
        }
        boolean replace = (flags & Protocol.FLAG_REPLACE_INVENTORY) != 0 && !vanillaInventoryAllowed;
        send(Protocol.inventory(replace));
        return replace;
    }

    public static void receive(YPayload payload, Minecraft client) {
        try (DataInputStream in = Protocol.read(payload)) {
            int id = in.readUnsignedByte();
            if (id == Protocol.S_CONFIG) {
                in.readInt();
                flags = in.readInt();
                configured = true;
            } else if (id == Protocol.S_OPEN_INVENTORY) {
                openVanillaInventory(client);
            }
        } catch (IOException ignored) {
        }
    }

    public static void reset() {
        helloSent = false;
        configured = false;
        flags = 0;
        vanillaInventoryAllowed = false;
    }

    private static void send(YPayload payload) {
        if (transport.canSend()) {
            transport.send(payload);
        }
    }

    private static void openVanillaInventory(Minecraft client) {
        if (client.player == null || client.gameMode == null) {
            return;
        }
        vanillaInventoryAllowed = true;
        try {
            client.gui.setScreen(new InventoryScreen(client.player));
        } finally {
            vanillaInventoryAllowed = false;
        }
    }
}
