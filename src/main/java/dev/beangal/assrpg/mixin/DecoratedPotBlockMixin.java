package dev.beangal.assrpg.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DecoratedPotBlock.class)
public class DecoratedPotBlockMixin {
    @Inject(method = "getDrops", at = @At("HEAD"), cancellable = true)
    protected void preventSherdDrops(BlockState blockState, LootParams.Builder builder, CallbackInfoReturnable<List<ItemStack>> cir) {
        cir.setReturnValue(List.of());
    }
}
