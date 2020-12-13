package week08;

public class Tile {
	int w = 0;
	int h = 0;
	
	int x = 0;
	int y = 0;
	
	public static void main (String[] args) {
		Tile t = new Tile();
		t.setTile(2,10);
		t.setPart(2,1);
		t.start();
	}
	
	private void setPart(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	private void setTile(int tWidth, int tHeight) {
		this.w = tWidth;
		this.h = tHeight;
		
	}
	
	private void start() {
		// x to w / y to h
		if(x<w) {
			
		}
		// x to h / y to w
			
	}

	
	
}
