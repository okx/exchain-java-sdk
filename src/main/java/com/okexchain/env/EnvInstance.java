package com.okexchain.env;

public class EnvInstance {

    static EnvBase env = new LocalEnv("http://localhost:8545");

    static public void setEnv(String type) {
    }

    static public void setEnv(EnvBase env) {
        EnvInstance.env = env;
    }

    static public EnvBase getEnv() {
        return env;
    }
}
