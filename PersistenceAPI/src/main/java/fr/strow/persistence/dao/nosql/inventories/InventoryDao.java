/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 06/06/2020 21:25
 */

package fr.strow.persistence.dao.nosql.inventories;

import com.google.inject.Inject;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public class InventoryDao {

    private final Path inventoryFolder;

    @Inject
    public InventoryDao(@InventoryFolder Path inventoryFolder) {
        this.inventoryFolder = inventoryFolder;
    }

    public ItemStack[] getInventoryContent(UUID faction) {
        try (BukkitObjectInputStream data = new BukkitObjectInputStream(new FileInputStream(String.valueOf(inventoryFolder.resolve(faction.toString()))))) {
            int size = data.readInt();
            ItemStack[] content = new ItemStack[size];

            for (int i = 0; i < size; i++) {
                content[i] = (ItemStack) data.readObject();
            }

            return content;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveInventoryContent(UUID faction, Inventory inventory) {
        try (BukkitObjectOutputStream data = new BukkitObjectOutputStream(new FileOutputStream(String.valueOf(inventoryFolder.resolve(faction.toString()))))) {
            data.writeInt(inventory.getSize());

            for (ItemStack item : inventory.getContents()) {
                data.writeObject(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
