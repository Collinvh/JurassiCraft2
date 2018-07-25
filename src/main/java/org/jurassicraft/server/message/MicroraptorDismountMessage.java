package org.jurassicraft.server.message;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.entity.DinosaurEntity;

import java.util.Set;

public class MicroraptorDismountMessage extends AbstractMessage<MicroraptorDismountMessage> {
    private int entityId;

    public MicroraptorDismountMessage(int entityId) {
        this.entityId = entityId;
    }

    public MicroraptorDismountMessage() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.entityId);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientReceived(Minecraft client, MicroraptorDismountMessage message, EntityPlayer player, MessageContext messageContext) {
        Entity entity = client.world.getEntityByID(message.entityId);
        if (entity instanceof DinosaurEntity) {
            entity.dismountRidingEntity();
        }
    }

    @Override
    public void onServerReceived(MinecraftServer server, MicroraptorDismountMessage message, EntityPlayer player, MessageContext messageContext) {
        Entity entity = player.world.getEntityByID(message.entityId);
        if (entity instanceof DinosaurEntity) {
            DinosaurEntity microraptor = (DinosaurEntity) entity;
            if (microraptor.isOwner(player)) {
                microraptor.dismountRidingEntity();
                if (!player.world.isRemote) {
                    WorldServer worldServer = (WorldServer) player.world;
                    Set<? extends EntityPlayer> trackers = worldServer.getEntityTracker().getTrackingPlayers(microraptor);
                    for (EntityPlayer tracker : trackers) {
                        JurassiCraft.NETWORK_WRAPPER.sendTo(message, (EntityPlayerMP) tracker);
                    }
                }
            }
        }
    }
}