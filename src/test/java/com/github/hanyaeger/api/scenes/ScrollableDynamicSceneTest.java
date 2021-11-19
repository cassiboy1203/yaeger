package com.github.hanyaeger.api.scenes;

import com.github.hanyaeger.core.Updater;
import com.github.hanyaeger.core.ViewOrders;
import com.github.hanyaeger.core.YaegerConfig;
import com.github.hanyaeger.core.entities.Debugger;
import com.github.hanyaeger.core.entities.EntityCollection;
import com.github.hanyaeger.core.entities.EntitySupplier;
import com.github.hanyaeger.core.factories.EntityCollectionFactory;
import com.github.hanyaeger.core.factories.PaneFactory;
import com.github.hanyaeger.core.factories.SceneFactory;
import com.github.hanyaeger.core.factories.animationtimer.AnimationTimerFactory;
import com.github.hanyaeger.core.scenes.delegates.BackgroundDelegate;
import com.github.hanyaeger.core.scenes.delegates.KeyListenerDelegate;
import com.google.inject.Injector;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ScrollableDynamicSceneTest {

    private ScrollableDynamicSceneImpl sut;

    private SceneFactory sceneFactory;
    private Debugger debugger;
    private EntityCollectionFactory entityCollectionFactory;
    private AnimationTimer animationTimer;
    private AnimationTimerFactory animationTimerFactory;
    private PaneFactory paneFactory;
    private Injector injector;

    private KeyListenerDelegate keyListenerDelegate;
    private BackgroundDelegate backgroundDelegate;

    private EntityCollection entityCollection;
    private EntitySupplier entitySupplier;
    private EntitySupplier viewPortEntitySupplier;

    private Pane defaultPane;
    private Pane stickyPane;
    private static ScrollPane scrollPane;
    private StackPane stackPane;
    private ObservableList<Node> stackPaneChildren;

    private YaegerConfig config;
    private Scene scene;
    private Updater updater;
    private Stage stage;


    @BeforeAll
    static void beforAll() {
        // Ensure that the scrollpane is created from a JavaFX thread
        Platform.startup(() -> {
            scrollPane = mock(ScrollPane.class, withSettings());
        });
    }

    @BeforeEach
    void setup() {
        sut = new ScrollableDynamicSceneImpl();

        defaultPane = mock(Pane.class);
        stickyPane = mock(Pane.class);
        stackPane = mock(StackPane.class);
        reset(scrollPane);

        stackPaneChildren = mock(ObservableList.class);

        backgroundDelegate = mock(BackgroundDelegate.class);
        keyListenerDelegate = mock(KeyListenerDelegate.class);
        entitySupplier = mock(EntitySupplier.class);
        viewPortEntitySupplier = mock(EntitySupplier.class);
        sceneFactory = mock(SceneFactory.class);
        debugger = mock(Debugger.class);
        animationTimer = mock(AnimationTimer.class);
        entityCollectionFactory = mock(EntityCollectionFactory.class);
        animationTimerFactory = mock(AnimationTimerFactory.class);
        paneFactory = mock(PaneFactory.class);
        injector = mock(Injector.class);
        updater = mock(Updater.class);
        config = mock(YaegerConfig.class);
        stage = mock(Stage.class);
        scene = mock(Scene.class);
        entityCollection = mock(EntityCollection.class);

        when(sceneFactory.create(defaultPane)).thenReturn(scene);
        when(entityCollectionFactory.create(config)).thenReturn(entityCollection);
        when(animationTimerFactory.create(any())).thenReturn(animationTimer);

        when(paneFactory.createPane()).thenReturn(defaultPane, stickyPane);
        when(paneFactory.createScrollPane()).thenReturn(scrollPane);
        when(paneFactory.createStackPane()).thenReturn(stackPane);

        when(stackPane.getChildren()).thenReturn(stackPaneChildren);

        when(stage.getScene()).thenReturn(scene);

        sut.setDebugger(debugger);
        sut.setSceneFactory(sceneFactory);
        sut.setEntityCollectionFactory(entityCollectionFactory);
        sut.setPaneFactory(paneFactory);
        sut.setBackgroundDelegate(backgroundDelegate);
        sut.setKeyListenerDelegate(keyListenerDelegate);
        sut.setEntitySupplier(entitySupplier);
        sut.setViewPortEntitySupplier(viewPortEntitySupplier);
        sut.setViewPortEntitySupplier(viewPortEntitySupplier);
        sut.setAnimationTimerFactory(animationTimerFactory);
        sut.setUpdater(updater);
        sut.setStage(stage);
        sut.setConfig(config);


        sut.init(injector);
    }

    @Test
    void scrollPaneInitializedCorrectly() {
        // Arrange

        // Act

        // Assert
        verify(scrollPane).setViewOrder(ViewOrders.VIEW_ORDER_SCROLLPANE);
        verify(scrollPane).setFitToWidth(true);
        verify(scrollPane).setFitToHeight(true);
        verify(scrollPane).setContent(defaultPane);
    }

    @Test
    void stickyPaneInitializedCorrectly() {
        // Arrange

        // Act

        // Assert
        verify(stickyPane).setViewOrder(ViewOrders.VIEW_ORDER_STICKYPANE);
        verify(stickyPane).setPickOnBounds(false);
    }

    @Test
    void stackPaneInitializedCorrectly() {
        // Arrange

        // Act

        // Assert
        verify(stackPane).setAlignment(Pos.TOP_LEFT);
        verify(stackPaneChildren).add(scrollPane);
        verify(stackPaneChildren).add(stickyPane);
    }

    @Test
    void getRootPaneReturnsDefaultPane() {
        // Arrange

        // Act

        // Assert
        assertEquals(defaultPane, sut.getRootPane());
    }

    @Test
    void getPaneForDebuggerReturnsStickyPane() {
        // Arrange

        // Act
        var actual = sut.getPaneForDebugger();

        // Assert
        assertEquals(stickyPane, actual);
    }

    @Test
    void getSceneReturnsSceneFromScrollPane() {
        // Arrange
        when(scrollPane.getScene()).thenReturn(scene);

        // Act
        var actual = sut.getScene();

        // Assert
        assertEquals(scene, actual);
    }

    @Test
    void postActivateSetsPaneOnViewPortEntitySupplier() {
        // Arrange
        sut.activate();

        // Act
        sut.postActivate();

        // Assert
        verify(viewPortEntitySupplier).setPane(stickyPane);
    }

    @Test
    void postActivateSetsGameDimensionsOnDebuggerIfDebugIsTrue() {
        // Arrange
        when(config.showDebug()).thenReturn(true);
        sut.activate();

        // Act
        sut.postActivate();

        // Assert
        verify(debugger).setGameDimensions(any());
    }

    @Test
    void postActivateDisablesSscrollingByDefault() {
        // Arrange
        when(config.enableScroll()).thenReturn(false);
        sut.activate();

        // Act
        sut.postActivate();

        // Assert
        verify(scrollPane).addEventFilter(eq(ScrollEvent.SCROLL), any());
    }

    @Test
    void getWidthReturnsWidthOfDefaultPane() {
        // Arrange

        // Act
        sut.getWidth();

        // Assert
        verify(defaultPane).getPrefWidth();
    }

    @Test
    void getHeightReturnsHeightOfDefaultPane() {
        // Arrange

        // Act
        sut.getHeight();

        // Assert
        verify(defaultPane).getPrefHeight();
    }

    @Test
    void getViewPortWidthReturnsWidthOfStage() {
        // Arrange

        // Act
        sut.getViewportWidth();

        // Assert
        verify(scene).getWidth();
    }

    @Test
    void getViewPortHeightReturnsHeightOfStage() {
        // Arrange

        // Act
        sut.getViewportHeight();

        // Assert
        verify(scene).getHeight();
    }

    @Test
    void setHorizontalScrollPositionDelegatesToPane() {
        // Arrange
        var expected = 0.37D;

        // Act
        sut.setHorizontalScrollPosition(expected);

        // Assert
        verify(scrollPane).setHvalue(expected);
    }

    @Test
    void setVerticalScrollPositionDelegatesToPane() {
        // Arrange
        var expected = 0.37D;

        // Act
        sut.setVerticalScrollPosition(expected);

        // Assert
        verify(scrollPane).setVvalue(expected);
    }

    private static class ScrollableDynamicSceneImpl extends ScrollableDynamicScene {

        @Override
        public void setupScene() {
            // Not required here
        }

        @Override
        public void setupEntities() {
            // Not required here
        }
    }
}
