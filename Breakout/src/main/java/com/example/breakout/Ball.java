package com.example.breakout;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Ball {
    double x, y;
    double vx, vy;
    double radius;
    Color color;
    public static int schianti = 0;

    public Ball(double x, double y, double vx, double vy) {
        Random random = new Random();

        this.x = x;
        this.y = y;
        this.vx = (random.nextDouble() * 4 + 1) * (random.nextBoolean() ? 1 : -1);;
        this.vy = (random.nextDouble() * 4 + 1) * (random.nextBoolean() ? 1 : -1);;
        this.radius = 15;
        this.color = Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }

    public void update() {
        x += vx; // Posizione += velocità
        y += vy;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    public void checkWalldistance() {
        if (x - radius < 0) {
            x = radius;
            vx = -vx;
        }
        if (x + radius > 800) {
            x = 800 - radius;
            vx = -vx;
        }
        if (y - radius < 0) {
            y = radius;
            vy = -vy;
        }
        if (y + radius > 600) {
            y = 600 - radius;
            vy = -vy;
        }
    }

    public static void checkBalldistance(Ball a, Ball b){
        double dx = b.x - a.x;
        double dy = b.y - a.y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist == 0 || dist >= a.radius + b.radius){
            return;
        }

        double vetx = dx / dist;
        double vety = dy / dist;
        double velx = a.vx - b.vx;
        double vely = a.vy - b.vy;
        double vel = velx * vetx + vely * vety;

        if (vel <= 0){
            return;
        }

        b.vx += vel * vetx;
        b.vy += vel * vety;
        a.vx -= vel * vetx;
        a.vy -= vel * vety;
        double overlap = (a.radius + b.radius - dist) / 2.0;
        a.x -= overlap * vetx;
        a.y -= overlap * vety;
        b.x += overlap * vetx;
        b.y += overlap * vety;
        schianti++;
        Random casuale =  new Random();
        b.color = Color.color(casuale.nextDouble(), casuale.nextDouble(), casuale.nextDouble());
        a.color = Color.color(casuale.nextDouble(), casuale.nextDouble(), casuale.nextDouble());
    }
}