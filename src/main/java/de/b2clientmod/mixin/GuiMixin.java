package de.b2clientmod.mixin;

import de.b2clientmod.Protocol;
import de.b2clientmod.ServerLink;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    private void b2clientmod$onSetScreen(Screen screen, CallbackInfo ci) {
        if (!(screen instanceof InventoryScreen) || !ServerLink.configured()) {
            return;
        }
        boolean replace = ServerLink.replacesInventory();
        ServerLink.send(Protocol.inventory(replace));
        if (replace) {
            ci.cancel();
        }
    }
}
