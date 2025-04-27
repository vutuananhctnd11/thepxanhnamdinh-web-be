package com.graduate.be_txnd_fanzone.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageableListResponse <T> {

    Integer page;
    Integer limit;
    Long totalPage;
    List<T> listResults;
}
