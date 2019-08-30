package com.yss.gateway.dynamicroute.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 动态路由服务
 * <p>
 * 可以通过 /actuator/gateway/routes 获取所有路由的json:
 * 比如这里：http://localhost:8888/actuator/gateway/routes
 * 得到的结果如下
 * <p>
 * [
 * {
 * "route_id": "gateway",
 * "route_definition": {
 * "id": "gateway",
 * "predicates": [
 * {
 * "name": "Path",
 * "args": {
 * "pattern": "/getbussiness/**"
 * }
 * }
 * ],
 * "filters": [
 * <p>
 * ],
 * "uri": "lb://bussiness-service",
 * "order": 0
 * },
 * "order": 0
 * }
 * ]
 */
@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;
    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }


    //增加路由
    public String add(RouteDefinition definition) {
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }

    //更新路由
    public String update(RouteDefinition definition) {
        try {
            delete(definition.getId());
        } catch (Exception e) {
            return "update fail,not find route  routeId: " + definition.getId();
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception e) {
            return "update route  fail";
        }
    }

    //删除路由
    public Mono<ResponseEntity<Object>> delete(String id) {
        return this.routeDefinitionWriter.delete(Mono.just(id)).then(Mono.defer(() -> {
            System.out.println("删除成功");
            return Mono.just(ResponseEntity.ok().build());
        })).onErrorResume((t) -> {
            System.out.println(t.getMessage().toString());
            return t instanceof NotFoundException;
        }, (t) -> {
            System.out.println(t.getMessage().toString());
            return Mono.just(ResponseEntity.notFound().build());
        });
    }


}