package fr.strow.core.module.mine.command.parameter;

import me.choukas.commands.api.Parameter;

import java.util.Optional;

/**
 * Created by Hokkaydo on 17-06-2020.
 */
public class MineTimeParameter extends Parameter<Integer> {

    public MineTimeParameter() {
        super("time");
    }


    @Override
    public Optional<Integer> check(String s) {
        return isInteger(s) ? Optional.ofNullable(Integer.parseInt(s)) : Optional.empty();
    }

    @Override
    public String getMessage(String s) {
        return "§cArgument invalide. Veuillez entrer un argument sous forme de nombre";
    }

    private boolean isInteger(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch (NumberFormatException ignored){}
        return false;
    }
}
