package fr.strow.core.module.duel.util;

import fr.strow.core.module.duel.command.SetInventoryCommand;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Hokkaydo on 10-07-2020.
 */
public class Duel {

    private final DuelGroup duelGroup1;
    private final SetInventoryCommand.InventoryType inventoryType;
    private final Duel.Type type;
    private DuelGroup duelGroup2;
    private boolean started;

    public Duel(DuelGroup duelGroup, Type type, SetInventoryCommand.InventoryType inventoryType) {
        this.duelGroup1 = duelGroup;
        this.type = type;
        this.inventoryType = inventoryType;
    }

    public void setSecondDuelGroup(DuelGroup duelGroup) {
        duelGroup2 = duelGroup;
    }

    public SetInventoryCommand.InventoryType getInventoryType() {
        return inventoryType;
    }

    public DuelGroup getDuelGroup1() {
        return duelGroup1;
    }

    public DuelGroup getDuelGroup2() {
        return duelGroup2;
    }

    public Type getType() {
        return type;
    }

    public void start() {
        started = true;
    }

    public boolean isStarted() {
        return started;
    }

    public enum Type {
        ONEVONE("1v1", 1),
        TWOVTWO("2v2", 2),
        EIGHTVEIGHT("8v8", 8);

        private final String name;
        private final int playerAmount;

        Type(String name, int playerAmount) {
            this.name = name;
            this.playerAmount = playerAmount;
        }

        public static Optional<Type> getByAmount(int size) {
            return Arrays.stream(values()).filter(t -> t.playerAmount == size).findFirst();
        }

        public static Optional<Type> getByName(String name) {
            return Arrays.stream(values()).filter(t -> t.name.equalsIgnoreCase(name)).findFirst();
        }

        public String getName() {
            return name;
        }

        public int getPlayerAmount() {
            return playerAmount;
        }
    }
}
