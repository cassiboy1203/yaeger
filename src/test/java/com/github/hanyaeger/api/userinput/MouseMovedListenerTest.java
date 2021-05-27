package com.github.hanyaeger.api.userinput;

import com.github.hanyaeger.core.YaegerConfig;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.github.hanyaeger.core.repositories.DragNDropRepository;
import com.google.inject.Injector;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MouseMovedListenerTest {

    private static final Coordinate2D DEFAULT_LOCATION = new Coordinate2D(0, 0);


    @Test
    void attachMouseMovedListenerAttachesMouseListenerToEntity() {
        // Arrange
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var scene = mock(Scene.class);
        var mouseMovedListener = new MouseMovedListeningEntityImpl(DEFAULT_LOCATION);
        mouseMovedListener.setNode(node);

        when(node.getScene()).thenReturn(scene);

        // Act
        mouseMovedListener.attachMouseMovedListener();

        // Assert
        verify(scene).setOnMouseMoved(any());
    }

    @Test
    void attachMouseMovedListenerAttachesMouseListenerToScene() {
        // Arrange
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var mouseMovedListener = new MouseMovedListeningSceneImpl();
        mouseMovedListener.setNode(node);

        // Act
        mouseMovedListener.attachMouseMovedListener();

        // Assert
        verify(node).setOnMouseMoved(any());
    }

    @Test
    void attachMouseMovedListenerToOtherDoesNothing() {
        // Arrange
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var mouseMovedListener = new MouseMovedListenerImpl();
        mouseMovedListener.setNode(node);

        // Act
        mouseMovedListener.attachMouseMovedListener();

        // Assert
        verifyNoInteractions(node);
    }

    private static class MouseMovedListenerImpl implements MouseMovedListener {

        private Node node;

        public void setNode(Node node) {
            this.node = node;
        }

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.empty();
        }

        @Override
        public void onMouseMoved(Coordinate2D coordinate2D) {

        }
    }

    private static class MouseMovedListeningSceneImpl implements YaegerScene, MouseMovedListener {

        private Node node;

        @Override
        public void clear() {

        }

        @Override
        public void destroy() {

        }

        @Override
        public void setBrightness(double brightness) {

        }

        @Override
        public double getBrightness() {
            return 0;
        }

        @Override
        public void init(Injector injector) {

        }

        public void setNode(Node node) {
            this.node = node;
        }

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.of(node);
        }

        @Override
        public void setupScene() {

        }

        @Override
        public void setupEntities() {

        }

        @Override
        public void postActivate() {

        }

        @Override
        public void setBackgroundColor(Color color) {

        }

        @Override
        public void setBackgroundImage(String url) {

        }

        @Override
        public void setBackgroundAudio(String url) {

        }

        @Override
        public Scene getScene() {
            return null;
        }

        @Override
        public Stage getStage() {
            return null;
        }

        @Override
        public void setStage(Stage stage) {

        }

        @Override
        public void setConfig(YaegerConfig yaegerConfig) {

        }

        @Override
        public void onMouseMoved(Coordinate2D coordinate2D) {

        }

        @Override
        public void setDragNDropRepository(DragNDropRepository dragNDropRepository) {
            
        }

        @Override
        public DragNDropRepository getDragNDropRepository() {
            return null;
        }
    }

    private static class MouseMovedListeningEntityImpl extends YaegerEntity implements MouseMovedListener {

        private Node node;

        /**
         * Create a new {@link YaegerEntity} on the given {@link Coordinate2D}.
         *
         * @param initialLocation the initial {@link Coordinate2D} of this {@link YaegerEntity}
         */
        protected MouseMovedListeningEntityImpl(Coordinate2D initialLocation) {
            super(initialLocation);
        }

        @Override
        public Optional<? extends Node> getNode() {
            return Optional.of(node);
        }

        void setNode(Node node) {
            this.node = node;
        }

        @Override
        public void onMouseMoved(Coordinate2D coordinate2D) {

        }
    }
}
