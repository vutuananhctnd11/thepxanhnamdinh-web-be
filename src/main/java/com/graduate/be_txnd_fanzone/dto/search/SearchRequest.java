package com.graduate.be_txnd_fanzone.dto.search;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchRequest {

    int page;
    int limit;
    String search;
}
