package com.wxw.jaeger;

import io.opentracing.Span;
import io.opentracing.Tracer;

/**
 * @author ：wxw.
 * @date ： 10:45 2021/3/12
 * @description：
 * @link:
 * @version: v_0.0.1
 */
public class DemoOpenTrancing {

    Tracer tracer;

    public static void main(String[] args) {
        new DemoOpenTrancing().hello("Java 半颗糖");
    }


    public DemoOpenTrancing() {
//        tracer = ToolsJaegerTracer.initTracer("demoOpenTrancingService");
    }

    private void hello(String name) {
        Span span = tracer.buildSpan("hello").start();
        span.setTag("name", name);
        System.out.println("Hello " + name);
        span.log("Love service say hello to " + name);
        span.finish();
    }

}
