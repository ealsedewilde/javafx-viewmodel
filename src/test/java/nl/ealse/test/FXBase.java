package nl.ealse.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FXBase {
  private static final Logger LOG = LoggerFactory.getLogger(FXBase.class);
  static {
    Platform.startup(() -> {});
    Platform.setImplicitExit(false);
  }

  protected boolean runFX(Callable<Boolean> work) {
    FutureTask<Boolean> task = new FutureTask<>(work);
    Platform.runLater(task);
    try {
      return task.get(6, TimeUnit.SECONDS);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      LOG.error("Exception in Runnable", e);
      return false;
    }
  }

}
