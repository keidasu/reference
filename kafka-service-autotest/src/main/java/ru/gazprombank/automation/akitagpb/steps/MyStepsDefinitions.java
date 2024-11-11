package ru.gazprombank.automation.akitagpb.steps;

import io.cucumber.java.ru.И;
import ru.gazprombank.automation.akitagpb.modules.core.steps.BaseMethods;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static ru.gazprombank.automation.akitagpb.modules.api.steps.ApiBaseMethods.sendRequest;

public class MyStepsDefinitions extends BaseMethods {

    @И("^ожидается (\\d+) секунд поднятия сервиса по адресу \"(.*)\"")
    public void serviceIsUpCheck(int seconds, String actuatorAddress) throws Exception {
        int responseCode = 0;
        for (int i = 0; i < seconds; i++) {
            try {
                responseCode = sendRequest("GET", actuatorAddress, new ArrayList<>()).getStatusCode();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            if (responseCode == 200) {
                break;
            }
            TimeUnit.SECONDS.sleep(1);
        }
        if (responseCode != 200) {
            throw new Exception("Истекло время ожидания запуска всех компонентов сервиса. Код ответа актуатора " + responseCode);
        }
    }

}