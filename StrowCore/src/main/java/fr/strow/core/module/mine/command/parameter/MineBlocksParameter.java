package fr.strow.core.module.mine.command.parameter;

import me.choukas.commands.api.Parameter;
import org.bukkit.Material;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hokkaydo on 17-06-2020.
 */
public class MineBlocksParameter extends Parameter<String> {

    public MineBlocksParameter() {
        super("time");
    }

    @Override
    public Optional<String> check(String s) {
        return match(s) ? Optional.of(s) : Optional.empty();
    }

    @Override
    public String getMessage(String s) {
        return "§cArgument non reconnu. Veuillez donner la configurations sous la forme number%id | number%name";
    }

    private boolean match(String s){
        Pattern pattern = Pattern.compile("\\d{1,3}%([A-z]+}|[0-9]{1,3})");
        Matcher matcher = pattern.matcher(s);
        if(!matcher.matches()) return false;
        if(isInteger(s.split("%")[1])){
            if(Material.getMaterial(Integer.parseInt(s.split("%")[1])) == null) return false;
        }
        if(!isInteger(s.split("%")[1])){
            return Material.getMaterial(s.split("%")[1]) != null;
        }
        return true;
    }

    private boolean isInteger(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch (NumberFormatException ignored){}
        return false;
    }
}
