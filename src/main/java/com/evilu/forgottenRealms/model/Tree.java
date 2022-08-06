package com.evilu.forgottenRealms.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import java.awt.geom.Rectangle2D;

import org.abego.treelayout.Configuration;
import org.abego.treelayout.NodeExtentProvider;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;

/**
 * Tree
 */

public class Tree<K, V> {

    protected V value;
    protected final Tree<K, V> parent;
    protected final Map<K, Tree<K, V>> childs;

    public Tree(final Tree<K, V> parent, final V value) {
        this.parent = parent;
        this.value = value;
        this.childs = new LinkedHashMap<>();
    }

    public boolean containsKey(final K key) {
        return childs.containsKey(key);
    }

    public boolean containsValue(final V value) {
        if (value == null)
            return false;

        return childs.values().stream().anyMatch(value::equals);
    }

    public boolean isRoot() {
      return parent == null;
    }

    public Tree<K, V> getParent() {
      return parent;
    }

    public Tree<K, V> getChild(final K key) {
        return childs.get(key);
    }

    public V getChildValue(final K key) {
        final Tree<K, V> node = childs.get(key);
        if (node != null) {
            return node.value;
        }

        return null;
    }

    public Optional<Tree<K, V>> findRecursive(final K key) {
      if (childs.containsKey(key)) {
        return Optional.of(childs.get(key));
      }

      return childs.values()
        .stream()
        .map(t -> t.findRecursive(key))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .findAny();
    }

    public Tree<K, V> addChild(final K key, final V value) {
        if (childs.containsKey(key)) {
            final Tree<K, V> subTree = childs.get(key);
            subTree.value = value;
        }
        final Tree<K, V> subTree = new Tree<>(this, value);
        childs.put(key, subTree);

        return subTree;
    }

    public void putChild(final K key, final Tree<K, V> child) {
      childs.put(key, child);
    }

    public Optional<V> findDirectChildValue(final K key) {
        return Optional.ofNullable(getChildValue(key));
    }

    public V getValue() {
        return value;
    }

    public void forEach(final Consumer<Tree<K, V>> consumer) {
      consumer.accept(this);
      childs.values().forEach(child -> child.forEach(consumer));
    }

    public void forEachValue(final Consumer<V> consumer) {
      consumer.accept(value);
      childs.values().forEach(child -> child.forEachValue(consumer));
    }

    public <T> Tree<K, T> mapNodeRecursive(final Function<Tree<K, V>, T> mapper) {
      return mapNodeRecursive(null, mapper);
    }

    private <T> Tree<K, T> mapNodeRecursive(final Tree<K, T> parent, final Function<Tree<K, V>, T> mapper) {
      final Tree<K, T> newTree = new Tree<>(parent, mapper.apply(this));
      for (final Map.Entry<K, Tree<K, V>> entry : childs.entrySet()) {
        final Tree<K, T> newChild = entry.getValue().mapNodeRecursive(newTree, mapper);
        newTree.childs.put(entry.getKey(), newChild);
      }

      return newTree;
    }



    public <T> Tree<K, T> mapRecursive(final Function<V, T> mapper) {
      return mapRecursive(null, mapper);
    }

    private <T> Tree<K, T> mapRecursive(final Tree<K, T> parent, final Function<V, T> mapper) {
      final Tree<K, T> newTree = new Tree<>(parent, mapper.apply(value));

      for (final Map.Entry<K, Tree<K, V>> entry : childs.entrySet()) {
        final Tree<K, T> newChild = entry.getValue().mapRecursive(newTree, mapper);
        newTree.childs.put(entry.getKey(), newChild);
      }

      return newTree;
    }

    public void parentChildPreorderTraversal(final BiConsumer<V, V> consumer) {
      parentChildPreorderTraversal(consumer, true);
    }

    public void parentChildPreorderTraversal(final BiConsumer<V, V> consumer, final boolean isRoot) {
      if (isRoot) {
        consumer.accept(null, value);
      }

      for (final Tree<K, V> child : childs.values()) {
        consumer.accept(this.value, child.value);
        if (!child.childs.isEmpty()) {
          child.parentChildPreorderTraversal(consumer, false);
        }
      }
    }

    public static class BoundNode<V> {
      private final Rectangle2D.Double bounds;
      private final V value;

      public BoundNode(final Rectangle2D.Double bounds, final V value) {
        this.bounds = bounds;
        this.value = value;
      }

      public Rectangle2D.Double getBounds() {
        return bounds;
      }

      public V getValue() {
        return value;
      }
    }


    public TreeForTreeLayout<Tree<K, V>> createLayoutTree() {
        return new TreeForTreeLayout<>() {

          @Override
          public Tree<K, V> getRoot() {
              return Tree.this;
          }

          @Override
          public boolean isLeaf(final Tree<K, V> node) {
              return node.childs.isEmpty();
          }

          @Override
          public boolean isChildOfParent(final Tree<K, V> node, final Tree<K, V> parentNode) {
              return parentNode.childs.values()
                  .stream()
                  .anyMatch(node::equals);
          }

          @Override
          public Iterable<Tree<K, V>> getChildren(final Tree<K, V> parentNode) {
              return parentNode.childs.values();
          }

          @Override
          public Iterable<Tree<K, V>> getChildrenReverse(final Tree<K, V> parentNode) {
              final List<Tree<K, V>> childs = parentNode.childs.values()
                  .stream()
                  .collect(Collectors.toList());

              Collections.reverse(childs);

              return childs;
          }

          @Override
          public Tree<K, V> getFirstChild(final Tree<K, V> parentNode) {
              return parentNode.childs.values()
                  .stream()
                  .findFirst()
                  .orElseThrow();
          }

          @Override
          public Tree<K, V> getLastChild(final Tree<K, V> parentNode) {
              return parentNode.childs.values()
                  .stream()
                  .reduce((a, b) -> b)
                  .orElseThrow();
          }

        };
    }

    public Tree<K, BoundNode<V>> createOutlayedTree(final NodeExtentProvider<Tree<K, V>> nodeExtentProvider, final Configuration<Tree<K, V>> config) {
      final Map<Tree<K, V>, Rectangle2D.Double> bounds = new TreeLayout<>(createLayoutTree(), nodeExtentProvider, config).getNodeBounds();
      return mapNodeRecursive(node -> new BoundNode<>(bounds.get(node), node.value));
    }


}
