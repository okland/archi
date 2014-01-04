package struc;

public class ResStationItem <T extends Comparable<T>> implements Comparable<T> {
	
 	private final String Name;
 	private static boolean Busy;
 	private int Op;
 	private T VI;
 	private T VK;
 	private ResStationItem<T> QI;
 	private ResStationItem<T> QK;
 	private int execAtCycle;
	private IComputable<T> Comput;

	
 	
	public ResStationItem(String name, IComputable<T> comput) {
		this.Name = name;
		this.Comput = comput;
	}
	

	public String getName() {
		return Name;
	}
	
	
	public static boolean getBusy() {
		return Busy;
	}
	
	public int getOp() {
		return Op;
	}
	
	public T getVi() {
		return VI;
	}
	
	public T getVk() {
		return VK;
	}
	
	public ResStationItem<T> getQi() {
		return QI;
	}
	
	public ResStationItem<T> getQk() {
		return QK;
	}
	
	
	public void clean() {
		VI = null;
		VK = null;
		QI = null;
		QK = null;
		Busy = false;
		execAtCycle = Op = 0;
	}


	@Override
	public int compareTo(T o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
