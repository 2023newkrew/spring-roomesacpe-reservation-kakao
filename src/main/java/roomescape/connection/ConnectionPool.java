package roomescape.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.Semaphore;

public class ConnectionPool {
    private final ConnectionSetting connectionSetting;
    private final Stack<Connection> freePool = new Stack<>();
    private final Set<Connection> usedPool = new HashSet<>();
    private final Semaphore semaphore;

    public ConnectionPool(ConnectionSetting connectionSetting, PoolSetting poolSetting)
            throws SQLException, InterruptedException {
        this.connectionSetting = connectionSetting;
        this.semaphore = new Semaphore(poolSetting.getMaxPool());
        openConnection(poolSetting.getMaxPool());
    }

    private void openConnection(int num) throws InterruptedException, SQLException {
        semaphore.acquire(num);
        for (int i = 0; i < num; i++) {
            Connection connection = connectionSetting.getConnection();
            freePool.addElement(connection);
            semaphore.release();
        }
    }

    private void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Connection getConnection() throws InterruptedException {
        semaphore.acquire();
        Connection connection = freePool.pop();
        usedPool.add(connection);
        return connection;
    }

    public void releaseConnection(Connection connection) {
        usedPool.remove(connection);
        freePool.push(connection);
        semaphore.release();
    }

    public void close() {
        usedPool.forEach(this::closeConnection);
        freePool.forEach(this::closeConnection);
    }
}
