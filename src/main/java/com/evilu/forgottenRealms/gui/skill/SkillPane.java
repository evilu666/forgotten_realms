package com.evilu.forgottenRealms.gui.skill;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.skill.Skill;
import com.ldtteam.blockui.Loader;
import com.ldtteam.blockui.Pane;
import com.ldtteam.blockui.PaneBuilders;
import com.ldtteam.blockui.PaneParams;
import com.ldtteam.blockui.controls.Button;
import com.ldtteam.blockui.controls.ButtonHandler;
import com.ldtteam.blockui.controls.ButtonImage;
import com.ldtteam.blockui.controls.Tooltip;
import com.ldtteam.blockui.views.View;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

/**
* SkillPane
*/
public class SkillPane extends View implements ButtonHandler {

	public static final ResourceLocation LOCATION = new ResourceLocation(ForgottenRealmsMod.MODID, "gui/skill_pane.xml");

	public static SkillPane create(final String id, final View parent, final Skill skill) {
		Loader.createFromXMLFile(LOCATION, parent);

		final SkillPane pane = parent.getChildren()
						.stream()
						.filter(SkillPane.class::isInstance)
						.map(SkillPane.class::cast)
						.filter(sp -> sp.getID() != null)
						.findFirst()
						.orElseThrow();

		pane.setID(id);
		pane.setSkill(skill);

		return pane;
	}

	static {
		Loader.INSTANCE.register("skill_pane", SkillPane::new);
		Pane.debugging = true;
	}

	private Skill skill = null;

	public SkillPane(final PaneParams params) {
		super(params);
	}

	public void setSkill(final Skill skill) {
		final ButtonImage button = findPaneOfTypeByID("skill_button", ButtonImage.class);
		button.setImage(skill.getIcon(), false);
		button.setText(skill.getName());
		button.setTextScale(3d);
		final Tooltip tooltip = PaneBuilders.tooltipBuilder()
				.append(skill.getName())
				.paragraphBreak()
				.append(skill.getDescription())
				.hoverPane(button)
				.build();
		tooltip.setTextScale(3d);
		tooltip.setSize(300, 100);
		button.setHoverPane(tooltip);

		this.skill = skill;
	}

	public Skill getSkill() {
		return skill;
	}

	@Override
	public void onButtonClicked(final Button button) {
		//TODO: trigger skill selection
	}

	
}
