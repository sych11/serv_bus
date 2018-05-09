package org.prof_itgroup.it_cbr_fourpl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.it.utils.exception.ExceptionUtil;
import org.it.utils.jdbc.JDBCHelper;
import org.it.utils.properties.PropertiesService;
import org.it.utils.validate.CustomValidator;
import org.it.utils.xml.XmlUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ItMessageListener implements MessageListener{

	private static Logger log = Logger.getLogger(ItMessageListener.class.getName());

	private PropertiesService propertiesService;

	private XmlUtils utils;
	private CustomValidator validator;

	private JDBCHelper jdbcHelper;

	public ItMessageListener(PropertiesService propertiesService, JdbcTemplate template) {
		this.propertiesService = propertiesService;

		jdbcHelper = new JDBCHelper(template);

		utils = new XmlUtils();
		validator = new CustomValidator();

	}

	public void onMessage(Message message) {
		try {
			TextMessage tm = (TextMessage) message;
			String payload = tm.getText();
			Document doc = utils.parseMessage(payload);
			String validateInputEgg = propertiesService.get("validate");
			Boolean isValidate = Boolean.valueOf(validateInputEgg);

			if (isValidate) {
				try {
					String it_cbr_xsd = "/xsd/it-cbr.xsd";
					validator.validate(doc, ItMessageListener.class.getClassLoader().getResource(it_cbr_xsd));
				} catch (Exception e) {
					log.info("ItMessageListener validate error:" + ExceptionUtil.getPrintStackTraceAsString(e));
					log.debug("Error:" + ExceptionUtil.getPrintStackTraceAsString(e));
					return;
				}
			}

			String currentDate=null;
			Date date1= Calendar.getInstance().getTime(); //текущ дата
			String date2 = new SimpleDateFormat("dd.MM.yyyy").format(date1).toString();// текущ дата отформта
			String dateValue = utils.getStringValue("/ValCurs/@Date", doc);// получ дата с хмлки xpath
			String date3 = new SimpleDateFormat("dd.MM.yyyy").parse(dateValue).toString(); //дата с хмлки отформат
			if (date2.equals(date3)) {
				currentDate=date3;
			}
			else {
				currentDate=date2;
			}

			//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
			//Date date = new SimpleDateFormat("dd.MM.yyyy").parse(currentDate);

			Date date= new SimpleDateFormat("dd.MM.yyyy").parse(currentDate);

			NodeList documentList = utils.getNodeList("/ValCurs/Valute", doc);

			for (int i = 0; i < documentList.getLength(); i++) {

				Node document = documentList.item(i).cloneNode(true);
				String numCode = utils.getStringValue("./NumCode", document);
				String value = utils.getStringValue("./Value", document);
				Integer quantity = Integer.parseInt(utils.getStringValue("./Nominal", document));
				String charCode = utils.getStringValue("./CharCode", document);

				DecimalFormat myFormatter = (DecimalFormat) DecimalFormat.getNumberInstance(new Locale("ru"));
				Double doubleValue = new Double(myFormatter.parse(value).doubleValue());

				List<Map<String, Object>> listMap = jdbcHelper.getListMap("select id from currency where name=?", new Object[]{charCode});
				for (Map<String, Object> map : listMap) {
					Integer currencyid = Integer.parseInt((String) map.get("id"));
					jdbcHelper.update("insert into currencyrate (currencyid, daterate, value, quantity) values(?, ?, ?, ?)", new Object[]{currencyid, date, doubleValue, quantity});
				}

			}


		} catch (Exception e) {
			log.info("ItMessageListener error:" + ExceptionUtil.getPrintStackTraceAsString(e));
			log.debug("Error:" + ExceptionUtil.getPrintStackTraceAsString(e));
		}
	}
}