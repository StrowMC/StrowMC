package fr.strow.core.module.miscellaneous.configuration;

import fr.strow.api.configuration.AbstractConfiguration;
import fr.strow.api.configuration.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hokkaydo on 12-07-2020.
 */
public class MiscellaneousConfiguration extends AbstractConfiguration {

    //region farmsword attributes
    @Config("farmsword.name")
    public static String FARMSWORD_NAME = "§6FarmSword";
    @Config("farmsword.lore")
    public static List<String> FARMSWORD_LORE = new ArrayList<>();
    //endregion
    //region money earned at kill
    @Config("farmsword.money.pig")
    public static int PIG_MONEY = 1;
    @Config("farmsword.money.cow")
    public static int COW_MONEY = 1;
    @Config("farmsword.money.zombie")
    public static int ZOMBIE_MONEY = 2;
    @Config("farmsword.money.skeleton")
    public static int SKELETON_MONEY = 2;
    @Config("farmsword.money.spider")
    public static int SPIDER_MONEY = 2;
    @Config("farmsword.pigman.spider")
    public static int PIGMAN_MONEY = 3;
    @Config("farmsword.money.creeper")
    public static int CREEPER_MONEY = 3;
    @Config("farmsword.money.golem")
    public static int GOLEM_MONEY = 3;
    @Config("farmsword.money.blaze")
    public static int BLAZE_MONEY = 5;
    //endregion
    //region farmarmor attributes
    @Config("farmarmor.helmet.name")
    public static String FARMHELMET_NAME = "§6FarmHelmet";
    @Config("farmarmor.chestplate.name")
    public static String FARMCHESTPLATE_NAME = "§6FarmChestplate";
    @Config("farmarmor.leggings.name")
    public static String FARMLEGGINGS_NAME = "§6FarmLeggings";
    @Config("farmarmor.boots.name")
    public static String FARMBOOTS_NAME = "§6FarmBoots";

    public MiscellaneousConfiguration() {
        super("misc");
    }
}
