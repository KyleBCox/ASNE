package com.kc.asne.asne.tileentity;

import com.kc.asne.asne.capability.energy.SteamGeneratorEnergyStorage;
import com.kc.asne.asne.capability.fluid.SteamGeneratorWaterTank;
import com.kc.asne.asne.container.SteamGeneratorContainer;
import com.kc.asne.asne.init.ContainerTypes;
import com.kc.asne.asne.init.TileEntityTypes;
import com.kc.asne.base.capability.AsneEnergyStorage;
import com.kc.asne.base.capability.AsneFluidTank;
import com.kc.asne.base.tileentity.AsneMultiBlockMachineTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;

public class SteamGeneratorTileEntity extends AsneMultiBlockMachineTileEntity {

    private float fuelBurnPercentage = 0.f;

    private final int GENERATED_ENERGY_PER_TICK = 80;
    private static final int COAL_BURN_TIME = 800;

    public SteamGeneratorTileEntity() {
        super(TileEntityTypes.STEAM_GENERATOR.get(), ContainerTypes.STEAM_GENERATOR.get());
        this.hasEnergyStorage = true;
        this.hasFluidTank = true;
        this.energyOutput = true;
    }

    @Override
    protected AsneFluidTank createFluidHandler() {
        return new SteamGeneratorWaterTank();
    }

    @Override
    protected AsneEnergyStorage createEnergyHandler() {
        return new SteamGeneratorEnergyStorage();
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new SteamGeneratorContainer(id, player, this);
    }


    @Override
    public void tick() {
        super.tick();
        if ((invContents.get(0).isEmpty() && fuelBurnPercentage <= 0) || this.fluids.isEmpty()) {
            return;
        }
        if (fuelBurnPercentage <= 0) {
            invContents.get(0).setCount(invContents.get(0).getCount() - 1);
            fuelBurnPercentage = 1;
        }
        this.energy.internalReceiveEnergy(GENERATED_ENERGY_PER_TICK);
        fuelBurnPercentage -= 1.f / COAL_BURN_TIME;

    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    public float getEnergyContentsPercentage() {
        return (float) this.energy.getEnergyStored() / this.energy.getMaxEnergyStored();
    }

    public float getFuelBurnPercentage() {
        return this.fuelBurnPercentage;
    }

    public float getWaterContentsPercentage() {
        return (float) this.fluids.getFluidInTank(0).getAmount() / this.fluids.getTankCapacity(0);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {

        return super.write(compound);
    }
}
