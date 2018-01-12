package org.jurassicraft.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.server.block.entity.BugCrateBlockEntity;
import org.jurassicraft.server.container.BugCrateContainer;
import org.jurassicraft.server.container.FossilGrinderContainer;

@SideOnly(Side.CLIENT)
public class BugCrateGui extends GuiContainer {
    private static final ResourceLocation TEXTURE = new ResourceLocation("jurassicraft:textures/gui/bug_crate.png");
    private final InventoryPlayer playerInventory;
    private IInventory crate;

    public BugCrateGui(InventoryPlayer playerInventory, BugCrateBlockEntity crate) {
        super(crate.createContainer(playerInventory, playerInventory.player));
        this.playerInventory = playerInventory;
        this.crate = crate;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = this.crate.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(x + 88, y + 35, 176, 0, this.getProgress(24), 17);
    }

    private int getProgress(int scale) {
        int progress = this.crate.getField(0);
        int maxProgress = this.crate.getField(1);
        return maxProgress != 0 && progress != 0 ? progress * scale / maxProgress : 0;
    }
}
