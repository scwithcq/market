package com.fmcd.market.Common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageData<T> {
    private long total;      // 总记录数
    private int pageSize;    // 每页大小
    private int totalPage;   // 总页数
    private int currPage;    // 当前页码
    private List<T> item;    // 数据列表
}
