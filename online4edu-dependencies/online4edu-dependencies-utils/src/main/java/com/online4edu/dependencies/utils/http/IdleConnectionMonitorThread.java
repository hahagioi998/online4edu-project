package com.online4edu.dependencies.utils.http;

import org.apache.http.conn.HttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

/**
 * 监控线程池 {@link HttpClientConnectionManager} 过期及空闲的连接
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/12/24 20:44
 */
class IdleConnectionMonitorThread extends Thread {

    private volatile boolean shutdown;
    private final HttpClientConnectionManager connMgr;

    public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
        super();
        this.connMgr = connMgr;
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(3000);
                    // Close expired connections
                    connMgr.closeExpiredConnections();
                    // Optionally, close connections
                    // that have been idle longer than 30 sec
                    connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                }
            }
        } catch (InterruptedException ex) {
            // terminal
        }
    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }

}
