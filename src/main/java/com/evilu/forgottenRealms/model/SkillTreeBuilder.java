package com.evilu.forgottenRealms.model;

import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;

import com.evilu.forgottenRealms.skill.Skill;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * SkillTreeBuilder
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SkillTreeBuilder {

    private final List<SkillTreeBuilder> children = new Vector<>();

    private Skill skill = Skill.ROOT;

    public SkillTreeBuilder withCustomRoot(final Skill skill) {
        this.skill = skill;
        return this;
    }

    public SkillTreeBuilder addChild(final Skill skill) {
        children.add(new SkillTreeBuilder(skill));
        return this;
    }
    public SkillTreeBuilder addChild(final Skill skill, final Consumer<SkillTreeBuilder> childBuilder) {
        final SkillTreeBuilder builder = new SkillTreeBuilder(skill);
        children.add(builder);

        childBuilder.accept(builder);
        return this;
    }

    public SkillTree build() {
        return build(null);
    }

    public SkillTree build(final SkillTree parent) {
        final SkillTree tree = new SkillTree(parent, skill);
        children.stream()
            .map(stb -> stb.build(tree))
            .forEach(st -> tree.putChild(st.getSkill().getId(), st));
        return tree;
    }
    
}
