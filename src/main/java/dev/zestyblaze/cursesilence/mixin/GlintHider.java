package dev.zestyblaze.cursesilence.mixin;

import dev.zestyblaze.cursesilence.CurseSilenceConfig;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class GlintHider {
    @Inject(method = "isFoil", at = @At("HEAD"), cancellable = true)
    private void curse_isFoil(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if(CurseSilenceConfig.CURSE_TYPE.get() == CurseSilenceConfig.CurseType.FULL) {
            cir.setReturnValue(EnchantmentHelper.getEnchantments(stack).keySet().stream().anyMatch(e -> !e.isCurse()));
        }
    }
}
