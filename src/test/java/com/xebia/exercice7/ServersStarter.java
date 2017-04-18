package com.xebia.exercice7;


import org.junit.rules.ExternalResource;

public class ServersStarter extends ExternalResource {

    private FirstRemoteServer firstRemoteServer = new FirstRemoteServer();

    private SecondRemoteServer secondRemoteServer = new SecondRemoteServer();

    private MyAppServer appServer = new MyAppServer();

    @Override
    protected void before() {
        firstRemoteServer.start();
        secondRemoteServer.start();
        appServer.start();
    }

    @Override
    protected void after() {
        firstRemoteServer.stop();
        secondRemoteServer.stop();
        appServer.stop();
    }
}
