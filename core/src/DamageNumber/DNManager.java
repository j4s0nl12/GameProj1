package DamageNumber;

import java.util.Iterator;

import com.badlogic.gdx.utils.Array;

public class DNManager {

	Array<DamageNumber> dnList;
	
	public DNManager(){
		this.dnList = new Array<>();
	}
	
	public void update(float delta){
		for(DamageNumber dn : dnList){
			dn.update(delta);
		}
		checkDestroy();
	}
	
	public void checkDestroy(){
		Iterator<DamageNumber> it = this.dnList.iterator();
		for(; it.hasNext();){
			DamageNumber tmp = it.next();
			if(tmp.isDead){
				it.remove();
			}
		}
	}
	
	public void add(DamageNumber dn){
		this.dnList.add(dn);
	}
	
	public DamageNumber get(int idx){
		return this.dnList.get(idx);
	}
	
	public int size(){
		return this.dnList.size;
	}
}
