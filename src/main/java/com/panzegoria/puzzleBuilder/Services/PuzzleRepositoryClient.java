package com.panzegoria.puzzleBuilder.Services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.panzegoria.puzzleBuilder.Entities.WrappedBlockSet;
import com.panzegoria.puzzleBuilder.PuzzleBuilderPlugin;
import com.panzegoria.puzzleBuilder.Services.Capabilities.PuzzlePersistence;

/**
 * Created by roger.boone on 7/5/2017.
 */
public class PuzzleRepositoryClient implements PuzzlePersistence {

    private String REST_URI;

    public PuzzleRepositoryClient(String url) {
        REST_URI = url;
    }

    @Override
    public void savePuzzle(String name, WrappedBlockSet puzzle) {
        String uri = String.format("%s/post?name=%s",REST_URI, name);

        String puzzleData = PuzzleBuilderPlugin.gson.toJson(puzzle.getMap());

        HttpResponse<JsonNode> jsonResponse = null;

        try {
            jsonResponse = Unirest.post(uri)
                    .header("accept", "application/json")
                    .queryString("name", name)
                    .field("puzzle", puzzleData)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        if(jsonResponse.getStatus() == 200) {
            //success
        }
    }

    @Override
    public WrappedBlockSet loadPuzzle(String name) {
        return null;
    }
}
