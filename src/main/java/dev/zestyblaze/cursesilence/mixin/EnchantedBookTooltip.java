package dev.zestyblaze.cursesilence.mixin;

import dev.zestyblaze.cursesilence.CurseSilence;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EnchantedBookItem.class)
public abstract class EnchantedBookTooltip extends Item {
    @Shadow
    public static ListTag getEnchantments(ItemStack enchantedBookStack) {
        return null;
    }

    public EnchantedBookTooltip(Properties properties) {
        super(properties);
    }

    @Inject(method = "appendHoverText", at = @At("HEAD"), cancellable = true)
    private void curse_appendHoverText(ItemStack stack, Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced, CallbackInfo ci) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        CurseSilence.appendEnchantmentsAndCurses(tooltipComponents, getEnchantments(stack));
        ci.cancel();
    }
}
