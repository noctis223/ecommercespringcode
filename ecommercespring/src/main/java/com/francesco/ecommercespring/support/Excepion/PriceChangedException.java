package com.francesco.ecommercespring.support.Excepion;

public class PriceChangedException extends Exception{
    private long pid;

    public long getPid() {
        return pid;
    }

    public PriceChangedException(long pid) {
        super();
        this.pid = pid;
    }
}
