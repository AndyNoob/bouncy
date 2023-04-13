package me.comfortable_andy.bouncy.bouncible.implementations;

import me.comfortable_andy.bouncy.bouncible.Bouncible;
import me.comfortable_andy.bouncy.util.ImmutableLocation;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class Laser extends Bouncible {

    private static final Properties LASER_PROPERTIES = new Properties(0.5, 0, 300, 0, 0, true);

    public Laser(Location location, Vector currentDirection) {
        super(LASER_PROPERTIES, ImmutableLocation.makeImmutableCopy(location), currentDirection);
    }

    @Override
    public boolean display() {
        location.world().spawnParticle(Particle.REDSTONE, location.makeMutableCopy(), 1, 0, 0, 0, 0, new Particle.DustOptions(Color.RED, 0.5f), true);

        return true;
    }

    @Override
    public boolean bounced(@NotNull RayTraceResult result) {
        System.out.println("Bounced (" + result.getHitBlockFace() + ")");
        return true;
    }
}
