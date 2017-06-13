package com.panzegoria.puzzleBuilder.Commands;

import com.panzegoria.puzzleBuilder.Entities.IPlayersState;
import com.panzegoria.puzzleBuilder.Entities.ISelection;
import com.panzegoria.puzzleBuilder.Entities.PlayersState;
import com.panzegoria.puzzleBuilder.Entities.WrappedBlockSet;
import com.panzegoria.puzzleBuilder.Services.BlockService;
import com.panzegoria.puzzleBuilder.Services.IBlockService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;

/**
 * Created by roger.boone on 6/11/2017.
 */
public class CommandBlueprintSave implements CommandExecutor {

    private PlayersState _playersState;
    private File _dataDirectory;
    private IBlockService _blockService;

    public CommandBlueprintSave(IBlockService blockService, IPlayersState playersState, File dataDirectory) {
        _playersState = playersState;
        _dataDirectory = dataDirectory;
        _blockService = blockService;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            if(strings[0] == null || strings[0].length()==0) return false;

            //Cast our player
            Player player = (Player) commandSender;
            ISelection selection = _playersState.getState(player.getName()).Selection;
            WrappedBlockSet blockSet = _blockService.getBlocks(player.getWorld(),selection);

            String dataOut = blockSet.toString();
            String filename = makeFilename(strings[0]);

            BufferedWriter writer = null;
            try
            {
                writer = new BufferedWriter( new FileWriter( filename));
                writer.write( dataOut);

            }
            catch ( IOException e)
            {
            }
            finally
            {
                try
                {
                    if ( writer != null)
                        writer.close( );
                }
                catch ( IOException e)
                {
                }
            }
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
}
