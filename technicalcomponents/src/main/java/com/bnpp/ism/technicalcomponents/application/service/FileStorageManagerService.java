package com.bnpp.ism.technicalcomponents.application.service;

public interface FileStorageManagerService {
	String store(String context,String name, byte[] content,boolean force);
}
