package com.panzegoria.puzzleBuilder.Commands;

import com.panzegoria.puzzleBuilder.Entities.IPlayersState;
import com.panzegoria.puzzleBuilder.Entities.PlayersState;
import com.panzegoria.puzzleBuilder.Entities.WrappedPlayer;
import com.panzegoria.puzzleBuilder.Services.IBlockService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by roger.boone on 6/11/2017.
 */
public class CommandLoad implements CommandExecutor {

    private IPlayersState _playersState;
    private File _dataDirectory;

    public CommandLoad(IPlayersState playersState, File dataDirectory) {
        _playersState = playersState;
        _dataDirectory = dataDirectory;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            if(strings[0] == null || strings[0].length()==0) return false;

            //Cast our player
            Player player = (Player) commandSender;
            WrappedPlayer wrappedPlayer = _playersState.getState(player.getName());

            String filename = makeFilename(strings[0]);

            String content = readFile(filename);
            wrappedPlayer.LoadedBlocks = content;

            player.sendMessage(String.format("You successfully loaded %s into your Map.", strings[0]));

            return true;
        }

        return false;
    }

    private String makeFilename(String nameIn) {
        nameIn = nameIn.replaceAll("[^a-zA-Z0-9.-]", "_");
        String folder = _dataDirectory.getAbsolutePath() + "\\Maps";

        File file = new File(folder);

        if(!(file.exists())) {
            file.mkdir();
        }

        return String.format(_dataDirectory.getAbsolutePath() + "\\Maps\\map-%s.txt", nameIn);
    }

    private String readFile(String filename) {
        File f = new File(filename);
        try {
            byte[] bytes = Files.readAllBytes(f.toPath());
            return new String(bytes,"UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
