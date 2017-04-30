package com.neworin.easynotes.service.impl;

import com.mysql.jdbc.log.Log;
import com.neworin.easynotes.dao.LoginRecordsMapper;
import com.neworin.easynotes.model.LoginRecords;
import com.neworin.easynotes.model.User;
import com.neworin.easynotes.service.ILoginRecords;
import com.neworin.easynotes.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by NewOrin Zhang on 2017/4/30.
 * Project Name:EasyNotesServer
 */
@Service
public class LoginRecordsImpl implements ILoginRecords {
    @Resource
    private LoginRecordsMapper mLoginRecordsMapper;

    public void insertRecords(User user) {
        List<LoginRecords> recordsList = mLoginRecordsMapper.selectAll();
        LoginRecords records = new LoginRecords();
        records.setId(recordsList.size() + 1);
        records.setEmail(user.getEmail());
        records.setLoginTime(DateUtil.getNowTime());
        mLoginRecordsMapper.insert(records);
    }
}
