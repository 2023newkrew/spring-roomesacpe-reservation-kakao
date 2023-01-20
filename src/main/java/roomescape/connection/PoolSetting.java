package roomescape.connection;

public class PoolSetting {

    private final int maxPool;

    public PoolSetting(final int maxPool) {
        this.maxPool = maxPool;
    }

    public int getMaxPool() {
        return maxPool;
    }
}
