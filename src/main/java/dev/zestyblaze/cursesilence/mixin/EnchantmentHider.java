package dev.zestyblaze.cursesilence.mixin;

import dev.zestyblaze.cursesilence.CurseSilenceConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(ItemStack.class)
public class EnchantmentHider {
    @Inject(method = "appendEnchantmentNames", at = @At(value = "HEAD"), cancellable = true)
    private static void applyCurseSilence(List<Component> tooltipComponents, ListTag storedEnchantments, CallbackInfo ci) {
        int removed = 0;
        for(Tag t: storedEnchantments) {
            if(t instanceof CompoundTag) {
                Optional<Enchantment> ench = Registry.ENCHANTMENT.getOptional(ResourceLocation.tryParse(((CompoundTag)t).getString("id")));
                if(ench.isPresent()) {
                    if(ench.get().isCurse()) {
                        removed++;
                    } else {
                        tooltipComponents.add(ench.get().getFullname(((CompoundTag)t).getInt("lvl")));
                    }
                }
            }
        }

        if(removed > 0 && CurseSilenceConfig.CURSE_TYPE.get() != CurseSilenceConfig.CurseType.FULL) {
            int level = CurseSilenceConfig.CURSE_TYPE.get() == CurseSilenceConfig.CurseType.HINT ? 0 : removed;
            tooltipComponents.add(Component.translatable("cursesilence.cursed", Component.translatable("enchantment.level." + level)).withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
        }

        ci.cancel();
    }
}
