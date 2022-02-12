package com.ict1009.pokemanz.scenes;


import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
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
	private static final int PLAY2_BUTTON_X=GameInfo.WIDTH/2-PLAY2_BUTTON_WIDTH/2-PLAY2_BUTTON_WIDTH;
	private static final int BACK_BUTTON_WIDTH=96;
	private static final int BACK_BUTTON_HEIGHT=58;
	private static final int LEVEL_BUTTON_WIDTH=135;
	private static final int LEVEL_BUTTON_X=GameInfo.WIDTH/2-LEVEL_BUTTON_WIDTH/2-LEVEL_BUTTON_WIDTH;;
	private static final int LEVEL_BUTTON_HEIGHT=61;
	private static final int TITLELOGO_WIDTH=615;
	private static final int TITLELOGO_HEIGHT=152;
	private static final int TITLELOGO_Y=GameInfo.HEIGHT-TITLELOGO_HEIGHT-70;


	private GameMain game;
	private SpriteBatch batch;
	private Texture play2ButtonActive;
	private Texture play2ButtonInactive;
	private Texture play3ButtonActive;
	private Texture play3ButtonInactive;
	private Texture play4ButtonActive;
	private Texture play4ButtonInactive;
	private Texture exitButtonActive;
	private Texture exitButtonInactive;
	private Texture backButtonActive;
	private Texture backButtonInactive;
	private Texture levelOneButtonActive;
	private Texture levelOneButtonInactive;
	private Texture levelTwoButtonActive;
	private Texture levelTwoButtonInactive;
	private Texture levelThreeButtonActive;
	private Texture levelThreeButtonInactive;
	private Texture titlelogo;
	private Texture exitlogo;
	private Animation<TextureRegion> animation;
	private float elapsed;
	private Music menuMusic;

    private boolean active = true;
	private boolean bye = false;
	private int numP = 1;

	public TitleScene(GameMain game) {
		this.game = game;
		this.batch = game.getBatch();
		play2ButtonActive = new Texture("screen/2playersactive.png");
		play2ButtonInactive = new Texture("screen/2playersinactive.png");
		play3ButtonActive = new Texture("screen/3playersactive.png");
		play3ButtonInactive = new Texture("screen/3playersinactive.png");
		play4ButtonActive = new Texture("screen/4playersactive.png");
		play4ButtonInactive = new Texture("screen/4playersinactive.png");
		exitButtonActive = new Texture("screen/exitactive.png");
		exitButtonInactive = new Texture("screen/exitinactive.png");
		backButtonActive = new Texture("screen/backactive.png");
		backButtonInactive = new Texture("screen/backinactive.png");
		levelOneButtonActive = new Texture("screen/level1active.png");
		levelOneButtonInactive = new Texture("screen/level1inactive.png");
		levelTwoButtonActive = new Texture("screen/level2active.png");
		levelTwoButtonInactive = new Texture("screen/level2inactive.png");
		levelThreeButtonActive = new Texture("screen/level3active.png");
		levelThreeButtonInactive = new Texture("screen/level3inactive.png");
		titlelogo = new Texture("screen/titlelogo.png");
		exitlogo = new Texture("screen/exitlogo.png");
		animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("screen/bg.gif").read());
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/music_menu.ogg"));
		menuMusic.setLooping(true);
		menuMusic.play();


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

        batch.draw(animation.getKeyFrame(elapsed), 0f,0f, GameInfo.WIDTH, GameInfo.HEIGHT);
        if (!bye) batch.draw(titlelogo,GameInfo.WIDTH/2-TITLELOGO_WIDTH/2,TITLELOGO_Y);
		if (bye) batch.draw(exitlogo,GameInfo.WIDTH/2-1010/2,TITLELOGO_Y);


        //2Player button
		if (active && Gdx.input.getX() < PLAY2_BUTTON_X + PLAY2_BUTTON_WIDTH  && Gdx.input.getX() > PLAY2_BUTTON_X - PLAY2_BUTTON_WIDTH && GameInfo.HEIGHT - Gdx.input.getY() < PLAY2_BUTTON_HEIGHT + PLAY2_BUTTON_Y && GameInfo.HEIGHT - Gdx.input.getY() > PLAY2_BUTTON_Y) {
			if (active) batch.draw(play2ButtonActive,PLAY2_BUTTON_X,PLAY2_BUTTON_Y);
			if (Gdx.input.justTouched()) {
				active = false;
				numP = 2;
				System.out.println(numP);
			}
		} else {
			if(active) batch.draw(play2ButtonInactive,PLAY2_BUTTON_X,PLAY2_BUTTON_Y);
		}

		//3Player button
		if (active && Gdx.input.getX() < PLAY2_BUTTON_X + PLAY2_BUTTON_WIDTH *2 && Gdx.input.getX() > PLAY2_BUTTON_X + PLAY2_BUTTON_WIDTH && GameInfo.HEIGHT - Gdx.input.getY() < PLAY2_BUTTON_HEIGHT + PLAY2_BUTTON_Y && GameInfo.HEIGHT - Gdx.input.getY() > PLAY2_BUTTON_Y) {
			if(active) batch.draw(play3ButtonActive,PLAY2_BUTTON_X + PLAY2_BUTTON_WIDTH,PLAY2_BUTTON_Y);
			if (Gdx.input.justTouched()) {
				active = false;
				numP = 3;
				System.out.println(numP);
			}
		} else {
			if(active) batch.draw(play3ButtonInactive,PLAY2_BUTTON_X + PLAY2_BUTTON_WIDTH,PLAY2_BUTTON_Y);

		}

		//4Player button
		if (active && Gdx.input.getX() < PLAY2_BUTTON_X + PLAY2_BUTTON_WIDTH * 3 && Gdx.input.getX() > PLAY2_BUTTON_X + PLAY2_BUTTON_WIDTH*2 && GameInfo.HEIGHT - Gdx.input.getY() < PLAY2_BUTTON_HEIGHT + PLAY2_BUTTON_Y && GameInfo.HEIGHT - Gdx.input.getY() > PLAY2_BUTTON_Y) {
			if (active) batch.draw(play4ButtonActive,PLAY2_BUTTON_X + PLAY2_BUTTON_WIDTH*2,PLAY2_BUTTON_Y);
			if (Gdx.input.justTouched()) {
                active = false;
				numP = 4;
				System.out.println(numP);
			}
		} else {
			if(active) batch.draw(play4ButtonInactive,PLAY2_BUTTON_X + PLAY2_BUTTON_WIDTH*2,PLAY2_BUTTON_Y);
		}

		//Level 1 Button
		if (!active && Gdx.input.getX() < LEVEL_BUTTON_X + LEVEL_BUTTON_WIDTH  && Gdx.input.getX() > LEVEL_BUTTON_X - LEVEL_BUTTON_WIDTH && GameInfo.HEIGHT - Gdx.input.getY() < LEVEL_BUTTON_HEIGHT + PLAY2_BUTTON_Y - 50 && GameInfo.HEIGHT - Gdx.input.getY() > PLAY2_BUTTON_Y - 50) {
			if (!active) batch.draw(levelOneButtonActive,LEVEL_BUTTON_X,PLAY2_BUTTON_Y-50);
			if (Gdx.input.justTouched()) {
				this.dispose();
				menuMusic.dispose();
				game.setScreen(new MainScene(game, numP, 1));
				System.out.println("Level 1 + " + numP);
			}
		} else {
			if(!active) batch.draw(levelOneButtonInactive,LEVEL_BUTTON_X,PLAY2_BUTTON_Y-50);
		}

		//Level 2 Button
		if (!active && Gdx.input.getX() < LEVEL_BUTTON_X + LEVEL_BUTTON_WIDTH * 2 && Gdx.input.getX() > LEVEL_BUTTON_X + LEVEL_BUTTON_WIDTH && GameInfo.HEIGHT - Gdx.input.getY() < PLAY2_BUTTON_HEIGHT + PLAY2_BUTTON_Y - 50 && GameInfo.HEIGHT - Gdx.input.getY() > PLAY2_BUTTON_Y - 50) {
			if(!active) batch.draw(levelTwoButtonActive,LEVEL_BUTTON_X + LEVEL_BUTTON_WIDTH,PLAY2_BUTTON_Y - 50);
			if (Gdx.input.justTouched()) {
				this.dispose();
				menuMusic.dispose();
				game.setScreen(new MainScene(game, numP, 2));
				System.out.println("Level 2 + " + numP);
			}
		} else {
			if(!active) batch.draw(levelTwoButtonInactive,LEVEL_BUTTON_X + LEVEL_BUTTON_WIDTH,PLAY2_BUTTON_Y - 50);

		}


		//Level 3 Button
		if (!active && Gdx.input.getX() < LEVEL_BUTTON_X + LEVEL_BUTTON_WIDTH * 3 && Gdx.input.getX() > LEVEL_BUTTON_X + LEVEL_BUTTON_WIDTH*2 && GameInfo.HEIGHT - Gdx.input.getY() < PLAY2_BUTTON_HEIGHT + PLAY2_BUTTON_Y - 50 && GameInfo.HEIGHT - Gdx.input.getY() > PLAY2_BUTTON_Y - 50) {
			if (!active) batch.draw(levelThreeButtonActive,LEVEL_BUTTON_X + LEVEL_BUTTON_WIDTH*2,PLAY2_BUTTON_Y-50);
			if (Gdx.input.justTouched()) {
				this.dispose();
				menuMusic.dispose();
				game.setScreen(new MainScene(game, numP, 3));
				System.out.println("Level 3 + " + numP);
			}
		} else {
			if(!active) batch.draw(levelThreeButtonInactive,LEVEL_BUTTON_X + LEVEL_BUTTON_WIDTH*2,PLAY2_BUTTON_Y-50);
		}


        //Exit button
        int ex = GameInfo.WIDTH/2-EXIT_BUTTON_WIDTH/2;
        if (active && Gdx.input.getX() < ex + EXIT_BUTTON_WIDTH && Gdx.input.getX() > ex && GameInfo.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_HEIGHT + EXIT_BUTTON_Y-PLAY2_BUTTON_HEIGHT*3 && GameInfo.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y-PLAY2_BUTTON_HEIGHT*3) {
        	if (active) batch.draw(exitButtonActive,ex,EXIT_BUTTON_Y-PLAY2_BUTTON_HEIGHT*3);
        	if (Gdx.input.justTouched()) {
        		 bye = true;
        		 Timer.schedule(new Task() {
        			    @Override
        			    public void run() {
        			       Gdx.app.exit();
        			    }
        			}, 2);
        	}
        } else {
        	if (active) batch.draw(exitButtonInactive,GameInfo.WIDTH/2-EXIT_BUTTON_WIDTH/2,EXIT_BUTTON_Y-PLAY2_BUTTON_HEIGHT*3);
        }
		//Back button
		int bk = GameInfo.WIDTH/2-BACK_BUTTON_WIDTH/2;
		if (!active && Gdx.input.getX() < bk + BACK_BUTTON_WIDTH && Gdx.input.getX() > ex && GameInfo.HEIGHT - Gdx.input.getY() < BACK_BUTTON_HEIGHT + EXIT_BUTTON_Y-PLAY2_BUTTON_HEIGHT*2 && GameInfo.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y-PLAY2_BUTTON_HEIGHT*2) {
			if (!active) batch.draw(backButtonActive,ex,EXIT_BUTTON_Y-BACK_BUTTON_HEIGHT*2);
			if (Gdx.input.justTouched()) {
				active = true;
				System.out.println("Back");
			}
		} else {
			if (!active) batch.draw(backButtonInactive,ex,EXIT_BUTTON_Y-BACK_BUTTON_HEIGHT*2);
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
