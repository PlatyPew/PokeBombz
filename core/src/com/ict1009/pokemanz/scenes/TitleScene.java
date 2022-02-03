package com.ict1009.pokemanz.scenes;


import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.ict1009.pokemanz.GameMain;
import com.ict1009.pokemanz.helper.GameInfo;

public class TitleScene implements Screen{
	
	private static final int EXIT_BUTTON_WIDTH=83;
	private static final int EXIT_BUTTON_HEIGHT=59;
	private static final int EXIT_BUTTON_Y=300;
	private static final int PLAY2_BUTTON_WIDTH=168;
	private static final int PLAY2_BUTTON_HEIGHT=66;
	private static final int PLAY2_BUTTON_Y=350;
	private static final int TITLELOGO_WIDTH=615;
	private static final int TITLELOGO_HEIGHT=152;
	private static final int TITLELOGO_Y=GameInfo.HEIGHT-TITLELOGO_HEIGHT-70;
	private static final int EXITLOGO_Y=GameInfo.HEIGHT-TITLELOGO_HEIGHT-70;

	
	
	GameMain game;
	private SpriteBatch batch;
	Texture play2ButtonActive;
	Texture play2ButtonInactive;
	Texture exitButtonActive;
	Texture exitButtonInactive;
	Texture titlelogo;
	Texture exitlogo;
	Animation<TextureRegion> animation;
    float elapsed;
	
	public TitleScene(GameMain game) {
		this.game = game;
		this.batch = game.getBatch();
		play2ButtonActive = new Texture("screen/2playersactive.png");
		play2ButtonInactive = new Texture("screen/2playersinactive.png");
		exitButtonActive = new Texture("screen/exitactive.png");
		exitButtonInactive = new Texture("screen/exitinactive.png");
		titlelogo = new Texture("screen/titlelogo.png");
		exitlogo = new Texture("screen/exitlogo.png");
		animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("screen/bg.gif").read());
		
	    
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	
        

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		elapsed += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        
        batch.draw(animation.getKeyFrame(elapsed), 20.0f, 20.0f, GameInfo.WIDTH, GameInfo.HEIGHT);
        batch.draw(titlelogo,GameInfo.WIDTH/2-TITLELOGO_WIDTH/2,TITLELOGO_Y);
        
        //2Player button
        int p2x = GameInfo.WIDTH/2-PLAY2_BUTTON_WIDTH/2;
        if (Gdx.input.getX() < p2x + PLAY2_BUTTON_WIDTH && Gdx.input.getX() > p2x && GameInfo.HEIGHT - Gdx.input.getY() < PLAY2_BUTTON_HEIGHT + PLAY2_BUTTON_Y && GameInfo.HEIGHT - Gdx.input.getY() > PLAY2_BUTTON_Y) {
        	batch.draw(play2ButtonActive,p2x,PLAY2_BUTTON_Y);
        	if (Gdx.input.isTouched()) {
        		this.dispose();
        		game.setScreen(new MainScene(game));
        	}
        } else {
        	batch.draw(play2ButtonInactive,GameInfo.WIDTH/2-PLAY2_BUTTON_WIDTH/2,PLAY2_BUTTON_Y);
        }
        
        //Exit button
        int ex = GameInfo.WIDTH/2-EXIT_BUTTON_WIDTH/2;
        if (Gdx.input.getX() < ex + EXIT_BUTTON_WIDTH && Gdx.input.getX() > ex && GameInfo.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_HEIGHT + EXIT_BUTTON_Y && GameInfo.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y) {
        	batch.draw(exitButtonActive,ex,EXIT_BUTTON_Y);
        	if (Gdx.input.isTouched()) {
        		 titlelogo = exitlogo;
        		 Timer.schedule(new Task() {
        			    @Override
        			    public void run() {
        			       Gdx.app.exit();
        			    }
        			}, 2);
        	}
        } else {
        	batch.draw(exitButtonInactive,GameInfo.WIDTH/2-EXIT_BUTTON_WIDTH/2,EXIT_BUTTON_Y);
        }
        batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	

}