package com.stusys.cattan.userclient.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode

public class ResultList<T> {
    public ResultList() {
    }

    private long total;
    private int page;
    private int size;
    private List<T> list;
}
