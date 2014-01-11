package struc;

public class ResStationItem <T> implements Comparable<T> {
	
 	private final String Name;
 	private boolean Busy;
 	public Operation op;
 	private T VI;
 	private T VK;
 	private ResStationItem<T> QI;
 	private ResStationItem<T> QK;
 	public int execAtCycle;
	
 	
	public ResStationItem(String name) {
		this.Name = name;
	}
	

	public String getName() {
		return Name;
	}
	
	
	public boolean getBusy() {
		return Busy;
	}
	
	public void setBusy(boolean busy) {
		Busy = busy;
	}
	
	public Operation getOp() {
		return op;
	}
	
	public void setOp(Operation _op) {
		 op = _op;
	}
	
	public T getVi() {
		return VI;
	}
	
	public void setVi(T vi) {
		VI = vi;
	}
	
	public T getVk() {
		return VK;
	}
	
	public void setVk(T vk) {
		VK = vk;
	}
	
	public ResStationItem<T> getQi() {
		return QI;
	}
	
	public void setQi(ResStationItem<T> qi) {
		QI = qi;
	}
	
	public ResStationItem<T> getQk() {
		return QK;
	}
	

	public void setQk(ResStationItem<T> qk) {
		QK = qk;
	}
	
	
	public void clean() {
		VI = null;
		VK = null;
		QI = null;
		QK = null;
		Busy = false;
		execAtCycle = 0;
		op = null;
	}


	@Override
	public int compareTo(T o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
