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

    public interface TimeZone {
        String SEOUL = "Asia/Seoul";
    }

    public interface SystemId {
        String SYSTEM_NUMBER = "0";
    }

    public interface Yn {
        String YES = "Y";
        String NO = "N";
    }

    public interface NoticeType {
        String DRAFT = "NT01";
        String USER_REACTION = "NT02";
        String RECIPE_STATUS = "NT03";
    }
}
