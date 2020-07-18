package fr.strow.api.game.player;

import fr.strow.api.game.StrowCollection;

import java.util.List;

public class PlayersCollection extends StrowCollection<StrowPlayer> {

    public PlayersCollection(List<StrowPlayer> players) {
        super(players);
    }

    public PlayersCollection connected() {
        filter().add(StrowPlayer::isConnected);

        return this;
    }

    public Filter<StrowPlayer> withPseudo(String pseudo) {
        return filter().add(player ->
                player.getProperty(Pseudo.class).getPseudo().equals(pseudo));
    }
}
