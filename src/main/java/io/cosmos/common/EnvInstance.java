package io.cosmos.common;

public class EnvInstance {


    static EnvBase env = new LocalEnv();

    static public void setEnv(String type) {
    }

    static public EnvBase getEnv() {
        return env;
    }
}
