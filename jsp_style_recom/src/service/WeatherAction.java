package service;

import java.text.SimpleDateFormat;
import org.w3c.dom.*;

import dao.TH_TownDao;

import javax.xml.parsers.*;
import java.util.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WeatherAction implements CommandProcess {

	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("start");
		try {
			String addr = request.getParameter("sido");
			System.out.println("addr->" + addr);
			TH_TownDao TD = TH_TownDao.getInstance();
			String city = TD.citycode(addr);
			System.out.println("city->"+city);
			String urlStr = "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=" + addr;
			String urlStrW = "http://www.kma.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=108";
			// �����������ϵ� XML�������� ������Ʈ �̸� �迭
			String[] fieldNames = { "temp", "wfKor", "wfEn", "pop", "hour", "day", "tmx", "tmn", "r12" };
			String[] fieldNames1 = { "tmEf", "wf", "tmx", "tmn", "rnSt" };
			String[] citys = { "����", "��õ", "����", "����", "��õ", "����", "��õ", "����", "����", "����", "����", "ȫ��", "û��", "����", "����",
					"����", "����", "����", "��õ", "����", "����", "����", "����", "����", "����", "��â", "����", "�λ�", "���", "â��", "����",
					"��â", "�뿵", "�뱸", "�ȵ�", "����", "����", "����", "�︪��", "����", "������" };

			// �� �Խù��ϳ��� �ش��ϴ� XML ��带 ���� ����Ʈ
			ArrayList<HashMap<String, String>> pubList = new ArrayList<HashMap<String, String>>();
			ArrayList<HashMap<String, String>> pubList1 = new ArrayList<HashMap<String, String>>();

			String[] wfKor = new String[7];
			String[] imgWfKor = new String[7];
			double Temp1 = -100.0;
			double[] rs = new double[7];

			double[] Tmx = new double[6];
			double[] Tmn = new double[6];
			try {
				// XML�Ľ� �غ�
				DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
				DocumentBuilder b = f.newDocumentBuilder();

				// ������ ������ URL�� ���� XMl �Ľ� ����
				Document doc = b.parse(urlStr);
				// System.out.println("doc->" + doc);
				doc.getDocumentElement().normalize();

				// �������� ������ XML�����͸� data(���๮�� 1�� �ش�)�±׷� ���� ����(�Ķ���ͷ� ��û�� size�׸��� ����ŭ)
				NodeList items = doc.getElementsByTagName("data");

				// for ��������
				for (int i = 0; i < items.getLength(); i++) {
					// i��° publication �±׸� �����ͼ�
					Node n = items.item(i);

					Element e = (Element) n;
					HashMap<String, String> pub = new HashMap<String, String>();

					// for ���� ����
					for (String name : fieldNames) {
						// "hour", "day", "temp", "tmx", "tmn", "sky", "pty", "wfKor"....�� �ش��ϴ� ���� XML
						// ��忡�� ������
						NodeList titleList = e.getElementsByTagName(name);
						Element titleElem = (Element) titleList.item(0);

						Node titleNode = titleElem.getChildNodes().item(0);
						// ������ XML ���� �ʿ� ������Ʈ �̸� - �� ������ ����
						pub.put(name, titleNode.getNodeValue());
					}
					// �����Ͱ� ���� �� ���� ����Ʈ�� �ְ� ȭ�鿡 �Ѹ� �غ�.
					pubList.add(pub);
					// System.out.println(pub);

				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			try {
				// XML�Ľ� �غ�
				DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
				DocumentBuilder b = f.newDocumentBuilder();

				// ������ ������ URL�� ���� XMl �Ľ� ����
				Document doc = b.parse(urlStrW);
				// System.out.println("doc->" + doc);
				doc.getDocumentElement().normalize();

				// �������� ������ XML�����͸� data(���๮�� 1�� �ش�)�±׷� ���� ����(�Ķ���ͷ� ��û�� size�׸��� ����ŭ)
				NodeList items = doc.getElementsByTagName("data");
				// System.out.println("items->" + items);
				// for ��������
				for (int i = 0; i < items.getLength(); i++) {
					// i��° publication �±׸� �����ͼ�

					Node n1 = items.item(i);

					Element e1 = (Element) n1;
					HashMap<String, String> pub1 = new HashMap<String, String>();

					// for ���� ����
					for (String name : fieldNames1) {
						// "hour", "day", "temp", "tmx", "tmn", "sky", "pty", "wfKor"....�� �ش��ϴ� ���� XML
						// ��忡�� ������
						NodeList titleList = e1.getElementsByTagName(name);
						Element titleElem1 = (Element) titleList.item(0);
						Node titleNode1 = titleElem1.getChildNodes().item(0);

						// ������ XML ���� �ʿ� ������Ʈ �̸� - �� ������ ����
						pub1.put(name, titleNode1.getNodeValue());
						int j = i / 13;
						pub1.put("city", citys[j]);

					}
					// �����Ͱ� ���� �� ���� ����Ʈ�� �ְ� ȭ�鿡 �Ѹ� �غ�.
					pubList1.add(pub1);
					// System.out.println("pub1->" + pub1);
				}

			} catch (Exception e) {
				System.out.println("error" + e.getMessage());
			}

			for (int i = 0; i < pubList.size(); i++) {
				HashMap<String, String> pub = pubList.get(i);

				String tmpWfKor = pub.get("wfKor");
				if (tmpWfKor == null)
					tmpWfKor = "";

				String tmpImgWfKor = "ico01";
				String tmpWfDay = pub.get("day");
				String tmpWfHour = pub.get("hour");

				if (tmpWfKor.equals("���� ����"))
					tmpImgWfKor = "ico02";
				else if (tmpWfKor.equals("���� ����"))
					tmpImgWfKor = "ico03";
				else if (tmpWfKor.equals("�帲"))
					tmpImgWfKor = "ico04";
				else if (tmpWfKor.equals("��"))
					tmpImgWfKor = "ico05";
				else if (tmpWfKor.equals("��/��"))
					tmpImgWfKor = "ico06";
				else if (tmpWfKor.equals("��"))
					tmpImgWfKor = "ico07";

				String rsWfRS = pub.get("r12");
				// System.out.println("rsWfRS->" + rsWfRS);
				if (rsWfRS == null)
					break;
				Double Rs = Double.parseDouble(rsWfRS);// �޾ƿ� ������

				String tmpWfTemp = pub.get("temp");
				tmpWfTemp = tmpWfTemp.replace('"', ' ');
				Double Temp = Double.parseDouble(tmpWfTemp);// �޾ƿ� �µ�
				// �ְ�µ�
				String tmx = pub.get("tmx");
				// System.out.println(tmx);
				tmx = tmx.replace('"', ' ');
				Double TempM = Double.parseDouble(tmx);// �޾ƿ� �µ�
				// �����µ�
				String tmn = pub.get("tmn");
				tmn = tmn.replace('"', ' ');
				Double TempN = Double.parseDouble(tmn);// �޾ƿ� �µ�

				if (tmpWfDay.equals("0")) {
					switch (tmpWfHour) {
					case ("3"): {

						wfKor[0] = tmpWfKor;
						imgWfKor[0] = tmpImgWfKor;
						rs[0] = Rs;
						Temp1 = Temp;
						// System.out.println(tmpWfDay);
					}
					case ("6"): {
						wfKor[0] = tmpWfKor;
						imgWfKor[0] = tmpImgWfKor;
						rs[0] = Rs;
						if (Temp1 == -100.0)
							Temp1 = Temp;
						else
							break;
						// System.out.println(tmpWfDay);

					}
					case ("9"): {
						wfKor[0] = tmpWfKor;
						imgWfKor[0] = tmpImgWfKor;
						rs[0] = Rs;
						if (Temp1 == -100.0)
							Temp1 = Temp;
						else
							break;
						// System.out.println(tmpWfDay);

					}
					case ("12"): {
						wfKor[0] = tmpWfKor;
						imgWfKor[0] = tmpImgWfKor;
						rs[0] = Rs;
						if (Temp1 == -100.0)
							Temp1 = Temp;
						else
							break;
						// System.out.println(tmpWfDay);

					}
					case ("15"): {
						wfKor[0] = tmpWfKor;
						imgWfKor[0] = tmpImgWfKor;
						rs[0] = Rs;
						if (Temp1 == -100.0)
							Temp1 = Temp;
						else
							break;
						// System.out.println(tmpWfDay);

					}
					case ("18"): {
						wfKor[0] = tmpWfKor;
						imgWfKor[0] = tmpImgWfKor;
						rs[0] = Rs;
						if (Temp1 == -100.0)
							Temp1 = Temp;
						else
							break;
						// System.out.println(tmpWfDay);

					}
					case ("21"): {
						wfKor[0] = tmpWfKor;
						imgWfKor[0] = tmpImgWfKor;
						rs[0] = Rs;
						if (Temp1 == -100.0)
							Temp1 = Temp;
						else
							break;
						// System.out.println(tmpWfDay);

					}
					}
				}
				// ���ϳ��������ޱ�
				if (tmpWfDay.equals("1")) {
					wfKor[1] = tmpWfKor;
					imgWfKor[1] = tmpImgWfKor;
					rs[1] = Rs;
					Tmx[0] = TempM;
					Tmn[0] = TempN;
					// System.out.println(tmpWfDay);

				}

				// �� ���� ���� �ޱ�
				if (!tmpWfKor.equals("") && tmpWfDay.equals("2")) {
					wfKor[2] = tmpWfKor;
					imgWfKor[2] = tmpImgWfKor;
					rs[2] = Rs;
					Tmx[1] = TempM;
					Tmn[1] = TempN;
					// System.out.println(tmpWfDay);

				}

			}

			// �ְ���������
			for (int i = 0; i < pubList1.size(); i++) {
				HashMap<String, String> pub1 = pubList1.get(i);
				// "tmEf", "wf","tmx","tmn","rnSt"

				String tmpWf = pub1.get("wf");
				if (tmpWf == null)
					tmpWf = "";

				String tmpImgWfKor = "ico01";

				if (tmpWf.equals("���� ����"))
					tmpImgWfKor = "ico02";
				else if (tmpWf.equals("���� ����"))
					tmpImgWfKor = "ico03";
				else if (tmpWf.equals("�帲"))
					tmpImgWfKor = "ico04";
				else if (tmpWf.equals("��"))
					tmpImgWfKor = "ico05";
				else if (tmpWf.equals("��/��"))
					tmpImgWfKor = "ico06";
				else if (tmpWf.equals("��"))
					tmpImgWfKor = "ico07";

				String tmEf = pub1.get("tmEf");
				// System.out.println(tmEf);
				int idx = tmEf.indexOf(" ");
				String day = tmEf.substring(0, idx);
				// System.out.println("tmEf1 : "+day);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

				Calendar c1 = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();
				Calendar c3 = Calendar.getInstance();
				Calendar c4 = Calendar.getInstance();
				c1.add(Calendar.DATE, 3);
				String day3 = df.format(c1.getTime());
				c2.add(Calendar.DATE, 4);
				String day4 = df.format(c2.getTime());
				c3.add(Calendar.DATE, 5);
				String day5 = df.format(c3.getTime());
				c4.add(Calendar.DATE, 6);
				String day6 = df.format(c4.getTime());
			//	System.out.println("��¥�˻�1->" + day5);
			//	System.out.println("��¥�˻�2->" + day6);

				String rsWfRS = pub1.get("rnSt");
				// System.out.println("rsWfRS->" + rsWfRS);
				if (rsWfRS == null)
					break;
				Double Rs = Double.parseDouble(rsWfRS);// �޾ƿ� ������

				// �ְ�µ�
				String tmx = pub1.get("tmx");
				// System.out.println(tmx);
				tmx = tmx.replace('"', ' ');
				Double TempM = Double.parseDouble(tmx);// �޾ƿ� �µ�
				// �����µ�
				String tmn = pub1.get("tmn");
				tmn = tmn.replace('"', ' ');
				Double TempN = Double.parseDouble(tmn);// �޾ƿ� �µ�

				String ct = pub1.get("city");

				if (day.compareTo(day3) == 0 && city == ct) {
					wfKor[3] = tmpWf;
					imgWfKor[3] = tmpImgWfKor;
					rs[3] = Rs;
					Tmx[2] = TempM;
					Tmn[2] = TempN;
				}
				if (day.compareTo(day4) == 0 && city == ct) {
					wfKor[4] = tmpWf;
					imgWfKor[4] = tmpImgWfKor;
					rs[4] = Rs;
					Tmx[3] = TempM;
					Tmn[3] = TempN;
				}
				if (day.compareTo(day5) == 0 && city == ct) {
					wfKor[5] = tmpWf;
					imgWfKor[5] = tmpImgWfKor;
					rs[5] = Rs;
			///		System.out.println("�µ���1" + TempM + " " + TempN);
					Tmx[4] = TempM;
					Tmn[4] = TempN;
				}
				if (day.compareTo(day6) == 0 && city == ct) {
					wfKor[6] = tmpWf;
					imgWfKor[6] = tmpImgWfKor;
					rs[6] = Rs;
			//		System.out.println("�µ���2" + TempM + " " + TempN);
					Tmx[5] = TempM;
					Tmn[5] = TempN;
				}

			}
			request.setAttribute("city", city);

			request.setAttribute("wfKor0", wfKor[0]);
			request.setAttribute("wfKor1", wfKor[1]);
			request.setAttribute("wfKor2", wfKor[2]);
			request.setAttribute("wfKor3", wfKor[3]);
			request.setAttribute("wfKor4", wfKor[4]);
			request.setAttribute("wfKor5", wfKor[5]);
			request.setAttribute("wfKor6", wfKor[6]);

			request.setAttribute("rs0", rs[0]);
			request.setAttribute("rs1", rs[1]);
			request.setAttribute("rs2", rs[2]);
			request.setAttribute("rs3", rs[3]);
			request.setAttribute("rs4", rs[4]);
			request.setAttribute("rs5", rs[5]);
			request.setAttribute("rs6", rs[6]);

			request.setAttribute("Temp1", Temp1);
			request.setAttribute("Tmx0", Tmx[0]);
			request.setAttribute("Tmn0", Tmn[0]);
			request.setAttribute("Tmx1", Tmx[1]);
			request.setAttribute("Tmn1", Tmn[1]);
			request.setAttribute("Tmx2", Tmx[2]);
			request.setAttribute("Tmn2", Tmn[2]);
			request.setAttribute("Tmx3", Tmx[3]);
			request.setAttribute("Tmn3", Tmn[3]);
			request.setAttribute("Tmx4", Tmx[4]);
			request.setAttribute("Tmn4", Tmn[4]);
			request.setAttribute("Tmx5", Tmx[5]);
			request.setAttribute("Tmn5", Tmn[5]);
			

			System.out.println("Tmx[0]->"+Tmx[0]);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "/mypage/weather.jsp";
	}

}
