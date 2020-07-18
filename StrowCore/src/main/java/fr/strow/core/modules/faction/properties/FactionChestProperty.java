package fr.strow.core.modules.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.faction.FactionChest;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.factions.FactionChestBean;
import fr.strow.persistence.dao.factions.FactionChestDao;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class FactionChestProperty extends ImplementationProperty implements FactionChest {

    private final FactionChestDao factionChestDao;

    private ItemStack[] chest;

    @Inject
    public FactionChestProperty(FactionChestDao factionChestDao) {
        this.factionChestDao = factionChestDao;
    }

    @Override
    public boolean load(UUID factionUuid) {
        if (factionChestDao.hasChest(factionUuid)) {
            FactionChestBean bean = factionChestDao.loadFactionChest(factionUuid);

            try (BukkitObjectInputStream data = new BukkitObjectInputStream(bean.getContent())) {
                int size = data.readInt();
                ItemStack[] content = new ItemStack[size];

                for (int i = 0; i < size; i++) {
                    content[i] = (ItemStack) data.readObject();
                }

                chest = content;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void save(UUID factionUuid) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (BukkitObjectOutputStream data = new BukkitObjectOutputStream(out)) {
            data.writeInt(chest.length);

            for (ItemStack item : chest) {
                data.writeObject(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        FactionChestBean bean = new FactionChestBean(factionUuid, new ByteArrayInputStream(out.toByteArray()));

        factionChestDao.saveFactionChest(bean);
    }

    @Override
    public ItemStack[] getChest() {
        return chest;
    }

    @Override
    public void setChest(ItemStack[] chest) {
        this.chest = chest;
    }
}
