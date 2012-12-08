package com.scurab.gwt.rlw.server.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.google.gson.Gson;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.scurab.gwt.rlw.client.DataService;
import com.scurab.gwt.rlw.server.Application;
import com.scurab.gwt.rlw.server.Database;
import com.scurab.gwt.rlw.server.Queries;
import com.scurab.gwt.rlw.server.Queries.AppQuery;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;

public class DataServiceImpl extends RemoteServiceServlet implements DataService {

    Gson g = new Gson();
    /**
     * 
     */
    private static final long serialVersionUID = -4187117132460121962L;

    @Override
    public List<String> getApplications() throws Exception {
        return new DataProvider().getApplications();
    }

    @Override
    public List<Device> getDevices(String jsonParams) {
        return new DataProvider().getDevices(g.fromJson(jsonParams, HashMap.class));
    }

    @Override
    public List<LogItem> getLogs(String jsonParams) {
        return null;
    }

}
