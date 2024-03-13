package com.example.minesweeperapi.controller;

import com.example.minesweeperapi.service.MinesweeperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class MinesweeperController {

    private final Map<String, MinesweeperService> games = new ConcurrentHashMap<>();

    @PostMapping("/new")
    public ResponseEntity<Object> createNewGame(@RequestBody Map<String, Integer> params) {
        try {
            int width = params.get("width");
            int height = params.get("height");
            int minesCount = params.get("mines_count");

            if (width <= 0 || height <= 0 || minesCount <= 0 || minesCount >= width * height) {
                throw new IllegalArgumentException("Invalid game parameters");
            }

            MinesweeperService game = new MinesweeperService();
            game.createNewGame(width, height, minesCount);
            String gameId = UUID.randomUUID().toString();
            games.put(gameId, game);

            Map<String, Object> response = new HashMap<>();
            response.put("game_id", gameId);
            response.put("width", width);
            response.put("height", height);
            response.put("mines_count", minesCount);
            response.put("field", game.getField());
            response.put("completed", game.isGameCompleted());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating new game: " + e.getMessage());
        }
    }

    @PostMapping("/turn")
    public ResponseEntity<Object> processTurn(@RequestBody Map<String, Object> params) {
        try {
            String gameId = (String) params.get("game_id");
            int row = (int) params.get("row");
            int col = (int) params.get("col");

            MinesweeperService game = games.get(gameId);
            if (game == null) {
                return ResponseEntity.badRequest().body("Game not found");
            }
            if (game.isGameCompleted()){
                return ResponseEntity.badRequest().body("Game is already completed");
            }
            game.processMove(row,col);

            Map<String,Object> response = new HashMap<>();
            response.put("game_id",gameId);
            response.put("width", game.getWidth());
            response.put("height", game.getHeight());
            response.put("mines_count", game.getMinesCount());
            response.put("field", game.getField());
            response.put("completed", game.isGameCompleted());

            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error processing turn: " + e.getMessage());
        }
    }
}
