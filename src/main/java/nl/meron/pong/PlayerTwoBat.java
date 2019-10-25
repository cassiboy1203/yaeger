package nl.meron.pong;

import javafx.scene.input.KeyCode;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.sprites.movement.MovementVector;
import nl.meron.yaeger.engine.entities.entity.sprites.Size;

import java.util.Set;

public class PlayerTwoBat extends PongBat {
    public PlayerTwoBat(Point initialPoint, Size size) {
        super(initialPoint, size);
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.K)) {
            setSpeed(SPEED);
            setDirection(MovementVector.Direction.UP);
        } else if (pressedKeys.contains(KeyCode.M)) {
            setSpeed(SPEED);
            setDirection(MovementVector.Direction.DOWN);
        } else {
            setSpeed(0);
        }
    }


}
