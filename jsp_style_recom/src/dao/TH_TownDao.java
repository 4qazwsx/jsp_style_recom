package dao;



public class TH_TownDao {
	private static TH_TownDao instance;
	private TH_TownDao() {}
	public static TH_TownDao getInstance() {
		if (instance == null) {	instance = new TH_TownDao();		}
		return instance;
	}
	public String citycode(String addr) {
		String city = "";

		if (addr.equals( "4281025000"))
			city = "������";
		else if (addr.equals( "4125053500"))
			city = "����";
		else if (addr.equals( "4111759600"))
			city = "����";
		else if (addr.equals( "4817074000"))
			city = "����";
		else if (addr.equals( "4717069000"))
			city = "�ȵ�";
		else if (addr.equals( "2920054000"))
			city = "����";
		else if (addr.equals( "2720065000"))
			city = "�뱸";
		else if (addr.equals( "3023052000"))
			city = "����";
		else if (addr.equals("2644058000"))
			city = "�λ�";
		else if (addr.equals( "1168066000"))
			city = "����";
		else if (addr.equals( "3611055000"))
			city = "����";
		else if (addr.equals( "3114056000"))
			city = "���";
		else if (addr.equals( "2871025000"))
			city = "��õ";
		else if (addr.equals( "4681025000"))
			city = "����";
		else if (addr.equals( "4579031000"))
			city = "����";
		else if (addr.equals( "5013025300"))
			city = "����";
		else if (addr.equals( "4480038000"))
			city = "ȫ��";
		else if (addr.equals( "4376031000"))
			city = "����";
		return city;
	}
}
