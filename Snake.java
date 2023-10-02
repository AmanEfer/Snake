package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    public Snake(int x, int y) {
        GameObject partFirst = new GameObject(x, y);
        GameObject partSecond = new GameObject(x + 1, y);
        GameObject partThird = new GameObject(x + 2, y);
        snakeParts.add(partFirst);
        snakeParts.add(partSecond);
        snakeParts.add(partThird);
    }

    private List<GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    public void setDirection(Direction direction) {
        boolean isCorrectX = snakeParts.get(0).getX() == snakeParts.get(1).getX();
        boolean isCorrectY = snakeParts.get(0).getY() == snakeParts.get(1).getY();
        if (direction == Direction.LEFT && this.direction != Direction.RIGHT && isCorrectX) {
            this.direction = direction;
        }
        if (direction == Direction.RIGHT && this.direction != Direction.LEFT && isCorrectX) {
            this.direction = direction;
        }
        if (direction == Direction.UP && this.direction != Direction.DOWN && isCorrectY) {
            this.direction = direction;
        }
        if (direction == Direction.DOWN && this.direction != Direction.UP && isCorrectY) {
            this.direction = direction;
        }
    }

    public void draw(Game game) {
        for (GameObject part : snakeParts) {
            if (part == snakeParts.get(0)) {
                game.setCellValueEx(part.getX(), part.getY(), Color.NONE, HEAD_SIGN, isAlive ? Color.BLACK : Color.RED, 75);
            } else {
                game.setCellValueEx(part.getX(), part.getY(), Color.NONE, BODY_SIGN, isAlive ? Color.BLACK : Color.RED, 75);
            }
        }
    }

    public void move(Apple apple) {
        GameObject head = createNewHead();

        if (head.getX() < 0
                || head.getY() < 0
                || head.getX() >= SnakeGame.WIDTH
                || head.getY() >= SnakeGame.HEIGHT
                || checkCollision(head)) {
            isAlive = false;
        } else if (head.getX() == apple.getX() && head.getY() == apple.getY()) {
            apple.isAlive = false;
            snakeParts.add(0, head);
        } else {
            snakeParts.add(0, head);
            removeTail();
        }
    }

    public GameObject createNewHead() {
        if (direction == Direction.LEFT) {
            return new GameObject(snakeParts.get(0).getX() - 1, snakeParts.get(0).getY());
        } else if (direction == Direction.RIGHT) {
            return new GameObject(snakeParts.get(0).getX() + 1, snakeParts.get(0).getY());
        } else if (direction == Direction.UP) {
            return new GameObject(snakeParts.get(0).getX(), snakeParts.get(0).getY() - 1);
        } else if (direction == Direction.DOWN) {
            return new GameObject(snakeParts.get(0).getX(), snakeParts.get(0).getY() + 1);
        } else {
            return null;
        }
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public boolean checkCollision(GameObject object) {
        return snakeParts.stream()
                .anyMatch(part -> part.getX() == object.getX() && part.getY() == object.getY());
    }

    public int getLength() {
        return snakeParts.size();
    }
}