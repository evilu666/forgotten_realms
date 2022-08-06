package com.evilu.forgottenRealms.skill;

/**
* SkillWithData
*/
public interface SkillWithData<T extends SkillData<T>> {

	public T createEmpty();

	public Class<T> getDataClass();
	
}
