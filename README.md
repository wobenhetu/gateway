# 网关测试

bussinessservice

bussinessservice2

这两个是业务应用；



eurekaserver

这个是eureka注册发现服务器；网关和业务应用都要注册到eureka上面；



gateway

网关服务；基于springcloud2.0和gateway网关方式的网关服务；



**SockJS简单的说明：**

sockjs-client 是一个浏览器 JavaScript 库，提供类似WebSocket的对象。 SockJS提供了一个连贯的跨浏览器Javascript API，可在浏览器和Web服务器之间创建低延迟，全双工，跨域通信通道。默认使用 Websocket 作为 transport，否则会逐步降级使用如下的 transport：

xhr-streaming、xdr-streaming、eventsource、iframe-wrap-eventsource、htmlfile、iframe-wrap-htmlfile、xhr-polling、xdr-polling、iframe-wrap-xhr-polling、jsonp-polling。所有的 transports 都在 lib/transport-list.js 文件可以查到。从这些降级的方案可以来看，兼容性做的非常的好，而且降级的方法的使用和你使用原生的 Websocket 对象没有多大的区别，库自己磨平了不同方案的差异。



**客户端：**

![img](file:///F:/study/studynote/dfwmaomao@163.com/077bf37bb26a43029eb31a97d6e70301/clipboard.png)



![img](file:///F:/study/studynote/dfwmaomao@163.com/b9715ca0bc8b44688688fa1822ca616f/clipboard.png)

先向服务端发送 /info 接口来获取一些信息，这一步是必须的，这是 sockjs-protocol 协议规定的一步；

协议里：在客户端启动会话之前调用此URL。 它用于检查服务器功能（websocket支持，cookies requiremet）并获取“origin”设置的值（当前未使用）。

在url里面拼接参数，在网关里面过滤器里是可以拿到参数的；

添加的header参数，使用stomp进行连接的时候，网关过滤器里是拿不到这个header参数的；这个header可以用于建立连接后发送消息时候的header认证；





**服务端：主要是.withSockJS()**

![img](file:///F:/study/studynote/dfwmaomao@163.com/a3a589d49b7449329a2431489017ecc4/clipboard.png)