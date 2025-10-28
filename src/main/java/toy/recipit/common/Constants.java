package toy.recipit.common;

public final class Constants {
    public interface GroupCode {
        String RECIPE_CATEGORY = "RC100";
    }

    public interface WeeklyRecipe {
        int RECOMMEND_COUNT = 10;
    }

    public interface RecommendType {
        String RECOMMEND = "RW01";
        String SUPPLEMENT = "RW02";
    }

    public interface Recipe {
        String RELEASE = "RS1";
        String PRIVATE = "RS2";
        String DRAFT = "RS3";
        String DELETED = "RS4";
    }

    public interface TimeStamp {
        String SEOUL = "Asia/Seoul";
    }
}
