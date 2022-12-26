package com.github.badaccuracyid.cuddlyoctogarbanzo.database;

import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.PlayerData;
import com.github.badaccuracyid.cuddlyoctogarbanzo.utils.EncryptionUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.*;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

    private final Map<String, PlayerData> playerDataMap;
    private PlayerData currentPlayer;

    public Database() {
        this.playerDataMap = new HashMap<>();
        this.currentPlayer = null;

        this.loadAllData();
    }

    public List<PlayerData> getLoadedData() {
        return new ArrayList<>(playerDataMap.values());
    }

    public void setCurrentPlayer(PlayerData playerData) {
        this.currentPlayer = playerData;
    }

    public PlayerData getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void savePlayerData(PlayerData data) {
        playerDataMap.put(data.getUsername(), data);

        try {
            this.saveAllData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PlayerData getPlayerData(String username) {
        return playerDataMap.get(username);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void loadAllData() {
        playerDataMap.clear();

        File file = new File("SuperS3cr3tFile.dat");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String read;
            while ((read = reader.readLine()) != null) {
                String decrypt;
                try {
                    decrypt = EncryptionUtils.decrypt(read);
                } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
                    e.printStackTrace();
                    continue;
                }

                String[] split = decrypt.split("#");

                PlayerData playerData = new PlayerData(split[0]);
                playerData.setPassword(split[1]);
                playerData.setScore(Integer.parseInt(split[2]));

                playerDataMap.put(playerData.getUsername(), playerData);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveAllData() throws IOException {
        File file = new File("SuperS3cr3tFile.dat");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (PlayerData playerData : playerDataMap.values()) {
                String content;
                try {
                    content = EncryptionUtils.encrypt(playerData.getUsername() + "#" + playerData.getPassword() + "#" + playerData.getScore());
                } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
                    e.printStackTrace();
                    continue;
                }

                writer.append(content);
                writer.append("\n");
            }
        }
    }
}
