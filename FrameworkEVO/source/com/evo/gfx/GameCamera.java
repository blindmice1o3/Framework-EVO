package com.evo.gfx;

import com.evo.Handler;
import com.evo.entities.Entity;
import com.evo.states.GameStageState;
import com.evo.states.StateManager;
import com.evo.tiles.Tile;

public class GameCamera {

    private Handler handler;
    private float xOffset, yOffset;

    public GameCamera(Handler handler, float xOffset, float yOffset) {
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    } // **** end GameCamera(Handler, float, float) constructor

    public void checkBlankSpace() {
        //if off-LEFT-of-map, re-position so it's right at the edge.
        if (xOffset < 0) {
            xOffset = 0;
        }
        //else if off-RIGHT-of-map, re-position so it's right at the edge.
        else if (xOffset > ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getWidthInNumOfTile()
                * (Tile.screenTileWidth) - handler.panelWidth) {
            xOffset = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getWidthInNumOfTile()
                    * (Tile.screenTileWidth) - handler.panelWidth;
        }

        //if off-TOP-of-map, re-position so it's right at the edge.
        if(yOffset < 0) {
            yOffset = 0;
        }
        // else if off-BOTTOM-of-map, re-position so it's right at the edge.
        else if (yOffset > ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getHeightInNumOfTile()
                * (Tile.screenTileHeight) - handler.panelHeight) {
            yOffset = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getHeightInNumOfTile()
                    * (Tile.screenTileHeight) - handler.panelHeight;
        }
    }

    public void centerOnEntity(Entity e) {
        xOffset = e.getX() - (handler.panelWidth / 2) + (e.getWidth() / 2);
        yOffset = e.getY() - (handler.panelHeight / 2)+ (e.getHeight() / 2);

        checkBlankSpace();
    }

    public void move(float xAmt, float yAmt) {
        xOffset += xAmt;
        yOffset += yAmt;

        checkBlankSpace();
    }

    // GETTERS AND SETTERS

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

} // **** end GameCamera class ****