package fr.strow.core.modules.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.faction.FactionChest;
import fr.strow.api.property.EmptyPropertyFactory;
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

public class FactionChestProperty implements FactionChest, ImplementationProperty<FactionChest> {

    private final FactionChestDao factionChestDao;

    private UUID factionUuid;
    private ItemStack[] chest;

    @Inject
    public FactionChestProperty(FactionChestDao factionChestDao) {
        this.factionChestDao = factionChestDao;
    }

    @Override
    public boolean load(UUID factionUuid) {
        this.factionUuid = factionUuid;

        if (factionChestDao.hasFactionChest(factionUuid)) {
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
        FactionChestBean bean = createBean(this.chest);
        factionChestDao.saveFactionChest(bean);
    }

    @Override
    public ItemStack[] getChest() {
        return chest;
    }

    @Override
    public void setChest(ItemStack[] chest) {
        if (this.chest != chest) {
            if (this.chest == null) {
                FactionChestBean bean = createBean(chest);
                factionChestDao.insertFactionChest(bean);

                this.chest = chest;
            } else {
                this.chest = chest;

                save(factionUuid);
            }
        }
    }

    @Override
    public void onUnregister(UUID factionUuid) {
        factionChestDao.deleteFactionChest(factionUuid);
    }

    private FactionChestBean createBean(ItemStack[] chest) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (BukkitObjectOutputStream data = new BukkitObjectOutputStream(out)) {
            data.writeInt(chest.length);

            for (ItemStack item : chest) {
                data.writeObject(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new FactionChestBean(factionUuid, new ByteArrayInputStream(out.toByteArray()));
    }
}
