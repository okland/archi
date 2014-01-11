package struc;

import java.util.ArrayList;

public class ResStation<T> extends ArrayList<ResStationItem<T>> {

	
	public ResStation(int stationsNum, String Name) {
		for (int i = 1; i <= stationsNum; i++) {
			add(i - 1, new ResStationItem<T>(Name + i));
		}
	}
	
	/**
	 * Return the first free station
	 */
	public ResStationItem<T> getFreeStation() {
		for (ResStationItem<T> item : this) {
			if (!item.getBusy())
				return item;
		}
		return null;
	}
}
