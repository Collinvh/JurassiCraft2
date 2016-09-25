package org.jurassicraft.server.block.fence;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jurassicraft.server.block.entity.ElectricFenceWireBlockEntity;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.tab.TabHandler;

public class ElectricFenceWireBlock extends BlockContainer {
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyDirection UP_DIRECTION = PropertyDirection.create("up");

    public ElectricFenceWireBlock() {
        super(Material.IRON);
        this.setHardness(2.0F);
        this.setCreativeTab(TabHandler.BLOCKS);
        this.setSoundType(SoundType.METAL);
    }

    @Override
    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
        boolean powered = false;
        if (!powered) {
            if (entity instanceof EntityPlayer || (entity instanceof DinosaurEntity && ((DinosaurEntity) entity).getDinosaur().canClimb())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        state = state.getActualState(world, pos);
        boolean north = state.getValue(ElectricFenceWireBlock.NORTH);
        boolean south = state.getValue(ElectricFenceWireBlock.SOUTH);
        boolean west = state.getValue(ElectricFenceWireBlock.WEST);
        boolean east = state.getValue(ElectricFenceWireBlock.EAST);
        if (!north && !south && !west && !east) {
            north = true;
            south = true;
            west = true;
            east = true;
        }
        double minX = 0.4, minZ = 0.4;
        double maxX = 0.6, maxZ = 0.6;
        if (north) {
            minZ = 0.0;
        }
        if (south) {
            maxZ = 1.0;
        }
        if (west) {
            minX = 0.0;
        }
        if (east) {
            maxX = 1.0;
        }
        return new AxisAlignedBB(minX, 0.0, minZ, maxX, 1.0, maxZ);
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new ElectricFenceWireBlockEntity();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, SOUTH, WEST, EAST, UP_DIRECTION);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        boolean north = false;
        boolean south = false;
        boolean east = false;
        boolean west = false;
        EnumFacing up = EnumFacing.DOWN;
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            BlockPos offset = pos.offset(facing);
            if (this.canConnect(world, pos, offset, world.getBlockState(offset))) {
                switch (facing) {
                    case NORTH:
                        north = true;
                        break;
                    case SOUTH:
                        south = true;
                        break;
                    case WEST:
                        west = true;
                        break;
                    case EAST:
                        east = true;
                        break;
                }
            }
        }
        BlockPos.MutableBlockPos basePos = new BlockPos.MutableBlockPos(pos);
        this.findBase(world, basePos);
        if (world.getBlockState(basePos).getBlock() instanceof ElectricFenceBaseBlock) {
            for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                BlockPos offset = basePos.offset(facing);
                BlockPos topOffset = offset.up();
                topOffset = topOffset.up(this.getFenceHeight(world, new BlockPos.MutableBlockPos(topOffset)) + 1);
                if (this.canConnect(world.getBlockState(topOffset))) {
                    BlockPos.MutableBlockPos connectBasePos = new BlockPos.MutableBlockPos(topOffset);
                    this.findBase(world, connectBasePos);
                    if (connectBasePos.getY() < basePos.getY()) {
                        continue;
                    }
                    if (world.getBlockState(connectBasePos).getBlock() instanceof ElectricFenceBaseBlock && !(world.getBlockState(offset).getBlock() instanceof ElectricFenceBaseBlock)) {
                        up = facing;
                        break;
                    }
                }
            }
        }
        return state.withProperty(NORTH, north).withProperty(SOUTH, south).withProperty(WEST, west).withProperty(EAST, east).withProperty(UP_DIRECTION, up);
    }

    private void findBase(IBlockAccess world, BlockPos.MutableBlockPos basePos) {
        IBlockState downState;
        int moved = 0;
        while (basePos.getY() > 1 && !((downState = world.getBlockState(basePos)).getBlock() instanceof ElectricFenceBaseBlock) && moved < 5) {
            basePos.setY(basePos.getY() - 1);
            if (downState.getBlock() instanceof ElectricFenceWireBlock) {
                moved = 0;
            } else {
                moved++;
            }
        }
    }

    private int getFenceHeight(IBlockAccess world, BlockPos.MutableBlockPos pos) {
        int height = 0;
        BlockPos start = new BlockPos(pos);
        if (world.getBlockState(pos).getBlock() instanceof ElectricFenceWireBlock) {
            IBlockState state = world.getBlockState(pos);
            while (pos.getY() < 255 && state.getBlock() instanceof ElectricFenceWireBlock) {
                pos.setY(pos.getY() + 1);
                state = world.getBlockState(pos);
                height++;
            }
            pos.setPos(start);
            while (pos.getY() > 1 && state.getBlock() instanceof ElectricFenceWireBlock) {
                pos.setY(pos.getY() - 1);
                state = world.getBlockState(pos);
                height++;
            }
        }
        return height;
    }

    protected boolean canConnect(IBlockState state) {
        return state.getBlock() instanceof ElectricFenceWireBlock || state.getBlock() instanceof ElectricFencePoleBlock;
    }

    protected boolean canConnect(IBlockAccess world, BlockPos current, BlockPos pos, IBlockState state) {
        if (this.canConnect(state)) {
            return true;
        } else {
            IBlockState down = world.getBlockState(pos.down());
            if (down.getBlock() instanceof ElectricFenceWireBlock && Math.abs(current.getY() - pos.down().getY()) == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }
}