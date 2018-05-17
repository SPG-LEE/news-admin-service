package sq.enumeration;
/** 
* @Description: 
* @author jack.liu@91jimai.com   
* @date 2016年5月31日 下午3:47:12 
* @version V1.0   
*/
public enum OrderType {
	ACTIVITY(0),SECKILL(1),GROUPBUY(2);
	public int index;

	public int getIndex() {
		return index;
	}

	private  OrderType(int index){
		this.index=index;
	}
	
	public static OrderType geType(int index){
		switch(index){
		case 0:
			return ACTIVITY;
		case 1:
			return SECKILL;
		case 2:
			return GROUPBUY;
		}
			return null;		
	}
	public String getName(){
		switch (index){		
		case 0:
			return "巡展";
		case 1:
			return "秒杀";
		case 2:
			return "团购";
		}
		return "";
	}
}
