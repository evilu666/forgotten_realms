package com.evilu.forgottenRealms.gui.skill;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.model.Reference;
import com.evilu.forgottenRealms.model.SkillTree;
import com.evilu.forgottenRealms.model.Tree;
import com.evilu.forgottenRealms.model.Tree.BoundNode;
import com.evilu.forgottenRealms.networking.PacketDispatcher;
import com.evilu.forgottenRealms.networking.packet.SkillPickedHandler;
import com.evilu.forgottenRealms.skill.Skill;
import com.evilu.forgottenRealms.skill.SkillsData;
import com.evilu.forgottenRealms.skill.SkillRequirement;
import com.ldtteam.blockui.Pane;
import com.ldtteam.blockui.PaneBuilders;
import com.ldtteam.blockui.controls.AbstractTextBuilder.TooltipBuilder;
import com.ldtteam.blockui.controls.Button;
import com.ldtteam.blockui.controls.ButtonHandler;
import com.ldtteam.blockui.controls.ButtonImage;
import com.ldtteam.blockui.controls.Tooltip;
import com.ldtteam.blockui.views.BOWindow;
import com.ldtteam.blockui.views.Group;
import com.ldtteam.blockui.views.ZoomDragView;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.abego.treelayout.Configuration;
import org.abego.treelayout.NodeExtentProvider;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

/**
 * SkillWindow
 */
public class SkillWindow extends BOWindow implements ButtonHandler, NodeExtentProvider<Tree<String, Skill>>, Configuration<Tree<String, Skill>> {

	private final ZoomDragView view;
	private final Tree<String, BoundNode<Skill>> outlayedSkillTree;
	private final SkillTree skillTree;
	private final Reference<SkillsData> skillsData;

	private final Player player;
	private final InteractionHand hand;

	private static final double SKILL_ICON_SIZE = 256d;

	public SkillWindow(final SkillTree skillTree, final Reference<SkillsData> skillsData, final Player player, final InteractionHand hand) {
		super(new ResourceLocation(ForgottenRealmsMod.MODID, "gui/skill_window.xml"));
		this.skillTree = skillTree;
		this.skillsData = skillsData;
		this.player = player;
		this.hand = hand;

		final Group group = findPaneByType(Group.class);
		view = new ZoomDragView() {
			@Override
			protected void abstractDrawSelfPre(final PoseStack ms, final double mx, final double my) {
				SkillWindow.this.outlayedSkillTree.parentChildPreorderTraversal((parent, child) -> {
					if (parent != null) {
						final Pane parentPane = findPaneByID(parent.getValue().getId());
						final Pane childPane = findPaneByID(child.getValue().getId());
						if (parentPane.isVisible() && childPane.isVisible()) {
							line(ms, (int) parent.getBounds().getCenterX(), (int) parent.getBounds().getCenterY(), (int) child.getBounds().getCenterX(), (int) child.getBounds().getCenterY(), 0, 200, 0, 255);
						}
					}
				});

				super.abstractDrawSelfPre(ms, mx, my);
			}
		};
		view.setWindow(this);
		view.setPosition(0, 50);
		view.setSize(3200, 1600);

		group.addChild(view);

		this.outlayedSkillTree = skillTree.createOutlayedTree(this, this);

		this.outlayedSkillTree.parentChildPreorderTraversal((parent, child) -> {
				final Skill skill = child.getValue();

				final ButtonImage button = new ButtonImage();
				button.setID(skill.getId());
				button.setImage(skill.getIcon(), false);
				button.setText(skill.getName());
				button.setWindow(this);

				TooltipBuilder tooltipBuilder = PaneBuilders.tooltipBuilder()
						.hoverPane(button)
						.bold()
						.append(skill.getName())
						.paragraphBreak()
						.bold(false)
						.append(skill.getDescription());

				if (!skill.getRequirements().isEmpty()) {
					tooltipBuilder = tooltipBuilder
						.paragraphBreak()
						.bold()
						.appendNL(new TranslatableComponent("forgottenRealms.gui.skillWindow.skillRequirements"))
						.bold(false);

					final List<SkillRequirement> requirements = skill.getRequirements()
						.stream()
						.collect(Collectors.toList());

					Collections.sort(requirements, Comparator.comparingInt(SkillRequirement::getOrder));

					for (final SkillRequirement req : requirements) {
						if (req.isVisible()) {
							tooltipBuilder = tooltipBuilder
								.append(new TextComponent(" - "))
								.appendNL(req.getDescription());
						} else {
							tooltipBuilder = tooltipBuilder
								.appendNL(new TextComponent(" - ???"));
						}
					}
				}

				final Tooltip tooltip = tooltipBuilder.build();

				tooltip.setTextScale(3d);
				tooltip.setSize(300, 100);
				button.setHoverPane(tooltip);

				button.setSize((int) SKILL_ICON_SIZE, (int) SKILL_ICON_SIZE);
				button.setPosition((int) child.getBounds().x, (int) child.getBounds().y);
				view.addChild(button);
		});

		refresh();
	}

	private void refresh() {
		final Set<Skill> unlockedSkillsCache = new HashSet<>();
		outlayedSkillTree.forEach(node -> {
			if (!node.isRoot()) {
				final Skill skill = node.getValue().getValue();
				final Button button = (Button) findPaneByID(skill.getId());
				final boolean isVisible = skill.areVisibilityRequirementsMet(skillTree, skillsData.get(), unlockedSkillsCache);
				final boolean isEnabled = isVisible && skill.areSkillRequirementsMet(skillTree, skillsData.get(), unlockedSkillsCache);
				if (isEnabled) unlockedSkillsCache.add(skill);
				button.setVisible(isVisible);
				button.setEnabled(isEnabled);
			}
		});
	}


	@Override
	public void onButtonClicked(final Button button) {
		skillTree.findRecursive(button.getID())
			.map(Tree::getValue)
			.ifPresent(skill -> {
				if (skill.areSkillRequirementsMet(skillTree, skillsData.get(), Set.of())) {
					skill.getRequirements().forEach(req -> req.apply(skillsData.get()));
					PacketDispatcher.getHandler(SkillPickedHandler.class).pickSkill(player, hand, skill);
					skillsData.get().getPickedSkills().add(skill);
					refresh();
				}
			});
	}

	@Override
	public double getWidth(final Tree<String, Skill> treeNode) {
		return SKILL_ICON_SIZE;
	}

	@Override
	public double getHeight(final Tree<String, Skill> treeNode) {
		return SKILL_ICON_SIZE;
	}

	@Override
	public Location getRootLocation() {
		return Location.Top;
	}

	@Override
	public AlignmentInLevel getAlignmentInLevel() {
		return AlignmentInLevel.Center;
	}

	@Override
	public double getGapBetweenLevels(int nextLevel) {
		return 32d;
	}

	@Override
	public double getGapBetweenNodes(final Tree<String, Skill> node1, final Tree<String, Skill> node2) {
		return 32d;
	}

}
