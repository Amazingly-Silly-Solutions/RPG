package dev.beangal.assrpg.mixin;

import dev.beangal.assrpg.AssRPGUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Zombie.class)
public class ZombieMixin {
    @Shadow
    private boolean canBreakDoors;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        Zombie zombie = (Zombie) (Object) this;
        if (!zombie.level().isClientSide()) {
            this.canBreakDoors = !AssRPGUtils.isChunkProtected(zombie.level().getChunk(BlockPos.containing(zombie.getPosition(0f))));
        }
    }
}
