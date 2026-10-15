package de.yvelgard.clientmod;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

public final class Protocol {

    public static final int VERSION = 1;

    public static final int C_HELLO = 0;
    public static final int C_INVENTORY = 1;
    public static final int C_KEY = 2;

    public static final int S_CONFIG = 0;
    public static final int S_OPEN_INVENTORY = 1;

    public static final int FLAG_REPLACE_INVENTORY = 1;

    private Protocol() {
    }

    public static YPayload hello(String modVersion) {
        return write(C_HELLO, out -> {
            out.writeInt(VERSION);
            out.writeUTF(modVersion);
        });
    }

    public static YPayload inventory(boolean replaced) {
        return write(C_INVENTORY, out -> out.writeBoolean(replaced));
    }

    public static YPayload key(int index) {
        return write(C_KEY, out -> out.writeByte(index));
    }

    public static DataInputStream read(YPayload payload) {
        return new DataInputStream(new ByteArrayInputStream(payload.data()));
    }

    private static YPayload write(int id, Body body) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (DataOutputStream out = new DataOutputStream(bytes)) {
            out.writeByte(id);
            body.write(out);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return new YPayload(bytes.toByteArray());
    }

    @FunctionalInterface
    private interface Body {
        void write(DataOutputStream out) throws IOException;
    }
}
