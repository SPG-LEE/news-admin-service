package sq.util;

import java.util.HashMap;
import java.util.Map;


public enum OrderByFieldUtil {

	UPDATE_DATE_ASC(0), UPDATE_DATE_DESC(1), DEFAULT(2), NAME_DESC(4), NAME_ASC(
			8), ID_DESC(16), ID_ASC(32);

	private int index;
	private static Map<OrderByFieldUtil, String> values;

	private OrderByFieldUtil(int index) {
		this.index = index;
	}

	public static String getOrder(String orderStr) {
		// TODO 判断返回order语句
		init();
		String[] ordersStrings = orderStr.split(",");
		System.out.println("AA" + ordersStrings.length);
		if (ordersStrings.length > 1) {
			if (ordersStrings[0].substring(1, ordersStrings[0].length())
					.equals("updateDate")) {
				if (ordersStrings[0].substring(0, 1).equals("-")) {
					if (ordersStrings[1]
							.substring(1, ordersStrings[1].length()).equals(
									"name")) {
						if (ordersStrings[1].substring(0, 1).equals("+")) {
							return values.get(UPDATE_DATE_DESC) + ","
									+ values.get(NAME_ASC);
						}
						if (ordersStrings[1].substring(0, 1).equals("-")) {
							return values.get(UPDATE_DATE_DESC) + ","
									+ values.get(NAME_DESC);
						}
					}
					if (ordersStrings[1]
							.substring(1, ordersStrings[1].length()).equals(
									"id")) {
						if (ordersStrings[1].substring(0, 1).equals("+")) {
							return values.get(UPDATE_DATE_DESC) + ","
									+ values.get(ID_ASC);
						}
						if (ordersStrings[1].substring(0, 1).equals("-")) {
							return values.get(UPDATE_DATE_DESC) + ","
									+ values.get(ID_DESC);
						}
					}

				}
				if (ordersStrings[0].substring(0, 1).equals("+")) {
					if (ordersStrings[1]
							.substring(1, ordersStrings[1].length()).equals(
									"name")) {
						if (ordersStrings[1].substring(0, 1).equals("+")) {
							return values.get(UPDATE_DATE_ASC) + ","
									+ values.get(NAME_ASC);
						}
						if (ordersStrings[1].substring(0, 1).equals("-")) {
							return values.get(UPDATE_DATE_ASC) + ","
									+ values.get(NAME_DESC);
						}
					}
					if (ordersStrings[1]
							.substring(1, ordersStrings[1].length()).equals(
									"id")) {
						if (ordersStrings[1].substring(0, 1).equals("+")) {
							return values.get(UPDATE_DATE_ASC) + ","
									+ values.get(ID_ASC);
						}
						if (ordersStrings[1].substring(0, 1).equals("-")) {
							return values.get(UPDATE_DATE_ASC) + ","
									+ values.get(ID_DESC);
						}
					}
				}

			}
			if (ordersStrings[0].substring(1, ordersStrings[0].length())
					.equals("name")) {
				if (ordersStrings[0].substring(0, 1).equals("-")) {
					if (ordersStrings[1]
							.substring(1, ordersStrings[1].length()).equals(
									"updateDate")) {
						if (ordersStrings[1].substring(0, 1).equals("+")) {
							return values.get(NAME_DESC) + ","
									+ values.get(UPDATE_DATE_ASC);
						}
						if (ordersStrings[1].substring(0, 1).equals("-")) {
							return values.get(NAME_DESC) + ","
									+ values.get(UPDATE_DATE_DESC);
						}
					}
					if (ordersStrings[1]
							.substring(1, ordersStrings[1].length()).equals(
									"id")) {
						if (ordersStrings[1].substring(0, 1).equals("+")) {
							return values.get(NAME_DESC) + ","
									+ values.get(ID_ASC);
						}
						if (ordersStrings[1].substring(0, 1).equals("-")) {
							return values.get(NAME_DESC) + ","
									+ values.get(ID_DESC);
						}
					}

				}
				if (ordersStrings[0].substring(0, 1).equals("+")) {
					if (ordersStrings[1]
							.substring(1, ordersStrings[1].length()).equals(
									"updateDate")) {
						if (ordersStrings[1].substring(0, 1).equals("+")) {
							return values.get(NAME_ASC) + ","
									+ values.get(UPDATE_DATE_ASC);
						}
						if (ordersStrings[1].substring(0, 1).equals("-")) {
							return values.get(NAME_ASC) + ","
									+ values.get(UPDATE_DATE_DESC);
						}
					}
					if (ordersStrings[1]
							.substring(1, ordersStrings[1].length()).equals(
									"id")) {
						if (ordersStrings[1].substring(0, 1).equals("+")) {
							return values.get(NAME_ASC) + ","
									+ values.get(ID_ASC);
						}
						if (ordersStrings[1].substring(0, 1).equals("-")) {
							return values.get(NAME_ASC) + ","
									+ values.get(ID_DESC);
						}
					}
				}

			}
			if (ordersStrings[0].substring(1, ordersStrings[0].length())
					.equals("id")) {
				if (ordersStrings[0].substring(0, 1).equals("-")) {
					if (ordersStrings[1]
							.substring(1, ordersStrings[1].length()).equals(
									"name")) {
						if (ordersStrings[1].substring(0, 1).equals("+")) {
							return values.get(ID_DESC) + ","
									+ values.get(NAME_ASC);
						}
						if (ordersStrings[1].substring(0, 1).equals("-")) {
							return values.get(ID_DESC) + ","
									+ values.get(NAME_DESC);
						}
					}
					if (ordersStrings[1]
							.substring(1, ordersStrings[1].length()).equals(
									"updateDate")) {
						if (ordersStrings[1].substring(0, 1).equals("+")) {
							return values.get(ID_DESC) + ","
									+ values.get(UPDATE_DATE_ASC);
						}
						if (ordersStrings[1].substring(0, 1).equals("-")) {
							return values.get(ID_DESC) + ","
									+ values.get(UPDATE_DATE_DESC);
						}
					}

				}
				if (ordersStrings[0].substring(0, 1).equals("+")) {
					if (ordersStrings[1]
							.substring(1, ordersStrings[1].length()).equals(
									"name")) {
						if (ordersStrings[1].substring(0, 1).equals("+")) {
							return values.get(ID_ASC) + ","
									+ values.get(NAME_ASC);
						}
						if (ordersStrings[1].substring(0, 1).equals("-")) {
							return values.get(ID_ASC) + ","
									+ values.get(NAME_DESC);
						}
					}
					if (ordersStrings[1]
							.substring(1, ordersStrings[1].length()).equals(
									"updateDate")) {
						if (ordersStrings[1].substring(0, 1).equals("+")) {
							return values.get(ID_ASC) + ","
									+ values.get(ID_ASC);
						}
						if (ordersStrings[1].substring(0, 1).equals("-")) {
							return values.get(ID_ASC) + ","
									+ values.get(ID_DESC);
						}
					}
				}

			}

		} else {
			if (ordersStrings[0].substring(1, ordersStrings[0].length())
					.equals("updateDate")) {
				if (ordersStrings[0].substring(0, 1).equals("-")) {

					return values.get(UPDATE_DATE_DESC);
				}
				if (ordersStrings[0].substring(0, 1).equals("+")) {
					return values.get(UPDATE_DATE_ASC);
				}
			}
			if (ordersStrings[0].substring(1, ordersStrings[0].length())
					.equals("name")) {
				if (ordersStrings[0].substring(0, 1).equals("-")) {

					return values.get(NAME_DESC);
				}
				if (ordersStrings[0].substring(0, 1).equals("+")) {
					return values.get(NAME_ASC);
				}
			}
			if (ordersStrings[0].substring(1, ordersStrings[0].length())
					.equals("id")) {
				if (ordersStrings[0].substring(0, 1).equals("-")) {

					return values.get(ID_DESC);
				}
				if (ordersStrings[0].substring(0, 1).equals("+")) {
					return values.get(ID_ASC);
				}
			}
		}

		return values.get(DEFAULT);
	}

	synchronized private static void init() {
		if (values == null) {
			values = new HashMap<OrderByFieldUtil, String>();
			values.put(UPDATE_DATE_ASC, "updateDate asc");
			values.put(UPDATE_DATE_DESC, "updateDate desc");
			values.put(NAME_ASC, "name asc");
			values.put(ID_ASC, "id asc");
			values.put(ID_DESC, "id desc");
			values.put(NAME_DESC, "name desc");
			values.put(DEFAULT, "");
		}
	}

}
