## OKChain Java SDK

The OKChain Java SDK is a lightweight Java library to interact with OKChain. 

### 1.Components

- src - the functions which could be invoked to interact with OKChain are contained in class `OKChainRPCClientImpl` of package `com.okchain.client.impl`. 
- test - it seems like a set of examples to show how to use the functions mentioned above. The developers could design their code refering to the test functions of class `OKChainRPCClientImplTest` in package `com.okchain.client` in detail.

There are some function modules in path `src` as well :

- common - network communications by RPC；

- crypto - related with the account info and address info of OKChain；
- encoding - the implementation of go-amino in Java;
- proto - the base of amino with protobuf3;
- transaction - how to build an available transaction for OKChain;
- types -  all the message types used in Java SDK;

### 2.Installation

```
git clone https://github.com/okex/okchain-java-sdk.git
```

### 3.API

you can find it on the wiki : [okchain wiki](https://okchain-docs.readthedocs.io/zh_CN/latest/api/sdk/java-sdk.html)

### 4.Tesing

All changes and addition of codes will be pushed with unit tests strictly. Right now you can find existing test cases : github.com/okex/okchain-java-sdk/src/test

### 5.Contributing

No doubt that it's admirable to make contributions to OKChain Java SDK. You can provide your code as long as you have tested it with a local client and your unit test showed its validity.  
