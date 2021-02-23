package com.ebs.hydrokleen.networkutils;

import com.ebs.hydrokleen.models.AcDetails;

/**
 * Created by Amit on 22,April,2020
 * kundu.amit517@gmail.com
 */
public interface AcInfoCallBack {

    void OnResult(boolean code);
    void AcDetails(AcDetails acDetails);

}
