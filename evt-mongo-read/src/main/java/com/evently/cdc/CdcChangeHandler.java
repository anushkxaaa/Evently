package com.evently.cdc;

import com.evently.common.cdc.CdcRecord;

public interface CdcChangeHandler<ID,T extends CdcRecord>{
    ID extractId(T row);
    void upsert(T row);
    void delete(ID id);
}
