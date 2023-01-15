package nextstep.step3.dao;

public class DatabaseUtil {
    public static void close(AutoCloseable... closeables){
        for (AutoCloseable closeable : closeables) {
            try {
                if(closeable != null){
                    closeable.close();
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
    }
}

