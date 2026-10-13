package de.b2clientmod;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public final class ServerLink {

    private static boolean helloSent;
    private static boolean configured;
    private static int flags;
    private static boolean vanillaInventoryAllowed;

    private ServerLink() {
    }

    public static boolean canSend() {
        return ClientPlayNetworking.canSend(B2Payload.TYPE);
    }

    public static void send(B2Payload payload) {
        if (canSend()) {
            ClientPlayNetworking.send(payload);
        }
    }

    static void sayHello(String modVersion) {
        if (!helloSent && canSend()) {
            helloSent = true;
            ClientPlayNetworking.send(Protocol.hello(modVersion));
        }
    }

    static void configure(int newFlags) {
        configured = true;
        flags = newFlags;
    }

    static void reset() {
        helloSent = false;
        configured = false;
        flags = 0;
        vanillaInventoryAllowed = false;
    }

    public static boolean configured() {
        return configured;
    }

    public static boolean replacesInventory() {
        return configured && (flags & Protocol.FLAG_REPLACE_INVENTORY) != 0 && !vanillaInventoryAllowed;
    }

    static void allowVanillaInventory(boolean allowed) {
        vanillaInventoryAllowed = allowed;
    }
}
