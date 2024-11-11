package ru.gazprombank.automation.akitagpb.hooks;

import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleHook {

  /** Пример cucumber hook */
  @Before(order = 1)
  public void sampleHook() {
    log.info("#### SAMPLE HOOK ###");
//    Configuration.holdBrowserOpen = true;
  }
}
