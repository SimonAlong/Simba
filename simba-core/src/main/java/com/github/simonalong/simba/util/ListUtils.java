package com.github.simonalong.simba.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shizi
 * @since 2019/12/3 11:48 上午
 */
@UtilityClass
public class ListUtils {

    /**
     * list 分段，每一段数据大小
     */
    public List<List> split(List dataList, Integer splitNum) {
        if (null == dataList || dataList.isEmpty() || splitNum == 0) {
            return new ArrayList();
        }

        List<List> splitList = new ArrayList<>();
        Integer size = dataList.size();

        while ((size / splitNum) != 0) {
            splitList.add(dataList.subList(0, splitNum));
            dataList = dataList.subList(splitNum, size);
            size = dataList.size();
        }

        if (0 != size) {
            splitList.add(dataList.subList(0, size));
        }
        return splitList;
    }
}
