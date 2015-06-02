package com.bnpp.ism.diffmerge.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.bnpp.ism.diffmerge.stereotype.DiffMergeIgnore;

public class ReflectionUtil {
	Map<Class<?>, List<Field>> fieldsMap = new Hashtable<Class<?>, List<Field>>();
	Map<Class<?>, List<Field>> fieldsWithoutDiffMergeMap = new Hashtable<Class<?>, List<Field>>();
	Map<Field, Method> fieldsGetterMethods = new Hashtable<Field, Method>();
	Map<Class<?>, List<Method>> methodsMap = new Hashtable<Class<?>, List<Method>>();

	public List<Field> getAllFields(Object o) {
		return getAllFields(o.getClass());
	}

	public List<Field> getAllFieldsExcludingAnnotates(Object o,
			Class... annotations) {
		return getAllFieldsExcludingAnnotates(o.getClass(), annotations);
	}

	public List<Field> getAllFieldsExcludingAnnotates(Class<?> clazz,
			Class... annotations) {
		List<Field> all = getAllFields(clazz);
		// Now filter
		List<Field> toRemove = new ArrayList<Field>();
		for (Field f : all) {
			for (Class annotation : annotations) {
				if (f.getAnnotation(annotation) != null) {
					toRemove.add(f);
					break;
				}
			}
		}
		all.removeAll(toRemove);
		return all;
	}

	public List<Field> getAllFieldsExcludingDiffMergeIgnore(Class<?> clazz) {
		List<Field> fields = fieldsWithoutDiffMergeMap.get(clazz);
		if (fields != null) {
			return fields;
		} else {
			fields = getAllFieldsExcludingAnnotates(clazz,
					DiffMergeIgnore.class);
			fieldsWithoutDiffMergeMap.put(clazz, fields);
			return fields;
		}
	}

	public List<Field> getAllFields(Class<?> clazz) {
		List<Field> fields = fieldsMap.get(clazz);
		if (fields != null) {
			return fields;
		} else {
			fields = new ArrayList<Field>();
			getAllFields(clazz, fields);
			fieldsMap.put(clazz, fields);
			return fields;
		}
	}

	private void getAllFields(Class<?> clazz, List<Field> fields) {
		List<Field> existingFields = fieldsMap.get(clazz);
		if (existingFields != null) {
			fields.addAll(existingFields);
		} else {
			List<Field> ownedFields = Arrays.asList(clazz.getDeclaredFields());
			if (clazz.getSuperclass() != null) {
				getAllFields(clazz.getSuperclass(), fields);
			}
			fields.addAll(ownedFields);
			fieldsMap.put(clazz, fields);
		}
	}

	public List<Method> getAllMethods(Class<?> clazz) {
		List<Method> methods = methodsMap.get(clazz);
		if (methods != null) {
			return methods;
		} else {
			methods = new ArrayList<Method>();
			getAllMethods(clazz, methods);
			methodsMap.put(clazz, methods);
			return methods;
		}
	}

	private void getAllMethods(Class<?> clazz, List<Method> methods) {
		List<Method> existingMethods = methodsMap.get(clazz);
		if (existingMethods != null) {
			methods.addAll(existingMethods);
		} else {
			List<Method> ownedMethods = Arrays.asList(clazz
					.getDeclaredMethods());
			if (clazz.getSuperclass() != null) {
				getAllMethods(clazz.getSuperclass(), methods);
			}
			methods.addAll(ownedMethods);
			methodsMap.put(clazz, methods);
		}
	}

	public Method getGetter(Class<?> clazz, Field field) {
		Method m = fieldsGetterMethods.get(field);
		if (m != null) {
			return m;
		} else {
			String methodName;
			if (field.getType().isPrimitive()
					&& field.getType().equals(boolean.class)) {
				methodName = "is";
			} else {
				methodName = "get";
			}

			methodName += field.getName().substring(0, 1).toUpperCase()
					+ (field.getName().length() == 1 ? "" : field.getName()
							.substring(1));
			try {
				for (Method met : getAllMethods(clazz)) {
					if (met.getName().equals(methodName)
							&& met.getReturnType() == field.getType()) {
						met.setAccessible(true);
						fieldsGetterMethods.put(field, met);
						return met;
					}
				}
			} catch (SecurityException e) {
				return null;
			}
		}
		return null;
	}

	public boolean isCollection(Field f) {
		return Collection.class.isAssignableFrom(f.getType());
	}

	public BidirectionalAssociationInfo isBidirectionalAssociation(Field f) {
		BidirectionalAssociationInfo info = new BidirectionalAssociationInfo(
				false, false, null);

		if (isCollection(f)) {
			info.setAssociation(true);
			// Search is OneToMany, OneToOne, ManytoMany ou ManyToOne are
			// applied
			Annotation ann = null;
			if ((ann = f.getAnnotation(OneToMany.class)) != null) {
				String mappedBy = ((OneToMany) ann).mappedBy();
				if (mappedBy != null && mappedBy.length() > 0) {
					info.setBidirectional(true);
					info.setOpposite(mappedBy);
				}
			}else 
				if ((ann = f.getAnnotation(OneToOne.class)) != null) {
					String mappedBy = ((OneToOne) ann).mappedBy();
					if (mappedBy != null && mappedBy.length() > 0) {
						info.setBidirectional(true);
						info.setOpposite(mappedBy);
					}
				}
				else 
					if ((ann = f.getAnnotation(ManyToMany.class)) != null) {
						String mappedBy = ((ManyToMany) ann).mappedBy();
						if (mappedBy != null && mappedBy.length() > 0) {
							info.setBidirectional(true);
							info.setOpposite(mappedBy);
						}
					}
			
		}
		return info;
	}
}
