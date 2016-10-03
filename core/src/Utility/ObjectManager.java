package Utility;

import java.util.Iterator;

import com.badlogic.gdx.utils.Array;

import GameObjects.BaseObject;

public class ObjectManager {
	
	Array<BaseObject> objectList;
	
	public ObjectManager(){
		this.objectList = new Array<>();
	}
	
	public void add(BaseObject o){
		this.objectList.add(o);
	}
	
	public void checkDestroy(){
		Iterator<BaseObject> it = this.objectList.iterator();
		for(; it.hasNext();){
			BaseObject tmp = it.next();
			if(tmp.isDead){
				it.remove();
			}
		}
	}
	
	public BaseObject get(int idx){
		return this.objectList.get(idx);
	}
	
	public void update(float delta){
		for(BaseObject o : this.objectList){
			o.update(delta);
		}
		
		checkDestroy();
	}
	
	public int size(){
		return this.objectList.size;
	}
}
