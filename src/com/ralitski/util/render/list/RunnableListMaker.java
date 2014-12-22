package com.ralitski.util.render.list;

public class RunnableListMaker extends ListMaker {
	
	private Runnable maker;
	
	public RunnableListMaker(Runnable maker) {
		this.maker = maker;
	}

	@Override
	public void makeList() {
		maker.run();
	}

}
