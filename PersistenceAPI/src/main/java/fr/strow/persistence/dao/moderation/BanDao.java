package fr.strow.persistence.dao.moderation;

public class BanDao {

    /*@Inject
    public BanDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public boolean hasBan(UUID uuid) {
        try (Jedis jedis = redisAccess.getResource()) {
            return jedis.hexists(Tables.BANS, uuid.toString());
        }
    }

    public List<BanBean> loadBans(UUID uuid) {
        List<BanBean> beans;
        final Type type = new TypeToken<List<BanBean>>() {}.getType();

        try (Jedis jedis = redisAccess.getResource()) {
            beans = gson.fromJson(jedis.hget(Tables.BANS, uuid.toString()), type);
        }

        return beans;
    }

    public void saveBans(UUID uuid, List<BanBean> beans) {
        try (Jedis jedis = redisAccess.getResource()) {
            jedis.hset(Tables.BANS,uuid.toString(), gson.toJson(beans));
        }
    }*/
}
