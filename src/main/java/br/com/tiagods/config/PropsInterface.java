package br.com.tiagods.config;

public interface PropsInterface {
	void fileLoad(PropsEnum propsEnum);
	String getValue(String key);
}
