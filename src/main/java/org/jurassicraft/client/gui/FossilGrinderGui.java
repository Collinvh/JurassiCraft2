package org.jurassicraft.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.server.container.FossilGrinderContainer;

@SideOnly(Side.CLIENT)
public class FossilGrinderGui extends GuiContainer {
    private static final ResourceLocation TEXTURE = new ResourceLocation("jurassicraft:textures/gui/fossil_grinder.png");
    private final InventoryPlayer playerInventory;
    private IInventory fossilGrinder;

    public FossilGrinderGui(InventoryPlayer playerInv, IInventory fossilGrinder) {
        super(new FossilGrinderContainer(playerInv, (TileEntity) fossilGrinder));
        this.playerInventory = playerInv;
        this.fossilGrinder = fossilGrinder;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.fossilGrinder.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        int progress = this.getProgress(24);
        this.drawTexturedModalRect(x + 79, y + 34, 176, 14, progress + 1, 16);
    }

    private int getProgress(int scale) {
        int j = this.fossilGrinder.getField(0);
        int k = this.fossilGrinder.getField(1);
        return k != 0 && j != 0 ? j * scale / k : 0;
    }
}
