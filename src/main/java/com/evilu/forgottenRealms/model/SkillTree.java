package com.evilu.forgottenRealms.model;

import java.util.List;
import java.util.stream.Collectors;

import com.evilu.forgottenRealms.skill.Skill;

/**
 * SkillTree
 */
public class SkillTree extends Tree<String, Skill> {

    public static SkillTree createRoot() {
        return new SkillTree(null, Skill.ROOT);
    }

    public static SkillTreeBuilder builder() {
        return new SkillTreeBuilder();
    }

    protected SkillTree(final SkillTree parent, final Skill skill) {
        super(parent, skill);
    }

    public SkillTree addSkill(final Skill skill)  {
        if (childs.containsKey(skill.getId())) {
            final SkillTree subTree = (SkillTree) childs.get(skill.getId());
            subTree.value = skill;
            return subTree;
        }

        final SkillTree subTree = new SkillTree(this, skill);
        childs.put(skill.getId(), subTree);
        return subTree;
    }

    @Override
    public SkillTree getParent() {
        return (SkillTree) parent;
    }

    public Skill getSkill() {
        return value;
    }

    public List<Skill> getChilds() {
        return childs.values()
            .stream()
            .map(Tree::getValue)
            .collect(Collectors.toList());
    }

    
}
