/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.flink.lidea.mapper;

import com.mmc.lidea.common.bo.LideaAppBO;
import com.mmc.lidea.common.context.Const;
import com.mmc.lidea.util.BytesUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.ResultsExtractor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joey
 * @date 2019/7/26 21:11
 */
public class LideaAppResultsExtractor implements ResultsExtractor<List<LideaAppBO>> {


    /**
     * Implementations must implement this method to process the entire {@link ResultScanner}.
     *
     * @param results {@link ResultScanner} to extract data from. Implementations should not close this; it will be closed
     *                automatically by the calling {@link HbaseTemplate}
     * @return an arbitrary result object, or null if none (the extractor will typically be stateful in the latter case).
     * @throws Exception if an Hbase exception is encountered
     */
    @Override
    public List<LideaAppBO> extractData(ResultScanner results) throws Exception {


        List<LideaAppBO> rs = new ArrayList<>();
        for (Result result : results) {
            LideaAppBO bo = map(result);
            rs.add(bo);
        }

        return rs;
    }

    private LideaAppBO map(Result result) {

        LideaAppBO bo = new LideaAppBO();
        bo.setTime(BytesUtils.toString(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("time"))));
        bo.setAppName(BytesUtils.toString(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("appName"))));
        return bo;

    }
}
