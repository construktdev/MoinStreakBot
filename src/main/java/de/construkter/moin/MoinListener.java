package de.construkter.moin;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MoinListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase();
        String moin = "moin";
        if (event.getChannel().getId().equals("69")) { //Mit Kanal ID ersetzen :)
            if (message.contains(moin)) {
                String currentUser = event.getAuthor().getName();
                String lastUser = readLastUserFromFile();

                if (lastUser != null && lastUser.equals(currentUser)) {
                    event.getMessage().delete().queue();
                }
                saveUserToFile(currentUser);
            } else {
                event.getMessage().delete().queue();
            }
        }
    }


    private String readLastUserFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("last_name.txt"))) {
            String lastLine = null;
            String line;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }
            return lastLine;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveUserToFile(String username) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("last_name.txt", true))) {
            writer.write(username + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
