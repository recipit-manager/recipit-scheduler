package toy.recipit.feign.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class NoticeRequest {
    private final List<NoticeItem> noticeItems;
}
