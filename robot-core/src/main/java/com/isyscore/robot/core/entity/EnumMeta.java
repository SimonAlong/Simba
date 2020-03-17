package com.isyscore.robot.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author shizi
 * @since 2019/12/3 11:48 上午
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
public class EnumMeta {
    @NonNull
    private String name;
    @NonNull
    private String desc;
}
