package test.com.bnpp.ism.diffmerge;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.Id;

import org.junit.Test;

import com.bnpp.ism.diffmerge.reflection.ReflectionUtil;
import com.bnpp.ism.entity.kpi.metadata.ManualNumericKpi;

public class ReflectionUtilTests {
	@Test
	public void testReflectionFieldsRetrieve() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		ReflectionUtil reflection = new ReflectionUtil();
		List<Field> fields = reflection.getAllFields(kpi);
		System.out.println("all....");
		for (Field f :fields) {
			System.out.println(f.getName());
		}
	}
	
	@Test
	public void testReflectionFieldsRetrieveWithExcludedAnnotations() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		ReflectionUtil reflection = new ReflectionUtil();
		List<Field> fields = reflection.getAllFieldsExcludingAnnotates(kpi,Id.class);
		System.out.println("Without annotations @Id");
		for (Field f :fields) {
			System.out.println(f.getName());
		}
	}
	
	@Test
	public void testReflectionFieldsRetrieveWithoutDiffMergeIgnore() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		ReflectionUtil reflection = new ReflectionUtil();
		List<Field> fields = reflection.getAllFieldsExcludingDiffMergeIgnore(kpi.getClass());
		System.out.println("Without diffmergeignore");
		for (Field f :fields) {
			System.out.println(f.getName());
		}
		// Now search getter
		System.out.println("getters...");
		for (Field f :fields) {
			System.out.println(reflection.getGetter(kpi.getClass(), f));
		}
	}
}
