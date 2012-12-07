package com.scurab.gwt.rlw.server;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Simple object to provide SQL/HQL Queries from sqlqueries.xml file
 * @author Joe Scurab
 *
 */

public class Queries extends HashMap<String, String>
{
	private static final long serialVersionUID = 8632300493669064910L;
	private static final HashMap<String, AppQuery> mQueries = new HashMap<String, AppQuery>();

	static
	{
		if(mQueries.size() == 0)
			init();
	}

	public static void init()
	{
		String file = "/sqlqueries.xml" ;
		InputStream inputStream = Queries.class.getResourceAsStream(file);

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
		try
		{
			docBuilder = docBuilderFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = docBuilder.parse(inputStream);
			NodeList queries = doc.getElementsByTagName("Query");
			for(int i = 0;i<queries.getLength();i++)
			{
				Node n = queries.item(i);
				Node type = n.getAttributes().getNamedItem("type");
				Node params = n.getAttributes().getNamedItem("parameters");
				String name= n.getAttributes().getNamedItem("name").getNodeValue();
				String queryType = (type != null) ? type.getNodeValue() : AppQuery.TYPE_HQL;
				String[] parameters = (params != null) ? params.getNodeValue().split(",") : null;
				String query = n.getTextContent().trim();
				AppQuery q = new AppQuery();
				q.Query = query;
				q.Type = queryType;
				if(parameters != null)
					q.Parameters = new HashSet<String>(Arrays.asList(parameters));
				mQueries.put(name, q);
			}
		}
		catch (Exception e)
		{
			System.err.println("UNABLE TO LOAD sqlquries.xml!");
			e.printStackTrace();
		}
	}

	public static AppQuery getQuery(String key)
	{
		return mQueries.get(key);
	}

	public static final class AppQuery
	{
		public static final String TYPE_SQL = "SQL";
		public static final String TYPE_HQL = "HQL";

		public String Query = null;
		public String Type = null;
		public HashSet<String> Parameters;
	}

	public static final class QueryNames
	{
        public static final String CREATE_TABLES = "CREATE_TABLES";
        public static final String SELECT_APPS = "SELECT_APPS";
        public static final String SELECT_DEVS = "SELECT_DEVS";
        public static final String SELECT_DEVS_BY_APP = "SELECT_DEVS_BY_APP";
	}
}
