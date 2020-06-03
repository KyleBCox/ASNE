package com.kc.asne.base.client.gui.components.gauge;

import com.kc.asne.base.client.gui.IAsneScreenPart;
import com.kc.asne.base.client.gui.base.TexturedGuiElement;
import com.kc.asne.base.general.constants.AsneConstants;
import com.kc.asne.base.math.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.util.ResourceLocation;

public abstract class GuiGauge implements IAsneScreenPart {
    private final ContainerScreen<?> caller;
    private final int x;
    private final int y;

    public GuiGauge(ContainerScreen<?> caller, int x, int y) {
        this.caller = caller;
        this.x = x;
        this.y = y;
    }

    protected ResourceLocation getTextureSheet() {
        return AsneConstants.RL.SLOT_PARTS_TEXTURE;
    }

    protected abstract float getPercentageUsed();

    @Override
    public void renderBackground(float partialTicks, int mouseX, int mouseY) {
        Minecraft.getInstance().getTextureManager().bindTexture(getTextureSheet());
        int x = (this.caller.width - this.caller.getXSize()) / 2;
        int y = (this.caller.height - this.caller.getYSize()) / 2;
        this.caller.blit(x + this.x, y + this.y, 50, 14, 18, 50);
        this.caller.blit(x + this.x, y + this.y, 68, 14, 18, (int) (getPercentageUsed() * 50.f));
        this.caller.drawString(this.caller.getMinecraft().fontRenderer, Float.toString(getPercentageUsed()), x + this.x, y + this.y, 1);
    }
}
