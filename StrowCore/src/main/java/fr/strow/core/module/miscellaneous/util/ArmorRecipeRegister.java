package fr.strow.core.module.miscellaneous.util;

import fr.strow.core.module.miscellaneous.command.ArmorGiveCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

/**
 * Created by Hokkaydo on 12-07-2020.
 */
public class ArmorRecipeRegister {

    public static void register() {
        for (ItemStack itemStack : ArmorGiveCommand.FARM_ARMOR) {
            ShapedRecipe shapedRecipe = new ShapedRecipe(itemStack);

            switch (itemStack.getType()) {
                case LEATHER_HELMET:
                    shapedRecipe.shape("lfl", "d e", "   ");
                    break;
                case LEATHER_CHESTPLATE:
                    shapedRecipe.shape("l l", "lel", "fdf");
                    break;
                case LEATHER_LEGGINGS:
                    shapedRecipe.shape("lfl", "l l", "d e");
                    break;
                case LEATHER_BOOTS:
                    shapedRecipe.shape("   ", "d e", "f l");
                    break;
            }
            shapedRecipe.setIngredient('l', Material.LEATHER);
            shapedRecipe.setIngredient('f', Material.FEATHER);
            shapedRecipe.setIngredient('d', Material.DIAMOND_BLOCK);
            shapedRecipe.setIngredient('e', Material.EMERALD_BLOCK);

            Bukkit.addRecipe(shapedRecipe);
        }
    }
}
