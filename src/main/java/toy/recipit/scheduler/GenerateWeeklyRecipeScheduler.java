package toy.recipit.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import toy.recipit.mapper.TestMapper;

@Slf4j
@Component
@EnableScheduling
public class GenerateWeeklyRecipeScheduler {
    private final TestMapper testMapper;

    public GenerateWeeklyRecipeScheduler(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void generateWeeklyRecipe() {
        log.info(testMapper.test());
    }
}
