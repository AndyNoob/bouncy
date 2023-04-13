package me.comfortable_andy.bouncy.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public record ImmutableLocation(@NotNull World world, double x,
                                double y, double z, float yaw,
                                float pitch) {

    public @NotNull World getWorld() {
        return world();
    }

    public @NotNull Location makeMutableCopy(boolean nullWorld) {
        return new Location(nullWorld ? null : getWorld(), this.x, this.y, this.z,
                this.yaw, this.pitch);
    }

    public @NotNull Location makeMutableCopy() {
        return makeMutableCopy(false);
    }

    public double distance(@NotNull final ImmutableLocation other) {
        return Math.sqrt(distanceSquared(other));
    }

    public double distanceSquared(@NotNull final ImmutableLocation other) {
        return distanceSquared(other.makeMutableCopy());
    }

    public double distanceSquared(@NotNull final Location other) {
        return other.distanceSquared(makeMutableCopy());
    }

    public @NotNull Vector toVector() {
        return new Vector(this.x, this.y, this.z);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public @NotNull ImmutableLocation makeNewFromOffset(@NotNull final Vector vector) {
        return makeNewFromOffset(vector.getX(), vector.getY(), vector.getZ());
    }

    public @NotNull ImmutableLocation makeNewFromOffset(final double x, final double y, final double z) {
        return makeNewFromOffset(x, y, z, 0, 0);
    }

    public @NotNull ImmutableLocation makeNewFromOffset(final double x, final double y, final double z, final float yaw, final float pitch) {
        return new ImmutableLocation(world(), this.x + x, this.y + y, this.z + z, this.yaw + yaw, this.pitch + pitch);
    }

    public static @NotNull ImmutableLocation makeImmutableCopy(@NotNull final
                                                               Location location) {
        return new ImmutableLocation(location.getWorld(),
                location.getX(), location.getY(), location.getZ(),
                location.getYaw(), location.getPitch());
    }

    public Block getBlock() {
        return world().getBlockAt((int) this.x, (int) this.y, (int) this.z);
    }
}
