package io.cosmos.common;

public class EnvInstance {

    static EnvBase localEnv = new LocalEnv();
    static EnvBase okEnv = new OKEnv();

    static EnvBase env = localEnv;

    static public void setEnv(String type) {

        if (type == "ok") {
            env = okEnv;
        } else {
            env = localEnv;
        }
    }

    static public EnvBase getEnv() {
        return env;
    }
}
