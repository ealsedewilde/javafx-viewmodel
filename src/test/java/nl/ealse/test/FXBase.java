package nl.ealse.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * All unit tests that need to run in the JavaFx application thread must extend this class
 */
public abstract class FXBase {
  private static final Logger LOG = LoggerFactory.getLogger(FXBase.class);

  /**
   * To enhance performance, all JavaFx test should run in the same tookit
   */
  static {
    Platform.startup(() -> {});
    Platform.setImplicitExit(false);
  }

  /**
   * Controlled run of some work in the JavaFx application thread.
   * @param work
   * @return true when successful
   */
  protected boolean runFX(Runnable work) {
    FutureTask<Boolean> task = new FutureTask<>(() -> {
      work.run();
      return true;
    });
    Platform.runLater(task);
    try {
      // Safeguard for hanging tests. Such tests time out after 6 seconds.
      return task.get(6, TimeUnit.SECONDS);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      LOG.error("Exception in Runnable", e);
      return false;
    }
  }

}
