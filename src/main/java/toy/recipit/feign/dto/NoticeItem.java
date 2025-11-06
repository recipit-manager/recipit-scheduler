package toy.recipit.feign.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NoticeItem {
    private final String noticeNo;
    private final String userNo;
}
