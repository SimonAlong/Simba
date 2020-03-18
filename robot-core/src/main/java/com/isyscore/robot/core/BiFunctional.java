package com.isyscore.robot.core;

/**
 * @author shizi
 * @since 2020/3/18 下午6:53
 */
@FunctionalInterface
public interface BiFunctional<K1, K2, K3, V>  {

    V apply(K1 k1, K2 k2, K3 k3);
}
