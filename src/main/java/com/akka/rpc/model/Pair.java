package com.akka.rpc.model;/* 
    create qiangzhiwei time 2023/11/14
 */

public class Pair<P, E> {
    private final P p;

    private final E e;

    public Pair(P p, E e) {
        this.p = p;
        this.e = e;
    }

    public P getP() {
        return p;
    }

    public E getE() {
        return e;
    }
}
