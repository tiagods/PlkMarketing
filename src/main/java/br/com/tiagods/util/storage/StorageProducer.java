package br.com.tiagods.util.storage;

public class StorageProducer{
	public static Storage newConfig() {
		return new AWSStorage();
	}
	
}
