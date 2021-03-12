//package com.wxw.manager.tools;
//
//import io.jaegertracing.internal.JaegerTracer;
//import io.jaegertracing.internal.metrics.Metrics;
//import io.jaegertracing.internal.metrics.NoopMetricsFactory;
//import io.jaegertracing.internal.reporters.CompositeReporter;
//import io.jaegertracing.internal.reporters.LoggingReporter;
//import io.jaegertracing.internal.reporters.RemoteReporter;
//import io.jaegertracing.internal.samplers.ConstSampler;
//import io.jaegertracing.thrift.internal.senders.HttpSender;
//
///**
// * @author ：wxw.
// * @date ： 10:47 2021/3/12
// * @description：
// * @link:
// * @version: v_0.0.1
// */
//public class ToolsJaegerTracer {
//    public static JaegerTracer initTracer(String service) {
//
//        final String endPoint = "http://wxw.plus:6830/open";
//
//        final CompositeReporter compositeReporter = new CompositeReporter(
//                new RemoteReporter.Builder()
//                        .withSender(new HttpSender.Builder(endPoint).build())
//                        .build(),
//                new LoggingReporter()
//        );
//
//        final Metrics metrics = new Metrics(new NoopMetricsFactory());
//
//        JaegerTracer.Builder builder = new JaegerTracer.Builder(service)
//                .withReporter(compositeReporter)
//                .withMetrics(metrics)
//                .withExpandExceptionLogs()
//                .withSampler(new ConstSampler(true));
//
//        return builder.build();
//    }
//}
