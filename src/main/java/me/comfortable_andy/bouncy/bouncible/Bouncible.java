package me.comfortable_andy.bouncy.bouncible;

import me.comfortable_andy.bouncy.util.ImmutableLocation;
import org.bukkit.FluidCollisionMode;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public abstract class Bouncible {

    int tickCount;

    protected final Properties properties;

    protected ImmutableLocation location;
    protected Vector currentDirection;
    protected int bounceCount = 0;

    public Bouncible(Properties properties, ImmutableLocation location, Vector currentDirection) {
        this.properties = properties;
        this.location = location;
        this.currentDirection = currentDirection;
    }

    /**
     * @return if this bouncible instance is still alive
     */
    public boolean tick() {
        if (this.bounceCount++ > this.properties.maxTickLife()) return false;
        if (!display()) return false;

        final RayTraceResult result = this.location.world().rayTraceBlocks(location.makeMutableCopy(), currentDirection, properties.speedInBlocksPerTick, FluidCollisionMode.NEVER, true);

        if (result != null
                && result.getHitBlock() != null
                && result.getHitBlockFace() != null && bounced(result)) {
            this.currentDirection = getReflectedDirection(result);
            this.location = ImmutableLocation.makeImmutableCopy(result.getHitPosition().toLocation(location.world()));
        } else this.location = location.makeNewFromOffset(this.currentDirection.clone().multiply(this.properties.speedInBlocksPerTick()));

        this.currentDirection.multiply(1 - this.properties.speedDecrementPercentPerTick());

        if (!currentDirection.isNormalized()) currentDirection.normalize();

        return true;
    }

    protected Vector getReflectedDirection(@NotNull final RayTraceResult result) {
        assert result.getHitBlockFace() != null;
        final Vector normal = result.getHitBlockFace().getDirection();
        final Vector direction = this.currentDirection.clone();
        final double upwardsOffset = -normal.dot(direction) * 2;
        return direction.add(normal.multiply(upwardsOffset));
    }

    /**
     * @return should this instance keep existing?
     */
    public abstract boolean display();

    /**
     * @param result the hit (it is assumed that the block and blockface are non-null)
     * @return should this bounce count?
     */
    public abstract boolean bounced(@NotNull final RayTraceResult result);

    public record Properties(double speedInBlocksPerTick, double gravityInBlocksPerTick, int maxTickLife, double speedDecrementPercentPerTick, int tickInterval, boolean instantaneous) {
    }

}
