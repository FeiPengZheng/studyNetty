package pseudo.asyn;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeServerHandlerExecutorPool {

    private ExecutorService executor;

    public TimeServerHandlerExecutorPool(int max, int queueSize){
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),max,120L, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(queueSize));
    }
    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
