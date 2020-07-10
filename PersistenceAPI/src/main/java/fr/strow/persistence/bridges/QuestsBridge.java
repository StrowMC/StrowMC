package fr.strow.persistence.bridges;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.quests.QuestProgressBean;
import fr.strow.persistence.data.redis.RedisAccess;
import fr.strow.persistence.data.sql.SQLAccess;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class QuestsBridge extends AbstractBridge {

    @Inject
    public QuestsBridge(SQLAccess sqlAccess, RedisAccess redisAccess, Gson gson) {
        super(sqlAccess, redisAccess, gson);
    }

    public void loadQuests() {
        Map<UUID, QuestProgressBean> questsProgresses = new HashMap<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM " + Tables.QUESTS_PROGRESSES;

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                        int id = resultSet.getInt("id");
                        int progress = resultSet.getInt("progress");

                        QuestProgressBean questProgress = new QuestProgressBean(uuid, id, progress);
                        questsProgresses.put(uuid, questProgress);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try (Jedis jedis = redisAccess.getResource()) {
            for (Map.Entry<UUID, QuestProgressBean> questProgress : questsProgresses.entrySet()) {
                jedis.hset(Tables.QUESTS_PROGRESSES, questProgress.getKey().toString(), gson.toJson(questProgress.getValue()));
            }
        }
    }

    public void unloadQuests() {
        Map<UUID, QuestProgressBean> questProgresses;

        try (Jedis jedis = redisAccess.getResource()) {
            questProgresses = jedis.hgetAll(Tables.QUESTS_PROGRESSES)
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            questProgress ->
                                    UUID.fromString(questProgress.getKey()),
                            questProgress ->
                                    gson.fromJson(questProgress.getValue(), QuestProgressBean.class)
                    ));
        }

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE " + Tables.QUESTS_PROGRESSES + " SET id = ?, progress = ? WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                for (Map.Entry<UUID, QuestProgressBean> questProgress : questProgresses.entrySet()) {
                    UUID uuid = questProgress.getKey();
                    QuestProgressBean bean = questProgress.getValue();

                    statement.setInt(1, bean.getId());
                    statement.setInt(2, bean.getProgress());
                    statement.setString(3, uuid.toString());

                    if (statement.executeUpdate() == 0) {
                        insertPlayer(connection, bean);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void insertPlayer(Connection connection, QuestProgressBean bean) throws SQLException {
        final String SQL = "INSERT INTO " + Tables.QUESTS_PROGRESSES + " (uuid, id, progress) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, bean.getUuid().toString());
            statement.setInt(2, bean.getId());
            statement.setInt(3, bean.getProgress());

            statement.executeUpdate();
        }
    }
}
