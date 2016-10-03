package Utility;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameProj1;

import Screens.BaseScreen;
import Screens.GameScreen;
import Screens.MainMenuScreen;
import Screens.OptionsScreen;

public class ScreenManager {
	
	public final GameProj1 game;
	
	public final int MAINMENUSCREEN = 0;
	public final int GAMESCREEN = 1;
	public final int OPTIONSSCREEN = 2;

	public int curScreenIdx;
	public int lastScreenIdx;
	public Array<BaseScreen> screenList;
	
	public ScreenManager(GameProj1 gam){
		game = gam;
		this.init();
	}
	
	private void init(){
		this.screenList = new Array<>();
		this.screenList.add(new MainMenuScreen(game));
		this.screenList.add(new GameScreen(game));
		this.screenList.add(new OptionsScreen(game));
		
		int initScreen = GAMESCREEN;
		this.curScreenIdx = initScreen;
		this.lastScreenIdx = initScreen;
		game.setScreen(this.screenList.get(initScreen));
	}
}
