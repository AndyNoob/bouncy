package me.comfortable_andy.bouncy.bouncible;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class BouncibleManager {

    private static final BouncibleManager INSTANCE = new BouncibleManager();
    private static final int INSTANTANEOUS_THRESHOLD = 1000;

    private final BukkitRunnable runnable;
    private final Set<Bouncible> bouncibles = new HashSet<>();

    private BouncibleManager() {
        this.runnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (Bouncible bouncible : bouncibles.toArray(Bouncible[]::new)) {
                    if (bouncible.tickCount++ < bouncible.properties.tickInterval()) continue;
                    bouncible.tickCount = 0;
                    if (bouncible.properties.instantaneous()) {
                        int i = 0;
                        while (bouncible.tick() && i < INSTANTANEOUS_THRESHOLD) {
                            i++;
                        }
                    } else if (!bouncible.tick()) bouncibles.remove(bouncible);
                }
            }
        };
    }

    public void start(@NotNull final JavaPlugin plugin) {
        try {
            this.runnable.getTaskId();
            throw new IllegalStateException("Can't start again");
        } catch (IllegalStateException ignored) {
            this.runnable.runTaskTimer(plugin, 0, 0);
        }
    }

    public void stop() {
        this.runnable.cancel();
    }

    public void startTicking(@NotNull final Bouncible bouncible) {
        this.bouncibles.add(bouncible);
    }

    public void stopTicking(@NotNull final Bouncible bouncible) {
        this.bouncibles.remove(bouncible);
    }

    public static BouncibleManager getInstance() {
        return INSTANCE;
    }

}
