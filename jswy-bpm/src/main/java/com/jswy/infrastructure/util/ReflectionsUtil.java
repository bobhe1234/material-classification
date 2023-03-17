package com.jswy.infrastructure.util;

import java.util.Iterator;
import java.util.Set;

public class ReflectionsUtil {
	public static void main(String[] args) throws InterruptedException {
		while(true) {
			System.out.println("hello");
			Thread.sleep(1000);
		}
	}


	/**
	 * get all sub class of ConsumerProcessor, cache topic and processor class in
	 * topicProcessorMap
	 */
//	private synchronized void getSubClassType() {
////		Reflections reflections = new Reflections("com.lenovo.plm.pg.integration.kafka.consumer.processor");
//		Reflections reflections = new Reflections(ConsumerIntialDataFactory.class.getPackageName());
//		Set<Class<? extends ConsumerProcessor>> subTypes = reflections.getSubTypesOf(ConsumerProcessor.class);
//		Iterator<Class<? extends ConsumerProcessor>> iterator = subTypes.iterator();
//		while (iterator.hasNext()) {
//			Class<ConsumerProcessor> processorClass = (Class<ConsumerProcessor>) iterator.next();
//			// skip DefaultConsumerProcessor
//			if (processorClass.equals(DefaultConsumerProcessor.class)) {
//				continue;
//			}
//			consumerProcessorList.add(processorClass);
//		}
//
//	}
}
